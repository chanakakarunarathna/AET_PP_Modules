package com.placepass.connector.bemyguest.domain.bemyguest.availability;

public class BmgAvailability {

    private String date;

    private BmgPrice price;

    private boolean soldOut;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BmgPrice getPrice() {
        return price;
    }

    public void setPrice(BmgPrice price) {
        this.price = price;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

}
