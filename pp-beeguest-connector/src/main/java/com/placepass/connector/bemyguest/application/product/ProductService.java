package com.placepass.connector.bemyguest.application.product;

import com.placepass.connector.common.product.GetAvailabilityRQ;
import com.placepass.connector.common.product.GetAvailabilityRS;
import com.placepass.connector.common.product.GetCancellationRulesRS;
import com.placepass.connector.common.product.GetProductDetailsRS;
import com.placepass.connector.common.product.GetProductOptionsRQ;
import com.placepass.connector.common.product.GetProductOptionsRS;
import com.placepass.connector.common.product.GetProductReviewsRS;

public interface ProductService {

    public GetProductDetailsRS getProductDetails(String productId);

    public GetAvailabilityRS getAvailability(GetAvailabilityRQ rq);

    public GetProductOptionsRS getProductOptions(GetProductOptionsRQ productOptionsRQ);

    //public GetAvailabilityRS getAvailability(String productId, String month, String year);

    public GetProductReviewsRS getProductReviews(String productid, int hitsPerPage, int pageNumber);

    public GetCancellationRulesRS getProductCancellationRules(String productId);

}