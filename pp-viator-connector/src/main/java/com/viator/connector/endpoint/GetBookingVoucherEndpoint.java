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
import com.placepass.connector.common.booking.BookingVoucherRQ;
import com.placepass.connector.common.booking.BookingVoucherRS;

@Service
public class GetBookingVoucherEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GetBookingVoucherEndpoint.class);

    @Autowired
    private BookingService bookingService;

    @ServiceActivator(inputChannel = "getBookingVoucherInputChannel", outputChannel = "amqpOutputChannel")
    public BookingVoucherRS getVoucherDetails(BookingVoucherRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        BookingVoucherRS response = null;

        try {
            logger.info("VIATOR connector received request BookingVoucherRQ: " + request.toString());

            logData.put("reference-number", request.getReferenceNumber());
            // logData.put("partner-id", bookingRequest.getPartnerId());
            // logData.put("user-id", bookingRequest.getUserId());

            response = bookingService.getVoucherDetails(request);

            logger.info("VIATOR connector returning response BookingVoucherRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Get Voucher Details"), e);
            response = new BookingVoucherRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
