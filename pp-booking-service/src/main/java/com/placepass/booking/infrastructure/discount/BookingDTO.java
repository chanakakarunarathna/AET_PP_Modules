package com.placepass.booking.infrastructure.discount;

public class BookingDTO {

    private String bookingId;
    
    private String bookingReference;
    
    private String vendorBookingRefId;
    
    private String customerId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getVendorBookingRefId() {
        return vendorBookingRefId;
    }

    public void setVendorBookingRefId(String vendorBookingRefId) {
        this.vendorBookingRefId = vendorBookingRefId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    
}
