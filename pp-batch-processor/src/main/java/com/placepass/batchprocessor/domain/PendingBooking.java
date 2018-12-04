package com.placepass.batchprocessor.domain;

import java.time.Instant;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PendingBooking")
public class PendingBooking {

    @Id
    private String id;

    private String bookingId;

    private String partnerId;

    private String bookingReference;

    private String vendorBookingRefId;

    private String bookerEmail;

    private String bookingStatus;

    private String vendor;

    private String latestBookingStatus;

    private String message;

    private ProcessStatus processStatus;

    private boolean isBookingStatusChanged;

    private Instant createdTime;

    private Instant updatedTime;

    private boolean interventionRequired;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getLatestBookingStatus() {
        return latestBookingStatus;
    }

    public void setLatestBookingStatus(String latestBookingStatus) {
        this.latestBookingStatus = latestBookingStatus;
    }

    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    public boolean isBookingStatusChanged() {
        return isBookingStatusChanged;
    }

    public void setBookingStatusChanged(boolean isBookingStatusChanged) {
        this.isBookingStatusChanged = isBookingStatusChanged;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }
    

	public boolean isInterventionRequired() {
		return interventionRequired;
	}

	public void setInterventionRequired(boolean interventionRequired) {
		this.interventionRequired = interventionRequired;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
