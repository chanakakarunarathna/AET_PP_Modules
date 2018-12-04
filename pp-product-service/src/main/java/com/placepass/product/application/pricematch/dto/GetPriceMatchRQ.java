package com.placepass.product.application.pricematch.dto;

import java.util.List;

import javax.validation.Valid;

public class GetPriceMatchRQ {

    @Valid
	private List<PricePerAgeBandDTO> prices;	
	
	@Valid
	private List<QuantityDTO> quantities;	
	
	public List<PricePerAgeBandDTO> getPrices() {
		return prices;
	}

	public void setPrices(List<PricePerAgeBandDTO> prices) {
		this.prices = prices;
	}

	public List<QuantityDTO> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<QuantityDTO> quantities) {
		this.quantities = quantities;
	}

}
