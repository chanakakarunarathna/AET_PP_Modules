package com.viator.connector.domain.viator.pricingmatrix;

import java.util.List;

public class ViatorPricingMatrixResPricingMatrix {

	private Integer sortOrder;
	
	private String pricingUnit;
	
	private String bookingDate;
	
	private List<ViatorPricingMatrixResAgeBandPrice> ageBandPrices;

	public List<ViatorPricingMatrixResAgeBandPrice> getAgeBandPrices() {
		return ageBandPrices;
	}

	public void setAgeBandPrices(List<ViatorPricingMatrixResAgeBandPrice> ageBandPrices) {
		this.ageBandPrices = ageBandPrices;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getPricingUnit() {
		return pricingUnit;
	}

	public void setPricingUnit(String pricingUnit) {
		this.pricingUnit = pricingUnit;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}
