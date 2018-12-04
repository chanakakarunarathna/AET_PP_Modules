package com.placepass.booking.domain.product;

public class Price {

    private Integer priceGroupSortOrder;

    private int ageBandId;

    private String priceType;

    private String currencyCode;

    private String description;

    private float retailPrice;

    private float finalPrice;

    private float merchantPrice;

    private float roundedFinalPrice;

    private Integer maxBuy;

    private Integer minBuy;

    private Integer ageFrom;

    private Integer ageTo;

    private String pricingUnit;

    public int getAgeBandId() {
        return ageBandId;
    }

    public void setAgeBandId(int ageBandId) {
        this.ageBandId = ageBandId;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
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

    public float getRoundedFinalPrice() {
        return roundedFinalPrice;
    }

    public void setRoundedFinalPrice(float roundedFinalPrice) {
        this.roundedFinalPrice = roundedFinalPrice;
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

    public String getPricingUnit() {
        return pricingUnit;
    }

    public void setPricingUnit(String pricingUnit) {
        this.pricingUnit = pricingUnit;
    }
}
