package com.placepass.product.application.hoteldetails.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetHotelDetailsRS {

    private List<HotelDetail> hotelDetails;

    private boolean freeText;

    public List<HotelDetail> getHotelDetails() {
        return hotelDetails;
    }

    public void setHotelDetails(List<HotelDetail> hotelDetails) {
        this.hotelDetails = hotelDetails;
    }

    public boolean isFreeText() {
        return freeText;
    }

    public void setFreeText(boolean freeText) {
        this.freeText = freeText;
    }

   

    

}
