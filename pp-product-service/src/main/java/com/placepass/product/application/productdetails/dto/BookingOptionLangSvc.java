package com.placepass.product.application.productdetails.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingOptionLangSvc {

    private String id;
    
    @JsonProperty("BookingOptionId")
    private String bookingOptionId;

    private List<KeyValuePair<String, String>> languageServices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<KeyValuePair<String, String>> getLanguageServices() {
        return languageServices;
    }

    public void setLanguageServices(List<KeyValuePair<String, String>> languageServices) {
        this.languageServices = languageServices;
    }

    public String getBookingOptionId() {
        return bookingOptionId;
    }

    public void setBookingOptionId(String bookingOptionId) {
        this.bookingOptionId = bookingOptionId;
    }
}
