package com.placepass.connector.bemyguest.domain.bemyguest.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.placepass.connector.bemyguest.domain.bemyguest.product.BmgRegularPrice;

public class BmgPricesPerDate {

    @JsonProperty("regular")
    private BmgRegularPrice regular;

    @JsonProperty("promotion")
    private BmgPromotionPrice promotion;

    public BmgRegularPrice getRegular() {
        return regular;
    }

    public void setRegular(BmgRegularPrice regular) {
        this.regular = regular;
    }

    public BmgPromotionPrice getPromotion() {
        return promotion;
    }

    public void setPromotion(BmgPromotionPrice promotion) {
        this.promotion = promotion;
    }

}
