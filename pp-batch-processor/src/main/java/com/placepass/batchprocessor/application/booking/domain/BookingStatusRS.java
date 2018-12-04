package com.placepass.batchprocessor.application.booking.domain;

import java.util.Map;

import com.placepass.utils.bookingstatus.ConnectorBookingStatus;

public class BookingStatusRS {

    private String referenceNumber;

    private ConnectorBookingStatus oldStatus;

    private ConnectorBookingStatus newStatus;

    private boolean bookingStatusUpdated;
    
	private boolean manualInterventionRequired;
	
	private String message;

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public ConnectorBookingStatus getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(ConnectorBookingStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public ConnectorBookingStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(ConnectorBookingStatus newStatus) {
        this.newStatus = newStatus;
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
