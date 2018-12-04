package com.viator.connector.infrastructure;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundGateway;
import org.springframework.integration.channel.DirectChannel;

@Configuration
public class RabbitMQConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfiguration.class);

    @Value("${rabbitmq.host}")
    private String rabbitHost;

    @Value("${rabbitmq.port}")
    private int rabbitPort;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.vendor.product.exchangename:vendor.product.exchange}")
    private String vendorProductExchangeName;

    @Value("${rabbitmq.vendor.booking.exchangename:vendor.booking.exchange}")
    private String vendorBookingExchangeName;

    @Value("${rabbitmq.vendor.product.queuename:viator.vendor.product.queue}")
    private String viatorProductQueueName;

    @Value("${rabbitmq.vendor.booking.queuename:viator.vendor.booking.queue}")
    private String viatorBookingQueueName;

    @Value("${rabbitmq.routingkeyname:viator}")
    private String connectorRoutingKeyName;

    @Value("${rabbitmq.heartbeat.frequency:5}")
    private int heartbeatFrequency;

    @Value("${rabbitmq.automatic.recovery.enabled:false}")
    private boolean automaticRecoveryEnabled;

    @Value("${rabbitmq.topology.recovery.enabled:true}")
    private boolean topologyRecoveryEnabled;

    @Value("${rabbitmq.network.recovery.interval:5000}")
    private int networkRecoveryInterval;

    // default set to production value.
    @Value("${rabbitmq.connector.fixed.queue.durable:true}")
    private boolean fixedQueueDurable;

    @Value("${rabbitmq.concurrent.consumers:15}")
    private int concurrentConsumers;

    @Value("${rabbitmq.max.concurrent.consumers:25}")
    private int maxConcurrentConsumers;
    
    @Value("${rabbitmq.listener.auto.startup:false}")
    private boolean autoStartup;

    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitmqClientConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitmqClientConnectionFactory.setHost(rabbitHost);
        rabbitmqClientConnectionFactory.setPort(rabbitPort);
        rabbitmqClientConnectionFactory.setPassword(this.password);
        rabbitmqClientConnectionFactory.setUsername(this.username);
        rabbitmqClientConnectionFactory.setRequestedHeartbeat(this.heartbeatFrequency);// check default vs what we want

        log.info("com.rabbitmq.client.ConnectionFactory.isAutomaticRecoveryEnabled():"
                + rabbitmqClientConnectionFactory.isAutomaticRecoveryEnabled());
        log.info("com.rabbitmq.client.ConnectionFactory.isTopologyRecoveryEnabled():"
                + rabbitmqClientConnectionFactory.isTopologyRecoveryEnabled());
        log.info("com.rabbitmq.client.ConnectionFactory.getNetworkRecoveryInterval():"
                + rabbitmqClientConnectionFactory.getNetworkRecoveryInterval());

        // FIXME: Spring AMQP may not be compatible
        // Spring AMQP now uses the 4.0.x version of amqp-client, which has auto recovery enabled by default.
        rabbitmqClientConnectionFactory.setAutomaticRecoveryEnabled(this.automaticRecoveryEnabled); // check default vs
                                                                                                    // what we want
        rabbitmqClientConnectionFactory.setTopologyRecoveryEnabled(this.topologyRecoveryEnabled);// check default vs
                                                                                                 // what we want
        rabbitmqClientConnectionFactory.setNetworkRecoveryInterval(this.networkRecoveryInterval);// check default vs
                                                                                                 // what we want

        // supports multiple broker addresses
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqClientConnectionFactory);
        // connectionFactory.setAddresses(this.addressList);
        connectionFactory.setRequestedHeartBeat(this.heartbeatFrequency);
        // connectionFactory.setHost(rabbitHost);
        // connectionFactory.setPort(rabbitPort);
        // connectionFactory.setUsername(username);
        // connectionFactory.setPassword(password);
        return connectionFactory;
    }

    // ------------------ Vendor Product Exchange, Queue and Binding

    @Bean
    public DirectExchange vendorProductExchange() {
        return new DirectExchange(vendorProductExchangeName);
    }

    @Bean
    public Queue vendorProductQueue() {

        // FIXME: advanced queue arguments to evaluate. A more flexible, unobtrisuve, and manageable way of configuring
        // x-arguments is via policies.
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 30000L); // 30 seconds.. ttl might not be needed. if not TTL can be specified on a
                                           // per-message basis, by setting the expiration field in the basic AMQP class
                                           // when sending a basic.publish.

        // arguments.put("x-ha-policy", "");
        // arguments.put("x-ha-policy-params", "");
        // return new Queue(eventQueueName, false, true, true, arguments);

        // FIXME: queue settings.
        // Exclusive queues while prevents other connectors (connections) from reusing the queue, it will however
        // deleted when that connection closes.

        // Durable (configurable), non-exclusive and non auto-delete queue.
        return new Queue(viatorProductQueueName, fixedQueueDurable, false, false);
    }

    @Bean
    public Binding vendorProductbBinding() {
        return BindingBuilder.bind(vendorProductQueue()).to(vendorProductExchange()).with(connectorRoutingKeyName);
    }

    // ------------------ Vendor Booking Exchange, Queue and Binding

    /**
     * Exchange is for placing a booking or canceling a booking, where message guarantee is needed.
     * 
     * Booking direct exchange should have strongest message delivery guarantees. Other direct exchanges can have lesser
     * guarantees.
     * 
     * 
     * Booking direct exchange: MessageProperties: deliveryMode-PERSISTENT. The persistence guarantees aren't strong,
     * but it's more than enough for our simple task queue. If you need a stronger guarantee then you can use publisher
     * confirms. https://www.rabbitmq.com/confirms.html
     * 
     * @return
     */
    @Bean
    public DirectExchange vendorBookingExchange() {
        return new DirectExchange(vendorBookingExchangeName);
    }

    @Bean
    public Queue vendorBookingQueue() {

        // FIXME: advanced queue arguments to evaluate
        // Map<String, Object> arguments = new HashMap<String, Object>();
        // arguments.put("x-message-ttl", 10000L); // 10 seconds.. ttl might not be needed
        // arguments.put("x-ha-policy", "");
        // arguments.put("x-ha-policy-params", "");
        // return new Queue(eventQueueName, false, true, true, arguments);

        // FIXME: queue settings.
        // Exclusive queues while prevents other connectors (connections) from reusing the queue, it will however
        // deleted when that connection closes.

        // Durable (configurable), non-exclusive and non auto-delete queue.
        return new Queue(viatorBookingQueueName, fixedQueueDurable, false, false);
    }

    @Bean
    public Binding vendorBookingBinding() {
        return BindingBuilder.bind(vendorBookingQueue()).to(vendorBookingExchange()).with(connectorRoutingKeyName);
    }

    // ------------------

    /**
     * Helps to control consumers separately for the product queue. Requests are supposed to be served fast, and may
     * require a higher number of consumers.
     * 
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer listenerContainerProduct() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        listenerContainer.setConcurrentConsumers(concurrentConsumers);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setQueues(vendorProductQueue());
        listenerContainer.setMessageConverter(new CustomMessageConverter());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        listenerContainer.setAutoStartup(autoStartup);
        // listenerContainer.setMessageListener(new MessageListber);

        // might need this setting when CustomMessageConverter is no longer required
        // listenerContainer.setDefaultRequeueRejected(false);

        // listenerContainer.setConsumerTagStrategy(new ViatorConsumerTagStrategy());
        // listenerContainer.setMessageListener(rabbitTemplate(connectionFactory(), messageConverter()));
        return listenerContainer;
    }

    /**
     * Helps to control consumers separately for the booking queue.
     * 
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer listenerContainerBooking() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory());
        listenerContainer.setConcurrentConsumers(concurrentConsumers);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setQueues(vendorBookingQueue());
        listenerContainer.setMessageConverter(new CustomMessageConverter());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        listenerContainer.setAutoStartup(autoStartup);

        // might need this setting when CustomMessageConverter is no longer required
        // listenerContainer.setDefaultRequeueRejected(false);
        return listenerContainer;
    }

    @Bean
    public AmqpInboundGateway inboundProductGateway() {
        AmqpInboundGateway gateway = new AmqpInboundGateway(listenerContainerProduct());
        gateway.setRequestChannel(amqpInputChannel());
        gateway.setReplyChannel(amqpOutputChannel());
        gateway.setMessageConverter(new CustomMessageConverter());
        gateway.setErrorChannel(errorChannel());
        gateway.setAutoStartup(autoStartup);
        return gateway;
    }

    @Bean
    public AmqpInboundGateway inboundBookingGateway() {
        AmqpInboundGateway gateway = new AmqpInboundGateway(listenerContainerBooking());
        gateway.setRequestChannel(amqpInputChannel());
        gateway.setReplyChannel(amqpOutputChannel());
        gateway.setMessageConverter(new CustomMessageConverter());
        gateway.setErrorChannel(errorChannel());
        gateway.setAutoStartup(autoStartup);
        return gateway;
    }

    @Bean
    public DirectChannel amqpInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel amqpOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel makeBookingInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel cancelBookingInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel getProductPriceInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel getBookingQuestionsInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel getBookingVoucherInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel getProductDetailsInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel getAvailabilityInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel getProductOptionsInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel getProductReviewsInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel errorChannel() {
        return new DirectChannel();
    }

    public static class ViatorConsumerTagStrategy implements ConsumerTagStrategy {
        @Override
        public String createConsumerTag(String queue) {
            try {
                return "vendor-connector-viator - " + InetAddress.getLocalHost().getHostName() + ":" + queue;
            } catch (UnknownHostException e) {
                return "vendor-connector-viator - Unknown Host";
            }
        }
    }

    // ----------------------------------

    /**
     * @return the admin bean that can declare queues etc.
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(autoStartup);
        return rabbitAdmin;
    }

}
