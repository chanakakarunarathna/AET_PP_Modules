package com.placepass.connector.bemyguest.domain.placepass.algolia.product;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Price")
public class AlgoliaProductPrice {
    private String priceType;

    private int ageBandId;

    private String currencyCode;

    private String description;

    private Float retailPrice;

    private Float finalPrice;

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

    public float getRoundedFinalPrice() {
        return roundedFinalPrice;
    }

    public void setRoundedFinalPrice(float roundedFinalPrice) {
        this.roundedFinalPrice = roundedFinalPrice;
    }

}
