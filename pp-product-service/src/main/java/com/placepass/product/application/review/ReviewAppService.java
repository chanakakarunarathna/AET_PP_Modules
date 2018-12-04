package com.placepass.product.application.review;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.product.application.review.dto.GetReviewsRS;
import com.placepass.product.infrastructure.RestClient;
import com.placepass.utils.vendorproduct.ProductHashGenerator;
import com.placepass.utils.vendorproduct.Vendor;
import com.placepass.utils.vendorproduct.VendorProduct;

@Service
public class ReviewAppService {

    @Autowired
    RestClient restClient;

    @Autowired
    ReviewProcessor reviewProcessor;

    @Autowired
    @Qualifier("ProductHashGenerator")
    private ProductHashGenerator productHashGenerator;

    @Value("${vendor.connector.outbound.amqp:false}")
    private boolean amqpOutboubd;

    public GetReviewsRS getProductReviews(String productid, int hitsPerPage, int pageNumber) {
        VendorProduct vc = null;
        try {
            vc = VendorProduct.getInstance(productid, productHashGenerator);

        } catch (Exception e) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PRODUCT_ID.toString(),
                    PlacePassExceptionCodes.INVALID_PRODUCT_ID.getDescription());
        }
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("productid", vc.getVendorProductID());
        urlVariables.put("hitsperpage", hitsPerPage);
        urlVariables.put("pagenumber", pageNumber);

        GetProductReviewsRS reviews = null;
        if (Vendor.VIATOR.name().equals(vc.getVendor().name()) && amqpOutboubd) {
            // FIXME: TEMP condition for amqp routing conversion

            GetProductReviewsRQ productReviewsRQ = new GetProductReviewsRQ();
            productReviewsRQ.setProductId(vc.getVendorProductID());
            productReviewsRQ.setHitsPerPage(hitsPerPage);
            productReviewsRQ.setPageNumber(pageNumber);

            reviews = reviewProcessor.getProductReviews(productReviewsRQ, vc.getVendor());
        } else {

            reviews = restClient.getProductReviews(urlVariables, vc.getVendor());
        }

        return ReviewTransformer.toGetReviewsRS(reviews, hitsPerPage);

    }

}
