package com.viator.connector.domain.viator.cancel;

public class ViatorCancelledBookingResCancelItem {

    private String cancellationResponseStatusCode;
    
    private String cancellationResponseDescription;
    
    private String itemId;
    
    private String distributorItemRef;

    public String getCancellationResponseStatusCode() {
        return cancellationResponseStatusCode;
    }

    public void setCancellationResponseStatusCode(String cancellationResponseStatusCode) {
        this.cancellationResponseStatusCode = cancellationResponseStatusCode;
    }

    public String getCancellationResponseDescription() {
        return cancellationResponseDescription;
    }

    public void setCancellationResponseDescription(String cancellationResponseDescription) {
        this.cancellationResponseDescription = cancellationResponseDescription;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDistributorItemRef() {
        return distributorItemRef;
    }

    public void setDistributorItemRef(String distributorItemRef) {
        this.distributorItemRef = distributorItemRef;
    }
    
}
