package com.gobe.connector.application.product;

import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;

public interface ProductService {

    public GetProductDetailsRS getProductDetails(String partnerId, String productId, String currencyCode,
                                                 boolean excludeTourGrade, boolean showUnavailable);

    public GetAvailabilityRS getAvailability(String productid, String month, String year);

    public GetProductOptionsRS getProductOptions(String productid, String date);

    public GetProductReviewsRS getProductReviews(String productid, int hitsPerPage, int pageNumber);

}