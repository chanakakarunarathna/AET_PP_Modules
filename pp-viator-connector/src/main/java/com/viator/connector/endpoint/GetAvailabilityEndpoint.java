package com.viator.connector.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.placepass.exutil.PlacePassExceptionCodes;
import com.viator.connector.application.common.LogMessage;
import com.viator.connector.application.product.ProductService;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;

@Service
public class GetAvailabilityEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GetAvailabilityEndpoint.class);

    @Autowired
    private ProductService productService;

    @ServiceActivator(inputChannel = "getAvailabilityInputChannel", outputChannel = "amqpOutputChannel")
    public GetAvailabilityRS getAvailability(GetAvailabilityRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        GetAvailabilityRS response = null;

        try {
            logger.info("VIATOR connector received request GetAvailabilityRQ: " + request.toString());

            Assert.hasText(request.getProductId(), "Product Id cannot be empty of null.");
            Assert.hasText(request.getYear(), "Year cannot be empty of null.");
            Assert.hasText(request.getMonth(), "Month cannot be empty of null.");

            logData.put("product-id", request.getProductId());
            logData.put("month", request.getMonth());
            logData.put("year", request.getYear());

            response = productService.getAvailability(request);

            logger.info("VIATOR connector returning response GetAvailabilityRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Get Availability"), e);
            response = new GetAvailabilityRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
