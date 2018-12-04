package com.placepass.connector.bemyguest.placepass.algolia.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlacePassIndices {
    /* PlacePass Indices */
    @Value("${pp.productDetails.index}")
    private String ppProductDetails;

    @Value("${pp.productCatalog.index}")
    private String ppProductCatalog;

    @Value("${pp.location.index}")
    private String ppLocations;

    public String getPpProductDetails() {
        return ppProductDetails;
    }

    public void setPpProductDetails(String ppProductDetails) {
        this.ppProductDetails = ppProductDetails;
    }

    public String getPpProductCatalog() {
        return ppProductCatalog;
    }

    public void setPpProductCatalog(String ppProductCatalog) {
        this.ppProductCatalog = ppProductCatalog;
    }

    public String getPpLocations() {
        return ppLocations;
    }

    public void setPpLocations(String ppLocations) {
        this.ppLocations = ppLocations;
    }
}
