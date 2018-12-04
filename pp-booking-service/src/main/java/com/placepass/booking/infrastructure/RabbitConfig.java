package com.placepass.booking.infrastructure;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

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

    @Value("${rabbitmq.wait.for.reply.timeout}")
    private long replyTimeout;

    @Value("${rabbitmq.platform.events.exchangename}")
    private String platformEventsExchangeName;

    @Value("${stripe.connectionmode:REST}")
    private String stripeConnectionMode;
    
    @Value("${sendgrid.connectionmode:REST}")
    private String sendgridConnectionMode;
    
    private static final String REST = "REST";
    
    // FIXME: for now these are read from properties.
    // private static String PLATFORM_EVENTS_EXCHANGENAME = "platform.events.exchange";

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        // connectionFactory.setChannelCacheSize(sessionCacheSize); may need if publisher confirms is in play, as
        // unbounded means can run out of memory.

        //Disable heartbeat when REST mode is enabled
        if (REST.equals(stripeConnectionMode) && REST.equals(sendgridConnectionMode)){
        	connectionFactory.setRequestedHeartBeat(0);
        }
        
        return connectionFactory;
    }

    /**
     * @return the admin bean that can declare queues etc.
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    // ------------------ Payment Exchange and related settings

    @Bean
    public RabbitTemplate bookingRabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(new CustomMessageConverter());
        rabbitTemplate.setReplyTimeout(replyTimeout);
        return rabbitTemplate;
    }

    // // FIXME: where is this used and the naming??
    // @Bean
    // public Queue replyQueue() {
    // return new Queue("my.reply.queue");
    // }
    //
    // // FIXME: where is this used??
    // @Bean
    // public SimpleMessageListenerContainer replyListenerContainer(ConnectionFactory rabbitConnectionFactory,
    // RabbitTemplate bookingRabbitTemplate, Queue replyQueue) {
    // SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    // container.setConnectionFactory(rabbitConnectionFactory);
    // container.setQueues(replyQueue);
    // container.setMessageListener(bookingRabbitTemplate);
    // return container;
    // }

    // ------------------ Payment Exchange and spring integration
    @Bean
    @ServiceActivator(inputChannel = "amqpOutboundChannel")
    public AmqpOutboundEndpoint amqpOutbound(RabbitTemplate bookingRabbitTemplate) {
        AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(bookingRabbitTemplate);
        outbound.setExpectReply(true);
        ExpressionParser parser = new SpelExpressionParser();
        outbound.setExchangeNameExpression(parser.parseExpression("Payload.ExchangeName"));
        outbound.setRoutingKeyExpression(parser.parseExpression("Payload.GatewayName"));
        return outbound;
    }

    // ------------------ Event Exchange and Event Rabbit Template

    @Bean
    public MessageConverter eventMessageConverter() {
        ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter();
        messageConverter.addDelegate("application/json", new Jackson2JsonMessageConverter());
        return messageConverter;
    }

    @Bean
    public RabbitTemplate eventRabbitTemplate(ConnectionFactory rabbitConnectionFactory,
            MessageConverter eventMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        // FIXME: message converter
        rabbitTemplate.setMessageConverter(eventMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public HeadersExchange platformEventsExchange() {
        return new HeadersExchange(platformEventsExchangeName);
    }

}
