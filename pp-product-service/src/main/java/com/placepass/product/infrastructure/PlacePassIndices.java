package com.placepass.product.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlacePassIndices {
    /* PlacePass Indices */
    @Value("${pp.productCatalog.index}")
    private String ppProductCatalog;

    @Value("${pp.location.index}")
    private String ppLocations;

    @Value("${viator.hotel.locations}")
    private String viatorHotelLocations;

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

    public String getViatorHotelLocations() {
        return viatorHotelLocations;
    }

    public void setViatorHotelLocations(String viatorHotelLocations) {
        this.viatorHotelLocations = viatorHotelLocations;
    }
}
