package com.gobe.connector.domain.gobe.book;

/**
 * Created on 8/8/2017.
 */
public class CancellationDetail {

    private String tourId;

    private String startDateTime;

    private int quantity;

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
