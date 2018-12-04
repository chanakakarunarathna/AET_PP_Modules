package com.viator.connector.domain.viator.pricingmatrix;

import java.util.List;

public class ViatorPricingMatrixResDate {

	private String sortOrder;

	private String bookingDate;

	private List<ViatorPricingMatrixResTourGrade> tourGrades;

	private String callForLastMinAvailability;

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public List<ViatorPricingMatrixResTourGrade> getTourGrades() {
		return tourGrades;
	}

	public void setTourGrades(List<ViatorPricingMatrixResTourGrade> tourGrades) {
		this.tourGrades = tourGrades;
	}

	public String getCallForLastMinAvailability() {
		return callForLastMinAvailability;
	}

	public void setCallForLastMinAvailability(String callForLastMinAvailability) {
		this.callForLastMinAvailability = callForLastMinAvailability;
	}
}
