package com.placepass.batchprocessor.infrastructure.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRQ;
import com.placepass.batchprocessor.application.booking.domain.BookingStatusRS;

@MessagingGateway(defaultRequestChannel = "bookingstatus.request.channel")
public interface GetBookingStatusGatewayService {

    public BookingStatusRS getBookingStatus(@Payload BookingStatusRQ bookingStatusRQ);

}
