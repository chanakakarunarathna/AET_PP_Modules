package com.viator.connector.domain.viator.cancel;

import java.util.List;

public class ViatorCancelBookingResDetails {

    private String itineraryId;
    
    private List<ViatorCancelledBookingResCancelItem> cancelItems;
    
    private String distributorRef;

    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public List<ViatorCancelledBookingResCancelItem> getCancelItems() {
        return cancelItems;
    }

    public void setCancelItems(List<ViatorCancelledBookingResCancelItem> cancelItems) {
        this.cancelItems = cancelItems;
    }

    public String getDistributorRef() {
        return distributorRef;
    }

    public void setDistributorRef(String distributorRef) {
        this.distributorRef = distributorRef;
    }
    
}
