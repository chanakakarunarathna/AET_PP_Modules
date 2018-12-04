package com.stripe.payment.connector.infrastructure;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.stripe.payment.connector.application.PaymentAppService;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.host}")
    private String rabbitHost;

    @Value("${rabbitmq.port}")
    private int rabbitPort;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.queuename}")
    private String queueName;

    @Value("${rabbitmq.exchangename}")
    private String exchangeName;

    @Value("${rabbitmq.routingkeyname}")
    private String connectorRoutingKeyName;

    private boolean durable = true;

    private boolean queueExclusive = false;

    private boolean autoDelete = false;

    @Autowired
    private PaymentAppService appService;
    
    @Value("${rabbitmq.heartbeat.frequency}")
    private int heartbeatFrequency;
    
    @Value("${rabbitmq.automatic.recovery.enabled:false}")
    private boolean automaticRecoveryEnabled;

    @Value("${rabbitmq.topology.recovery.enabled:true}")
    private boolean topologyRecoveryEnabled;

    @Value("${rabbitmq.network.recovery.interval}")
    private int networkRecoveryInterval;
    
    @Value("${rabbitmq.concurrent.consumers:15}")
    private int concurrentConsumers;

    @Value("${rabbitmq.max.concurrent.consumers:25}")
    private int maxConcurrentConsumers;
    
    @Value("${rabbitmq.listener.auto.startup:false}")
    private boolean autoStartup;
    
    private static final Logger log = LoggerFactory.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {

        com.rabbitmq.client.ConnectionFactory rabbitmqClientConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitmqClientConnectionFactory.setHost(rabbitHost);
        rabbitmqClientConnectionFactory.setPort(rabbitPort);
        rabbitmqClientConnectionFactory.setPassword(this.password);
        rabbitmqClientConnectionFactory.setUsername(this.username);
        rabbitmqClientConnectionFactory.setRequestedHeartbeat(this.heartbeatFrequency);

        log.info("com.rabbitmq.client.ConnectionFactory.isAutomaticRecoveryEnabled():"
                + rabbitmqClientConnectionFactory.isAutomaticRecoveryEnabled());
        log.info("com.rabbitmq.client.ConnectionFactory.isTopologyRecoveryEnabled():"
                + rabbitmqClientConnectionFactory.isTopologyRecoveryEnabled());
        log.info("com.rabbitmq.client.ConnectionFactory.getNetworkRecoveryInterval():"
                + rabbitmqClientConnectionFactory.getNetworkRecoveryInterval());

        // FIXME: Spring AMQP may not be compatible
        // Spring AMQP now uses the 4.0.x version of amqp-client, which has auto recovery enabled by default.
        rabbitmqClientConnectionFactory.setAutomaticRecoveryEnabled(this.automaticRecoveryEnabled);
        rabbitmqClientConnectionFactory.setTopologyRecoveryEnabled(this.topologyRecoveryEnabled);
        rabbitmqClientConnectionFactory.setNetworkRecoveryInterval(this.networkRecoveryInterval);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitmqClientConnectionFactory);
        cachingConnectionFactory.setRequestedHeartBeat(heartbeatFrequency);
        return cachingConnectionFactory;

    }

    @Bean
    public TopicExchange placepassExchange() {

        TopicExchange exchange = new TopicExchange(exchangeName, durable, autoDelete);
        exchange.setShouldDeclare(true);
        return exchange;
    }

    @Bean
    public Queue emailNotificaitonQueue() {
        Queue q = new Queue(queueName, durable, queueExclusive, autoDelete);
        q.setShouldDeclare(true);
        return q;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(emailNotificaitonQueue()).to(placepassExchange()).with(connectorRoutingKeyName);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {

        DefaultClassMapper cm = new DefaultClassMapper();
        JsonMessageConverter j = new JsonMessageConverter();
        j.setClassMapper(cm);
        return j;
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {

        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(rabbitConnectionFactory());
        listenerContainer.setConcurrentConsumers(concurrentConsumers);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setAutoStartup(autoStartup);
        listenerContainer.setQueues(emailNotificaitonQueue());
        listenerContainer.setMessageConverter(new CustomMessageConverter());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        listenerContainer.setConsumerTagStrategy(new TestConsumerTagStrategy());

        return listenerContainer;
    }

    public static class TestConsumerTagStrategy implements ConsumerTagStrategy {

        @Override
        public String createConsumerTag(String queue) {
            try {
                return "placepass-conpay-stripe - " + InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                return "placepass-conpay-stripe - Unknown Host";
            }
        }

    }

    // //// Spring Integration ////////
    @Bean
    public MessageChannel amqpInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public AmqpInboundGateway inbound(@Qualifier("amqpInputChannel") MessageChannel channel) {
        AmqpInboundGateway gateway = new AmqpInboundGateway(listenerContainer());
        gateway.setRequestChannel(channel);
        gateway.setMessageConverter(new CustomMessageConverter());
        gateway.setAutoStartup(autoStartup);
        return gateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "amqpInputChannel")
    public MessageHandler handler() {
        return new AbstractReplyProducingMessageHandler() {

            @Override
            protected Object handleRequestMessage(Message<?> requestMessage) {
                return appService.handleMessage(requestMessage.getPayload());
            }

        };
    }

}
