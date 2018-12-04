package com.placepass.product.application.pricematch.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Price")
public class PriceDTO {

    private Integer priceGroupSortOrder;
    
    private String priceType;

    @NotNull(message = "Age band id is required")
    private Integer ageBandId;

    @NotNull(message = "Currency Code is required")
    private String currencyCode;

    private String description;

    private float retailPrice;

    private float finalPrice;

    private float roundedFinalPrice;

    @NotNull(message = "MaxBuy Count is required")
    private Integer maxBuy;

    @NotNull(message = "MinBuy Count is required")
    private Integer minBuy;

    private Integer ageFrom;

    private Integer ageTo;

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

}
