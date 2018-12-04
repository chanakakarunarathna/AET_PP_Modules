package com.viator.connector.domain.placepass.algolia.product;

import java.util.List;

public class AlgoliaPartnerProperties {
    private String id;

    private List<AlgoliaLoyaltyDetail> loyaltyDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AlgoliaLoyaltyDetail> getLoyaltyDetails() {
        return loyaltyDetails;
    }

    public void setLoyaltyDetails(List<AlgoliaLoyaltyDetail> loyaltyDetails) {
        this.loyaltyDetails = loyaltyDetails;
    }
}
