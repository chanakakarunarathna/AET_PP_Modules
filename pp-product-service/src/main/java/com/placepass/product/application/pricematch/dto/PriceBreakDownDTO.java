package com.placepass.product.application.pricematch.dto;

public class PriceBreakDownDTO {

	private PricePerAgeBandDTO pricePerAgeBand;

	private TotalPricePerAgeBandDTO totalPricePerAgeBand;

	public PricePerAgeBandDTO getPricePerAgeBand() {
		return pricePerAgeBand;
	}

	public void setPricePerAgeBand(PricePerAgeBandDTO pricePerAgeBand) {
		this.pricePerAgeBand = pricePerAgeBand;
	}

	public TotalPricePerAgeBandDTO getTotalPricePerAgeBand() {
		return totalPricePerAgeBand;
	}

	public void setTotalPricePerAgeBand(TotalPricePerAgeBandDTO totalPricePerAgeBand) {
		this.totalPricePerAgeBand = totalPricePerAgeBand;
	}

}
