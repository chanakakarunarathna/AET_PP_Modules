package com.placepass.connector.sendgrid.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class EventApplicationServiceExperiment {

    private static final Logger log = LoggerFactory.getLogger(EventApplicationServiceExperiment.class);

    // @RabbitListener(queues = "#{@eventQueue}")
    // public void processMessage(Message message) {
    // log.info("############################");
    // log.info("Message received EVENT class: " + message.getClass().getName());
    // log.info("Message received EVENT: " + message);
    // }

    @RabbitListener(queues = "#{@testQueue}")
    public void testQueueMessage(Message message) {
        log.info("############################");
        log.info("Message received TEST class: " + message.getClass().getName());
        log.info("Message received TEST: " + message);
    }
}
