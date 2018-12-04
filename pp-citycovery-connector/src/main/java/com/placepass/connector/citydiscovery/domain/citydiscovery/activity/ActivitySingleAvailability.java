package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

public class ActivitySingleAvailability {

    private String date;

    private boolean soldOut;

    private ActivityAvailabilityPrice price;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public ActivityAvailabilityPrice getPrice() {
        return price;
    }

    public void setPrice(ActivityAvailabilityPrice price) {
        this.price = price;
    }

}
