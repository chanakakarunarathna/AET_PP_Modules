package com.placepass.connector.citydiscovery.application.product;

import com.placepass.connector.citydiscovery.domain.citydiscovery.xml.ClsProductDetailsInfoRS;
import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetCancellationRulesRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRQ;
import com.placepass.connector.common.product.GetProductReviewsRS;

public interface ProductService {

    public ClsProductDetailsInfoRS getProductDetails(String productId);

    public GetAvailabilityRS getAvailability(GetAvailabilityRQ availabilityRQ);

    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ productOptionsRQ);

    public GetProductReviewsRS getProductReviews(GetProductReviewsRQ productReviewRQ);

    public GetCancellationRulesRS getProductCancellationRules(String productId);

}