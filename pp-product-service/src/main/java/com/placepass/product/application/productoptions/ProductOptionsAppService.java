package com.placepass.product.application.productoptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.product.application.productoptions.dto.GetProductOptionsRS;
import com.placepass.product.infrastructure.RestClient;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.utils.vendorproduct.VendorProduct;

@Service
public class ProductOptionsAppService {

    @Autowired
    RestClient restClient;

    @Autowired
    ProductOptionsProcessor productOptionsProcessor;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Value("${vendor.connector.outbound.amqp:false}")
    private boolean amqpOutboubd;

    public GetProductOptionsRS getProductOptions(String productId, String date) {
        VendorProduct vc = null;
        try {
            vc = VendorProduct.getInstance(productId, productHashGenerator);
        } catch (Exception e) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                    PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
        }
        
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("productid", vc.getVendorProductID());
        urlVariables.put("date", date);

        com.placepass.connector.common.product.GetProductOptionsRS productOptions = null;
        if (Vendor.VIATOR.name().equals(vc.getVendor().name()) && amqpOutboubd) {
            // FIXME: TEMP condition for amqp routing conversion

            GetProductOptionsRQ productOptionsRQ = new GetProductOptionsRQ();
            productOptionsRQ.setProductId(vc.getVendorProductID());
            productOptionsRQ.setBookingDate(date);

            productOptions = productOptionsProcessor.getProductOptions(productOptionsRQ, vc.getVendor());
        } else {

            productOptions = restClient.getProductOptions(urlVariables, vc.getVendor());
        }

        return ProductOptionsTransformer.toGetProductOptionsRS(productHashGenerator, vc, productOptions);

    }

}
