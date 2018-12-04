package com.viator.connector.domain.viator.status;

import java.util.List;

public class ViatorStatusResponseInfo {

	private int sortOrder;
	
	private ViatorBookingStatus bookingStatus;
	
	private List<ViatorItemSummary> itemSummaries;
	
	private int itineraryId;
	
	private String bookingDate;
	
	private String distributorRef;
	
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
	public List<ViatorItemSummary> getItemSummaries() {
		return itemSummaries;
	}
	public void setItemSummaries(List<ViatorItemSummary> itemSummaries) {
		this.itemSummaries = itemSummaries;
	}
	public int getItineraryId() {
		return itineraryId;
	}
	public void setItineraryId(int itineraryId) {
		this.itineraryId = itineraryId;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getDistributorRef() {
		return distributorRef;
	}
	public void setDistributorRef(String distributorRef) {
		this.distributorRef = distributorRef;
	}

}
