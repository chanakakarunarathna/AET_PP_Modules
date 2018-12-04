package com.placepass.product.application.pricematch.dto;

import java.util.List;

public class GetPriceMatchRS {

    private List<PriceBreakDownDTO> priceBreakdowns;

    private List<LoyaltyDetailDTO> loyaltyDetails;

    private List<Fee> fees;

    private TotalDTO totalPrice;

	public List<PriceBreakDownDTO> getPriceBreakdowns() {
		return priceBreakdowns;
	}

	public void setPriceBreakdowns(List<PriceBreakDownDTO> priceBreakdowns) {
		this.priceBreakdowns = priceBreakdowns;
	}

    public List<LoyaltyDetailDTO> getLoyaltyDetails() {
        return loyaltyDetails;
    }

    public void setLoyaltyDetails(List<LoyaltyDetailDTO> loyaltyDetails) {
        this.loyaltyDetails = loyaltyDetails;
    }

	public TotalDTO getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(TotalDTO totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Fee> getFees() {
		return fees;
	}

	public void setFees(List<Fee> fees) {
		this.fees = fees;
	}
}
