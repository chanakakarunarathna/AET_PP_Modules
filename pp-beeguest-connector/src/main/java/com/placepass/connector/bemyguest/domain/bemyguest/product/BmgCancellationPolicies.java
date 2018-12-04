package com.placepass.connector.bemyguest.domain.bemyguest.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgCancellationPolicies {
	
	@JsonProperty("numberOfDays")
	private int numberOfDays;
	
	@JsonProperty("refundPercentage")
	private double refundPercentage;

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public double getRefundPercentage() {
		return refundPercentage;
	}

	public void setRefundPercentage(double refundPercentage) {
		this.refundPercentage = refundPercentage;
	}

}
