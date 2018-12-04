package com.placepass.booking.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;

@Configuration
@MessagingGateway(defaultRequestChannel = "amqpOutboundChannel", defaultReplyTimeout = "${rabbitmq.wait.for.reply.timeout}")
public interface ConnectorGateway {

    Object sendRequest(Object data);

}