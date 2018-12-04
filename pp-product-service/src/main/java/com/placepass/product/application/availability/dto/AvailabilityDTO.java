package com.placepass.product.application.availability.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Availability")
public class AvailabilityDTO {
	@ApiModelProperty(position = 1, notes = "Date")
	private String date;

	@ApiModelProperty(position = 2, notes = "available or not")
	private boolean soldOut;

	@ApiModelProperty(position = 3, notes = "price")
	private PriceDTO price;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isSoldOut() {
		return soldOut;
	}

	public void setSoldOut(boolean soldOut) {
		this.soldOut = soldOut;
	}

	public PriceDTO getPrice() {
		return price;
	}

	public void setPrice(PriceDTO price) {
		this.price = price;
	}

}
