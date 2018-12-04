package com.placepass.connector.common.booking;

public class BookingItem {

    private String itemId;
    
    private String itemRef;
    
    private boolean isCancellable;

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

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean isCancellable) {
        this.isCancellable = isCancellable;
    }
    
}
