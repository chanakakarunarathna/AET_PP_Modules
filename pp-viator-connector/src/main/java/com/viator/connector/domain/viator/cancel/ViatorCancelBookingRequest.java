package com.viator.connector.domain.viator.cancel;

import java.util.List;

public class ViatorCancelBookingRequest {

    private Integer itineraryId;

    private String distributorRef;

    private List<ViatorCancelBookingReqItineraryItem> cancelItems;

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getDistributorRef() {
        return distributorRef;
    }

    public void setDistributorRef(String distributorRef) {
        this.distributorRef = distributorRef;
    }

    public List<ViatorCancelBookingReqItineraryItem> getCancelItems() {
        return cancelItems;
    }

    public void setCancelItems(List<ViatorCancelBookingReqItineraryItem> cancelItems) {
        this.cancelItems = cancelItems;
    }

}
