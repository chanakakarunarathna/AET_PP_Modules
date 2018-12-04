package com.placepass.product.application.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.placepass.connector.common.common.ResultType;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.product.infrastructure.VendorProductGateway;
import com.placepass.utils.vendorproduct.Vendor;

/**
 * Wraps outbound vendor connector calls and translations.
 * 
 * @author wathsala.w
 *
 */
@Service
public class ReviewProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ReviewProcessor.class);

    @Autowired
    private VendorProductGateway productGateway;

    public GetProductReviewsRS getProductReviews(GetProductReviewsRQ request, Vendor vendor) {

        GetProductReviewsRS response = null;

        // Routingkey and exchange
        request.setGatewayName(vendor.name().toLowerCase());
        // request.setExchangeName(vendorBookingExchangeName);

        logger.debug("GetProductReviewsCRQ JSON : " + new Gson().toJson(request));

        try {

            response = productGateway.getProductReviews(request);

        } catch (Exception e) {

            logger.error("Exception occured when sending GetProductReviews Request", e);
            ResultType resultType = new ResultType();
            resultType.setCode(-1);
            resultType.setMessage("Internal error while connecting to vendor connector");
            response = new GetProductReviewsRS();
            response.setResultType(resultType);
        }

        // If response is null it could be a potential timeout.
        if (response == null) {

            logger.error(
                    "Null GetProductReviews Response received from vendor connector. Possibly a timeout occured processing the GetProductReviews Request");
            ResultType resultType = new ResultType();
            resultType.setCode(-1);
            resultType.setMessage("Internal error while connecting to vendor connector");
            response = new GetProductReviewsRS();
            response.setResultType(resultType);

        } else {
            logger.debug("GetProductReviewsCRS JSON: " + new Gson().toJson(response));
        }

        return response;
    }

}
