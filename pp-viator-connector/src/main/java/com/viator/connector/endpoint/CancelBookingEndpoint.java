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
import com.placepass.connector.common.booking.CancelBookingRQ;
import com.placepass.connector.common.booking.CancelBookingRS;

@Service
public class CancelBookingEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(CancelBookingEndpoint.class);

    @Autowired
    private BookingService bookingService;

    @ServiceActivator(inputChannel = "cancelBookingInputChannel", outputChannel = "amqpOutputChannel")
    public CancelBookingRS cancelBooking(CancelBookingRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        CancelBookingRS response = null;

        try {

            logger.info("VIATOR connector received request CancelBookingRQ: " + request.toString());

            logData.put("booking-id", request.getBookingId());

            // logData.put("partner-id", bookingRequest.getPartnerId());
            // logData.put("user-id", bookingRequest.getUserId());

            // response = bookingService.makeBooking(request);

            logger.info("VIATOR connector returning response CancelBookingRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Cancel Booking"), e);
            response = new CancelBookingRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
