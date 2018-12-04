package com.placepass.booking.application.booking.dto;

public class ProcessBookingStatusRQ {

	private String vendor;
	
	private String vendorBookingRefId;
	
	private String bookerEmail;
	
	private String status;

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
	
}
