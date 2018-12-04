package com.placepass.connector.bemyguest.domain.bemyguest.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgValidity {

    @JsonProperty("type")
    private String type;

    @JsonProperty("days")
    private Integer days;

    @JsonProperty("date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
