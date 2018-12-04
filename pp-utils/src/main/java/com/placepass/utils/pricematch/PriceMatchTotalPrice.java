package com.placepass.utils.pricematch;

public class PriceMatchTotalPrice {

    private String currencyCode;

    private Float finalTotal;

    private Float retailTotal;

    private Float roundedFinalTotal;

    private Float merchantTotal;

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

    public Float getMerchantTotal() {
        return merchantTotal;
    }

    public void setMerchantTotal(Float merchantTotal) {
        this.merchantTotal = merchantTotal;
    }

}
