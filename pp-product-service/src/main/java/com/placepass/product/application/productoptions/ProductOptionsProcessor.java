package com.placepass.product.application.productoptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.product.infrastructure.VendorProductGateway;
import com.placepass.utils.vendorproduct.Vendor;

/**
 * Wraps outbound vendor connector calls and translations.
 * 
 * @author wathsala.w
 *
 */
@Service
public class ProductOptionsProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ProductOptionsProcessor.class);

    @Autowired
    private VendorProductGateway productGateway;

    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ request, Vendor vendor) {

        GetProductOptionsRS response = null;

        // Routingkey and exchange
        request.setGatewayName(vendor.name().toLowerCase());
        // request.setExchangeName(vendorBookingExchangeName);

        logger.debug("GetProductOptionsCRQ JSON : " + new Gson().toJson(request));

        try {

            response = productGateway.getProductOptions(request);

        } catch (Exception e) {

            logger.error("Exception occured when sending GetProductOptions Request", e);
            ResultType resultType = new ResultType();
            resultType.setCode(-1);
            resultType.setMessage("Internal error while connecting to vendor connector");
            response = new GetProductOptionsRS();
            response.setResultType(resultType);
        }

        // If response is null it could be a potential timeout.
        if (response == null) {

            logger.error(
                    "Null GetProductOptions Response received from vendor connector. Possibly a timeout occured processing the GetProductOptions Request");
            ResultType resultType = new ResultType();
            resultType.setCode(-1);
            resultType.setMessage("Internal error while connecting to vendor connector");
            response = new GetProductOptionsRS();
            response.setResultType(resultType);

        } else {
            logger.debug("GetProductOptionsCRS JSON: " + new Gson().toJson(response));
        }

        return response;
    }


}
