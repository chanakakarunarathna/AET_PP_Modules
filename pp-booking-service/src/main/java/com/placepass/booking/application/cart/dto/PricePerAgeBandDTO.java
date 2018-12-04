package com.placepass.booking.application.cart.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "PricePerAgeBand")
public class PricePerAgeBandDTO {

    private Integer priceGroupSortOrder;

    private String priceType;

    private Integer ageBandId;

    private String currencyCode;

    private String description;

    private Float retailPrice;

    private Float finalPrice;

    private Float roundedFinalPrice;

    private Integer maxBuy;

    private Integer minBuy;

    private Integer ageFrom;

    private Integer ageTo;

    public Integer getPriceGroupSortOrder() {
        return priceGroupSortOrder;
    }

    private String pricingUnit;

    public void setPriceGroupSortOrder(Integer priceGroupSortOrder) {
        this.priceGroupSortOrder = priceGroupSortOrder;
    }

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

    public String getPricingUnit() {
        return pricingUnit;
    }

    public void setPricingUnit(String pricingUnit) {
        this.pricingUnit = pricingUnit;
    }
}
