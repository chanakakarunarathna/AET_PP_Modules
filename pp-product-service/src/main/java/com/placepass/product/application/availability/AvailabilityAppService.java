package com.placepass.product.application.availability;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.product.application.availability.dto.GetAvailabilityRS;
import com.placepass.product.infrastructure.RestClient;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.utils.vendorproduct.VendorProduct;

@Service
public class AvailabilityAppService {

    @Autowired
    RestClient restClient;

    @Autowired
    AvailabilityProcessor availabilityProcessor;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Value("${vendor.connector.outbound.amqp:false}")
    private boolean amqpOutboubd;

    public GetAvailabilityRS getAvailability(String productId, String month, String year) {

        VendorProduct vc = null;
        try {
            vc = VendorProduct.getInstance(productId, productHashGenerator);
        } catch (Exception e) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                    PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
        }

        com.placepass.connector.common.product.GetAvailabilityRS availabilities = null;
        if (Vendor.VIATOR.name().equals(vc.getVendor().name()) && amqpOutboubd) {
            // FIXME: TEMP condition for amqp routing conversion

            GetAvailabilityRQ availabilityRQ = new GetAvailabilityRQ();
            availabilityRQ.setProductId(vc.getVendorProductID());
            availabilityRQ.setMonth(month);
            availabilityRQ.setYear(year);

            availabilities = availabilityProcessor.getAvailability(availabilityRQ, vc.getVendor());
        } else {

            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("productid", vc.getVendorProductID());
            urlVariables.put("month", month);
            urlVariables.put("year", year);
            availabilities = restClient.getAvailability(urlVariables, vc.getVendor());

        }

        return AvailabilityTransformer.toGetAvailabilityRS(availabilities);

    }

}
