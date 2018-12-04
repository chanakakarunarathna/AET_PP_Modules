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
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;

@Service
public class GetProductOptionsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GetProductOptionsEndpoint.class);

    @Autowired
    private ProductService productService;

    @ServiceActivator(inputChannel = "getProductOptionsInputChannel", outputChannel = "amqpOutputChannel")
    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        GetProductOptionsRS response = null;

        try {
            logger.info("VIATOR connector received request GetProductOptionsRQ: " + request.toString());

            Assert.hasText(request.getProductId(), "Product Id cannot be empty of null.");
            Assert.hasText(request.getBookingDate(), "BookingDate cannot be empty of null.");

            logData.put("product-id", request.getProductId());
            logData.put("date", request.getBookingDate());

            response = productService.getProductOptions(request);

            logger.info("VIATOR connector returning response GetProductOptionsRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Get Product Options"), e);
            response = new GetProductOptionsRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
