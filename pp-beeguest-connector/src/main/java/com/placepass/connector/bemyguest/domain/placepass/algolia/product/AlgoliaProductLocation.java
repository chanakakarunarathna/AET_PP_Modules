package com.placepass.connector.bemyguest.domain.placepass.algolia.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaProductLocation {
    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }
    public void setLat(Double d) {
        this.lat = d;
    }
    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
}
