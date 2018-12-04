package com.placepass.connector.sendgrid.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MockMessageSender {

    private static final Logger log = LoggerFactory.getLogger(MockMessageSender.class);

    // private final RabbitTemplate rabbitTemplate;

    // @Autowired
    // public MockMessageSender(final RabbitTemplate rabbitTemplate) {
    // this.rabbitTemplate = rabbitTemplate;
    // }
    //
    // @Scheduled(fixedDelay = 100000L)
    // public void sendMessage() {
    // //eventNames: Welcome, Confirmation
    // //EventHeaders: [PARTNER_ID]
    // //EventAttributes: [RECEPIENT_EMAIL], [RECEPIENT_USERNAME], [RECEPIENT_FNAME], [RECEPIENT_LNAME]
    // PlacePassEvent message = new PlacePassEvent("324b82cf-abef-4d8d-b28b-627cbfac3bad", "BOOKING_CONFIRMED");
    //
    // message.getEventAttributes().put("recipient_email", "himaz.m@aeturnum.com");
    // message.getEventAttributes().put("recipient_username", "himaz");
    //
    // message.getEventAttributes().put("firstName", "Himaz");
    // message.getEventAttributes().put("fullName", "Mohamed Himaz");
    // message.getEventAttributes().put("orderId", "1234");
    // message.getEventAttributes().put("productTitle", "Kandy 2 Days Tour");
    // message.getEventAttributes().put("bookingDate", "Tue. May 30, 2017");
    // message.getEventAttributes().put("bookingTime", "4:15 pm");
    // message.getEventAttributes().put("location", "Colombo");
    // message.getEventAttributes().put("orderTotal", "$150");
    // message.getEventAttributes().put("adultCount", "2");
    // message.getEventAttributes().put("childCount", "0");
    // message.getEventAttributes().put("adultPrice", "$75");
    // message.getEventAttributes().put("childPrice", "");
    // message.getEventAttributes().put("adultTotal", "$150");
    // message.getEventAttributes().put("childTotal", "");
    //
    // log.info("Sending message...");
    // rabbitTemplate.convertAndSend(PlacepassEmailApplication.EXCHANGE_NAME, PlacepassEmailApplication.ROUTING_KEY,
    // message);
    // }
}