package com.placepass.batchprocessor.infrastructure.service;

import java.util.List;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

import com.placepass.batchprocessor.application.booking.domain.PendingBookingRQ;
import com.placepass.batchprocessor.domain.PendingBooking;

@MessagingGateway(defaultRequestChannel = "pendingbookings.request.channel")
public interface GetPendingBookingsGatewayService {

    public List<PendingBooking> getPendingBookings(@Payload PendingBookingRQ pendingBookingRQ);

}
