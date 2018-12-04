package com.placepass.batchprocessor.application.booking.domain;

import org.springframework.data.annotation.Id;

public class PendingBooking {

    @Id
    private String id;

    private String partnerId;

    private String bookingReference;

    private String vendor;

    private String vendorBookingRefId;

    private String bookerEmail;

    private PlatformStatus bookingStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendorBookingRefId() {
        return vendorBookingRefId;
    }

    public void setVendorBookingRefId(String vendorBookingRefId) {
        this.vendorBookingRefId = vendorBookingRefId;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public PlatformStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(PlatformStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

}
