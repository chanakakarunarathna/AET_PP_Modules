package com.placepass.product.application.availability.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "LowestPrice")
public class PriceDTO {

    private String priceType;

    private int ageBandId;

    private String currencyCode;

    private String description;

    private float retailPrice;

    private float finalPrice;

    private float roundedFinalPrice;

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

    public float getRoundedFinalPrice() {
        return roundedFinalPrice;
    }

    public void setRoundedFinalPrice(float roundedFinalPrice) {
        this.roundedFinalPrice = roundedFinalPrice;
    }

}
