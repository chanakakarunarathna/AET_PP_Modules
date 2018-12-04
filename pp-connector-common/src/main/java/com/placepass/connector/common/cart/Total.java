package com.placepass.connector.common.cart;

public class Total {

    private String currencyCode;

    private String currency;

    private float finalTotal;

    private float merchantTotal;

    private float retailTotal;

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

}
