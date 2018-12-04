package com.placepass.product.application.availability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.common.ResultType;
import com.placepass.product.infrastructure.VendorProductGateway;
import com.placepass.utils.vendorproduct.Vendor;

/**
 * Wraps outbound vendor connector calls and translations.
 * 
 * @author wathsala.w
 *
 */
@Service
public class AvailabilityProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AvailabilityProcessor.class);

    @Autowired
    private VendorProductGateway productGateway;

    public GetAvailabilityRS getAvailability(GetAvailabilityRQ request, Vendor vendor) {

        GetAvailabilityRS response = null;

        // Routingkey and exchange
        request.setGatewayName(vendor.name().toLowerCase());
        // request.setExchangeName(vendorBookingExchangeName);

        logger.debug("GetAvailabilityCRQ JSON : " + new Gson().toJson(request));

        try {

            response = productGateway.getAvailability(request);

        } catch (Exception e) {

            logger.error("Exception occured when sending GetAvailability Request", e);
            ResultType resultType = new ResultType();
            resultType.setCode(-1);
            resultType.setMessage("Internal error while connecting to vendor connector");
            response = new GetAvailabilityRS();
            response.setResultType(resultType);
        }

        // If response is null it could be a potential timeout.
        if (response == null) {

            logger.error(
                    "Null GetAvailability Response received from vendor connector. Possibly a timeout occured processing the GetAvailability Request");
            ResultType resultType = new ResultType();
            resultType.setCode(-1);
            resultType.setMessage("Internal error while connecting to vendor connector");
            response = new GetAvailabilityRS();
            response.setResultType(resultType);

        } else {
            logger.debug("GetAvailabilityCRS JSON: " + new Gson().toJson(response));
        }

        return response;
    }


}
