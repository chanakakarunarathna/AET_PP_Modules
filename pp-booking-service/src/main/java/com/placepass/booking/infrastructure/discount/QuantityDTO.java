package com.placepass.booking.infrastructure.discount;

public class QuantityDTO {

	private int ageBandId;

	private String ageBandLabel;

	private int quantity;

	public int getAgeBandId() {
		return ageBandId;
	}

	public void setAgeBandId(int ageBandId) {
		this.ageBandId = ageBandId;
	}

	public String getAgeBandLabel() {
		return ageBandLabel;
	}

	public void setAgeBandLabel(String ageBandLabel) {
		this.ageBandLabel = ageBandLabel;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
