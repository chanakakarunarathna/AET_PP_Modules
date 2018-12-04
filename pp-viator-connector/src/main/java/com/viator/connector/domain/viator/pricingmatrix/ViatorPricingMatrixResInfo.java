package com.viator.connector.domain.viator.pricingmatrix;

import java.util.List;

public class ViatorPricingMatrixResInfo {

	private String bookingMonth;

	private String pricingUnit;

	private List<ViatorPricingMatrixResDate> dates;

	public String getBookingMonth() {
		return bookingMonth;
	}

	public void setBookingMonth(String bookingMonth) {
		this.bookingMonth = bookingMonth;
	}

	public String getPricingUnit() {
		return pricingUnit;
	}

	public void setPricingUnit(String pricingUnit) {
		this.pricingUnit = pricingUnit;
	}

	public List<ViatorPricingMatrixResDate> getDates() {
		return dates;
	}

	public void setDates(List<ViatorPricingMatrixResDate> dates) {
		this.dates = dates;
	}
}
