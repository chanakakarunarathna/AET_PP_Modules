package com.placepass.connector.common.booking;

import com.placepass.utils.bookingstatus.ConnectorBookingStatus;

public class BookingStatusRQ {

    private String referenceNumber;
    
    private String bookerEmail;

	private ConnectorBookingStatus status;

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getBookerEmail() {
		return bookerEmail;
	}

	public void setBookerEmail(String bookerEmail) {
		this.bookerEmail = bookerEmail;
	}

	public ConnectorBookingStatus getStatus() {
		return status;
	}

	public void setStatus(ConnectorBookingStatus status) {
		this.status = status;
	}
	
}
