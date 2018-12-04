package com.viator.connector.domain.viator.cancel;

public class ViatorCancelBookingReqItineraryItem {

    private Integer itemId;
    
    private String distributorItemRef;
    
    private String cancelCode;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getDistributorItemRef() {
        return distributorItemRef;
    }

    public void setDistributorItemRef(String distributorItemRef) {
        this.distributorItemRef = distributorItemRef;
    }

    public String getCancelCode() {
        return cancelCode;
    }

    public void setCancelCode(String cancelCode) {
        this.cancelCode = cancelCode;
    }
    
}
