package com.viator.connector.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.placepass.exutil.PlacePassExceptionCodes;
import com.viator.connector.application.booking.BookingService;
import com.viator.connector.application.common.LogMessage;
import com.placepass.connector.common.booking.MakeBookingRQ;
import com.placepass.connector.common.booking.MakeBookingRS;

@Service
public class MakeBookingEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(MakeBookingEndpoint.class);

    @Autowired
    private BookingService bookingService;

    @ServiceActivator(inputChannel = "makeBookingInputChannel", outputChannel = "amqpOutputChannel")
    public MakeBookingRS makeBooking(MakeBookingRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        MakeBookingRS response = null;

        try {
            logger.info("VIATOR connector received request MakeBookingRQ: " + request.toString());

            logData.put("booking-id", request.getBookingId());
            // logData.put("partner-id", bookingRequest.getPartnerId());
            // logData.put("user-id", bookingRequest.getUserId());

            response = bookingService.makeBooking(request);

            logger.info("VIATOR connector returning response MakeBookingRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Make Booking"), e);
            response = new MakeBookingRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
