package com.gobe.connector.domain.gobe.price;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created on 8/7/2017.
 */
@Document(collection = "prices")
public class GobePrice {

    private String currency;

    private String endDate;

    private int minQuantity;

    private String price;

    private String startDate;

    private String tourId;

    private String cost;

    private Boolean revenueManageable;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Boolean getRevenueManageable() {
        return revenueManageable;
    }

    public void setRevenueManageable(Boolean revenueManageable) {
        this.revenueManageable = revenueManageable;
    }
}
