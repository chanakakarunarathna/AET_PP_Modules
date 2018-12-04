package com.placepass.connector.common.booking;

import com.placepass.connector.common.common.BaseRS;
import com.placepass.utils.bookingstatus.ConnectorBookingStatus;

public class BookingStatusRS extends BaseRS {

	private String referenceNumber;

	private ConnectorBookingStatus oldStatus;

	private ConnectorBookingStatus newStatus;

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

}
