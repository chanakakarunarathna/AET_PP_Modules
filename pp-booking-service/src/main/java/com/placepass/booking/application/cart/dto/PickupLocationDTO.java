package com.placepass.booking.application.cart.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "PickupLocation")
public class PickupLocationDTO {

    private String id;

    private String locationName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
