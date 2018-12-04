package com.placepass.product.application.pricematch.dto;

import javax.validation.constraints.NotNull;

public class PricePerAgeBandDTO {

	@NotNull(message = "Price Group Sort Order is required")
    private Integer priceGroupSortOrder;
    
    private String priceType;

    @NotNull(message = "Age Band ID is required")
    private Integer ageBandId;

    @NotNull(message = "Currency Code is required")
    private String currencyCode;

    private String description;

    @NotNull(message = "Retail Price is required")
    private Float retailPrice;

    @NotNull(message = "Final Price is required")
    private Float finalPrice;

    private Float roundedFinalPrice;

    private Integer maxBuy;

    private Integer minBuy;

    private Integer ageFrom;

    private Integer ageTo;

    private String pricingUnit;

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public Integer getAgeBandId() {
        return ageBandId;
    }

    public void setAgeBandId(Integer ageBandId) {
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

    public Float getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Float getRoundedFinalPrice() {
        return roundedFinalPrice;
    }

    public void setRoundedFinalPrice(Float roundedFinalPrice) {
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
