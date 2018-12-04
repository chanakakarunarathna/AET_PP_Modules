package com.placepass.batchprocessor.application.booking.domain;

import java.util.Map;

import com.placepass.utils.bookingstatus.ConnectorBookingStatus;

public class GetBookingStatusRS {

    private ConnectorBookingStatus newStatus;

    private String vendor;

    private String vendorBookingRefId;

    private String bookerEmail;

    private String status;

    private String bookingId;

    private String partnerId;

    private boolean bookingStatusUpdated;

    private boolean manualInterventionRequired;
    
    private String message;

    public ConnectorBookingStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ConnectorBookingStatus newStatus) {
        this.newStatus = newStatus;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public boolean isBookingStatusUpdated() {
        return bookingStatusUpdated;
    }

    public void setBookingStatusUpdated(boolean bookingStatusUpdated) {
        this.bookingStatusUpdated = bookingStatusUpdated;
    }

    public boolean isManualInterventionRequired() {
		return manualInterventionRequired;
	}

	public void setManualInterventionRequired(boolean manualInterventionRequired) {
		this.manualInterventionRequired = manualInterventionRequired;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
