package com.viator.connector.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.placepass.exutil.PlacePassExceptionCodes;
import com.viator.connector.application.booking.BookingService;
import com.viator.connector.application.common.LogMessage;
import com.placepass.connector.common.booking.GetBookingQuestionsRQ;
import com.placepass.connector.common.booking.GetBookingQuestionsRS;

@Service
public class GetBookingQuestionsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GetBookingQuestionsEndpoint.class);

    @Autowired
    private BookingService bookingService;

    @ServiceActivator(inputChannel = "getBookingQuestionsInputChannel", outputChannel = "amqpOutputChannel")
    public GetBookingQuestionsRS getProductPrice(GetBookingQuestionsRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        GetBookingQuestionsRS response = null;

        try {
            logger.info("VIATOR connector received request GetBookingQuestionsRQ: " + request.toString());

            Assert.hasText(request.getProductId(), "Product Id cannot be empty of null.");
            // Assert.hasText(request.getCurrencyCode(), "Currency code cannot be empty of null.");
            if (!StringUtils.hasText(request.getCurrencyCode())) {
                request.setCurrencyCode("USD");
            }

            logData.put("product-id", request.getProductId());
            logData.put("partner-id", request.getPartnerId());

            response = bookingService.getBookingQuestions(request);

            logger.info("VIATOR connector returning response GetBookingQuestionsRS: " + response.toString());
        } catch (IllegalArgumentException e) {
            logger.error(LogMessage.getLogMessage(logData, "Get Booking Questions"), e);
            response = new GetBookingQuestionsRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.BAD_REQUEST.name());
            return response;
        } catch (Exception e) {
            logger.error(LogMessage.getLogMessage(logData, "Get Booking Questions"), e);
            response = new GetBookingQuestionsRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
