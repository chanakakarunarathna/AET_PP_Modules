package com.viator.connector.domain.viator.status;


public class ViatorItemSummary {

	private int sortOrder;
	private ViatorBookingStatus bookingStatus;
	private int itineraryId;
	private String travelDate;
	private String distributorItemRef;
	private int itemId;
	
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public ViatorBookingStatus getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(ViatorBookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public int getItineraryId() {
		return itineraryId;
	}
	public void setItineraryId(int itineraryId) {
		this.itineraryId = itineraryId;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public String getDistributorItemRef() {
		return distributorItemRef;
	}
	public void setDistributorItemRef(String distributorItemRef) {
		this.distributorItemRef = distributorItemRef;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	
}
