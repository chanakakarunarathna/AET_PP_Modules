package com.placepass.booking.infrastructure.discount;

public class TotalDTO {

    private String currencyCode;

    private String currency;

    private float finalTotal;

    private float merchantTotal;

    private float retailTotal;

    private float roundedFinalTotal;
    
    private float originalTotal;
    
    private float discountAmount;
    
    private float totalAfterDiscount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(float finalTotal) {
        this.finalTotal = finalTotal;
    }

    public float getMerchantTotal() {
        return merchantTotal;
    }

    public void setMerchantTotal(float merchantTotal) {
        this.merchantTotal = merchantTotal;
    }

    public float getRetailTotal() {
        return retailTotal;
    }

    public void setRetailTotal(float retailTotal) {
        this.retailTotal = retailTotal;
    }

    public float getRoundedFinalTotal() {
        return roundedFinalTotal;
    }

    public void setRoundedFinalTotal(float roundedFinalTotal) {
        this.roundedFinalTotal = roundedFinalTotal;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public float getOriginalTotal() {
        return originalTotal;
    }

    public void setOriginalTotal(float originalTotal) {
        this.originalTotal = originalTotal;
    }

    public float getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(float totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }

    
}
