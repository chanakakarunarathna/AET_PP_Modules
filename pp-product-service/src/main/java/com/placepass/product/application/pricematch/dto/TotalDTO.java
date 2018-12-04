package com.placepass.product.application.pricematch.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Total")
public class TotalDTO {

    private String currencyCode;

    private Float finalTotal;

    private Float retailTotal;

    private Float roundedFinalTotal;

    private Float originalTotal;

    private Float discountAmount;

    private Float totalAfterDiscount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Float getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(Float finalTotal) {
        this.finalTotal = finalTotal;
    }

    public Float getRetailTotal() {
        return retailTotal;
    }

    public void setRetailTotal(Float retailTotal) {
        this.retailTotal = retailTotal;
    }

    public Float getRoundedFinalTotal() {
        return roundedFinalTotal;
    }

    public void setRoundedFinalTotal(Float roundedFinalTotal) {
        this.roundedFinalTotal = roundedFinalTotal;
    }

    public Float getOriginalTotal() {
        return originalTotal;
    }

    public void setOriginalTotal(Float originalTotal) {
        this.originalTotal = originalTotal;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Float getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(Float totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }
}
