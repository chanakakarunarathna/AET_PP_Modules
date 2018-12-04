package com.placepass.userservice;

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
    
    @Value("${sendgrid.connectionmode:REST}")
    private String sendgridConnectionMode;
    
    private static final String REST = "REST";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        //Disable heartbeat when REST mode is enabled
        if (REST.equals(sendgridConnectionMode)){
        	connectionFactory.setRequestedHeartBeat(0);
        }
        
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public MessageConverter messageConverter() {
        ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter();
        messageConverter.addDelegate("application/json", new Jackson2JsonMessageConverter());
        return messageConverter;
    }

    @Bean
    public RabbitTemplate eventRabbitTemplate(ConnectionFactory rabbitConnectionFactory,
            MessageConverter eventMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(eventMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public HeadersExchange platformEventsExchange() {
        return new HeadersExchange(platformEventsExchangeName);
    }

}
