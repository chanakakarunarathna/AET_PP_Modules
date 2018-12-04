package com.placepass.booking.domain.booking.cancel;

public class CancelledBookingItem {

    private String cancellationResponseStatusCode;

    private String cancellationResponseDescription;

    private String itemId;

    private String itemRef;

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

    public String getItemRef() {
        return itemRef;
    }

    public void setItemRef(String itemRef) {
        this.itemRef = itemRef;
    }

}
