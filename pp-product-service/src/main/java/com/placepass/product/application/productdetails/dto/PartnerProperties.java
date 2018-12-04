package com.placepass.product.application.productdetails.dto;

import java.util.List;

public class PartnerProperties {
    private String id;

    private List<LoyaltyDetail> loyaltyDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LoyaltyDetail> getLoyaltyDetails() {
        return loyaltyDetails;
    }

    public void setLoyaltyDetails(List<LoyaltyDetail> loyaltyDetails) {
        this.loyaltyDetails = loyaltyDetails;
    }
}
