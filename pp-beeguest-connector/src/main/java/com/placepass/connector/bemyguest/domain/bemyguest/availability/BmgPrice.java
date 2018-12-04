package com.placepass.connector.bemyguest.domain.bemyguest.availability;

public class BmgPrice {

	private Integer priceGroupSortOrder; 
	
    private String priceType;

    private int ageBandId;

    private String currencyCode;

    private String description;

    private float retailPrice;

    private float finalPrice;

    // TODO: Dont't include this in the DTO.
    private float merchantPrice;

    private Integer maxBuy;

    private Integer minBuy;

    private Integer ageFrom;

    private Integer ageTo;

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public int getAgeBandId() {
        return ageBandId;
    }

    public void setAgeBandId(int ageBandId) {
        this.ageBandId = ageBandId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public float getMerchantPrice() {
        return merchantPrice;
    }

    public void setMerchantPrice(float merchantPrice) {
        this.merchantPrice = merchantPrice;
    }

    public Integer getMaxBuy() {
        return maxBuy;
    }

    public void setMaxBuy(Integer maxBuy) {
        this.maxBuy = maxBuy;
    }

    public Integer getMinBuy() {
        return minBuy;
    }

    public void setMinBuy(Integer minBuy) {
        this.minBuy = minBuy;
    }

	public Integer getPriceGroupSortOrder() {
		return priceGroupSortOrder;
	}

	public void setPriceGroupSortOrder(Integer priceGroupSortOrder) {
		this.priceGroupSortOrder = priceGroupSortOrder;
	}
	
	public Integer getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }

}
