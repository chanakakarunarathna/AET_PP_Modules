package com.viator.connector.domain.viator.pricingmatrix;

public class ViatorPricingMatrixResPrice {

	private Integer sortOrder;

	private String currencyCode;

	private Double price;

	private Integer minNoOfTravellersRequiredForPrice;

	private String priceFormatted;

	private Double merchantNetPrice;

	private String merchantNetPriceFormatted;
	
	public String getPriceFormatted() {
		return priceFormatted;
	}

	public void setPriceFormatted(String priceFormatted) {
		this.priceFormatted = priceFormatted;
	}

	public String getMerchantNetPriceFormatted() {
		return merchantNetPriceFormatted;
	}

	public void setMerchantNetPriceFormatted(String merchantNetPriceFormatted) {
		this.merchantNetPriceFormatted = merchantNetPriceFormatted;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Double getMerchantNetPrice() {
		return merchantNetPrice;
	}

	public void setMerchantNetPrice(Double merchantNetPrice) {
		this.merchantNetPrice = merchantNetPrice;
	}

	public Integer getMinNoOfTravellersRequiredForPrice() {
		return minNoOfTravellersRequiredForPrice;
	}

	public void setMinNoOfTravellersRequiredForPrice(Integer minNoOfTravellersRequiredForPrice) {
		this.minNoOfTravellersRequiredForPrice = minNoOfTravellersRequiredForPrice;
	}
}
