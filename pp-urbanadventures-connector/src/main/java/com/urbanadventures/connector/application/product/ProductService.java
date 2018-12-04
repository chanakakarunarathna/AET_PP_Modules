package com.urbanadventures.connector.application.product;

import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;

public interface ProductService {

    public GetProductDetailsRS getProductDetails(String productId);

    public GetAvailabilityRS getAvailability(String productId, String month, String year, String fromDate,
            String toDate);

    public GetProductOptionsRS getProductOptions(String productId, String date);

    public GetProductReviewsRS getProductReviews();

}