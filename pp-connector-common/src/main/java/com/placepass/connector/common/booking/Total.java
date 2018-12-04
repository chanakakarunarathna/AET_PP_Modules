package com.placepass.connector.common.booking;

public class Total {

    private String currencyCode;

    private String currency;

    private float retailTotal;

    private float finalTotal;

    private float merchantTotal;

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

    public float getRetailTotal() {
        return retailTotal;
    }

    public void setRetailTotal(float retailTotal) {
        this.retailTotal = retailTotal;
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

}
