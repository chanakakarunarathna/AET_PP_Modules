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
import com.placepass.connector.common.booking.GetProductPriceRQ;
import com.placepass.connector.common.booking.GetProductPriceRS;

@Service
public class GetProductPriceEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GetProductPriceEndpoint.class);

    @Autowired
    private BookingService bookingService;

    @ServiceActivator(inputChannel = "getProductPriceInputChannel", outputChannel = "amqpOutputChannel")
    public GetProductPriceRS getProductPrice(GetProductPriceRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        GetProductPriceRS response = null;

        try {
            logger.info("VIATOR connector received request GetProductPriceRQ: " + request.toString());

            logData.put("product-id", request.getProductId());
            logData.put("product-option-id", request.getProductOptionId());
            logData.put("vendor-product-id", request.getVendorProductId());
            logData.put("vendor-product-option-id", request.getVendorProductOptionId());

            response = bookingService.getProductPrice(request);

            logger.info("VIATOR connector returning response GetProductPriceRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Get Product Price"), e);
            response = new GetProductPriceRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
