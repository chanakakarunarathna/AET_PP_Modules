package com.placepass.booking.application.cart.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "TotalPricePerAgeBand")
public class TotalPricePerAgeBandDTO {

    private String priceType;

    private Integer ageBandId;

    private String currencyCode;

    private String description;

    private Float retailPrice;

    private Float finalPrice;

    private Float roundedFinalPrice;

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public Integer getAgeBandId() {
        return ageBandId;
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

}
