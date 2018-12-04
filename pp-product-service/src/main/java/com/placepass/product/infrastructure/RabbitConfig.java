package com.placepass.product.infrastructure;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.host}")
    private String rabbitHost;

    @Value("${rabbitmq.port}")
    private int rabbitPort;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.vendor.product.exchangename}")
    private String vendorProductExchangeName;

    @Value("${rabbitmq.vendor.product.wait.for.reply.timeout}")
    private long productReplyTimeout;
    
    @Value("${vendor.connector.outbound.amqp:false}")
    private boolean amqpOutboubd;

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        if (!amqpOutboubd){
        	connectionFactory.setRequestedHeartBeat(0);
        }
        // connectionFactory.setChannelCacheSize(sessionCacheSize); may need if publisher confirms is in play, as
        // unbounded means can run out of memory.

        return connectionFactory;
    }

    /**
     * @return the admin bean that can declare queues etc.
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(amqpOutboubd);
        return rabbitAdmin;
    }

    // ------------------ Vendor Product Exchange and spring integration

    /**
     * For any product related activity with vendor connector.
     * 
     * 
     * @return
     */
    @Bean
    public DirectExchange vendorProductExchange() {
        return new DirectExchange(vendorProductExchangeName);
    }

    @Bean
    public RabbitTemplate productRabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(new VendorConnectorMessageConverter());
        rabbitTemplate.setReplyTimeout(productReplyTimeout);
        return rabbitTemplate;
    }

    @Bean
    @ServiceActivator(inputChannel = "vendorProductOutboundChannel")
    public AmqpOutboundEndpoint vendorOutbound(RabbitTemplate productRabbitTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(productRabbitTemplate);
        outbound.setExpectReply(true);
        outbound.setExchangeName(vendorProductExchangeName);
        ExpressionParser parser = new SpelExpressionParser();
        outbound.setRoutingKeyExpression(parser.parseExpression("headers['RoutingKey']"));
        return outbound;
    }

    // ----------------- Error Channel

    @Bean
    public DirectChannel errorChannel() {
        return new DirectChannel();
    }

}
