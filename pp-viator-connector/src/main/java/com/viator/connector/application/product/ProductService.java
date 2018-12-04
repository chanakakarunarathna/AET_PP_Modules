package com.viator.connector.application.product;

import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetProductDetailsRQ;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;

public interface ProductService {

    public GetProductDetailsRS getProductDetails(GetProductDetailsRQ productDetailsRQ);

    public GetAvailabilityRS getAvailability(GetAvailabilityRQ availabilityRQ);

    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ productOptionsRQ);

    public GetProductReviewsRS getProductReviews(GetProductReviewsRQ productReviewsRQ);

}