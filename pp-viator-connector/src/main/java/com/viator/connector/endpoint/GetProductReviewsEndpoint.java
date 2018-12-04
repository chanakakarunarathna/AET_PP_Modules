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
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;

@Service
public class GetProductReviewsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GetProductReviewsEndpoint.class);

    @Autowired
    private ProductService productService;

    @ServiceActivator(inputChannel = "getProductReviewsInputChannel", outputChannel = "amqpOutputChannel")
    public GetProductReviewsRS getProductReviews(GetProductReviewsRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        GetProductReviewsRS response = null;

        try {
            logger.info("VIATOR connector received request GetProductReviewsRQ: " + request.toString());

            Assert.hasText(request.getProductId(), "Product Id cannot be empty of null.");
            
            // logData.put("partner-id", request.getPartnerId());
            logData.put("product-id", request.getProductId());

            response = productService.getProductReviews(request);

            logger.info("VIATOR connector returning response GetProductReviewsRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Get Product Reviews"), e);
            response = new GetProductReviewsRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
