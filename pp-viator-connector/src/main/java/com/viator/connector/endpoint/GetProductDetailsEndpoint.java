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
import com.viator.connector.application.common.LogMessage;
import com.viator.connector.application.product.ProductService;
import com.placepass.connector.common.product.GetProductDetailsRQ;
import com.placepass.connector.common.product.GetProductDetailsRS;

@Deprecated
@Service
public class GetProductDetailsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(GetProductDetailsEndpoint.class);

    @Autowired
    private ProductService productService;

    @ServiceActivator(inputChannel = "getProductDetailsInputChannel", outputChannel = "amqpOutputChannel")
    public GetProductDetailsRS getProductDetails(GetProductDetailsRQ request) {

        Map<String, Object> logData = new HashMap<String, Object>();
        GetProductDetailsRS response = null;

        try {
            logger.info("VIATOR connector received request GetProductDetailsRQ: " + request.toString());

            Assert.hasText(request.getProductId(), "Product Id cannot be empty of null.");
            // Assert.hasText(request.getCurrencyCode(), "Currency code cannot be empty of null.");
            if (!StringUtils.hasText(request.getCurrencyCode())) {
                request.setCurrencyCode("USD");
            }
            
            logData.put("partner-id", request.getPartnerId());
            logData.put("product-id", request.getProductId());

            response = productService.getProductDetails(request);

            logger.info("VIATOR connector returning response GetProductDetailsRS: " + response.toString());
        } catch (Exception e) {

            logger.error(LogMessage.getLogMessage(logData, "Get Product Details"), e);
            response = new GetProductDetailsRS();
            response.getResultType().setCode(-1);
            response.getResultType().setMessage(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.name());
            return response;
        }

        return response;
    }
}
