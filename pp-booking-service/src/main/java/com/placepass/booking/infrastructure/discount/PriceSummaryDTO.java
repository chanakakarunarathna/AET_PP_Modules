package com.placepass.booking.infrastructure.discount;

import java.util.ArrayList;
import java.util.List;

public class PriceSummaryDTO {

    private float totalPrice;

    private float discountAmount;

    private float totalPriceAfterDiscount;

    private float finalTotalPrice;

    private String currencyCode;

    private List<FeeDTO> fees = new ArrayList<FeeDTO>();

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public float getTotalPriceAfterDiscount() {
        return totalPriceAfterDiscount;
    }

    public void setTotalPriceAfterDiscount(float totalPriceAfterDiscount) {
        this.totalPriceAfterDiscount = totalPriceAfterDiscount;
    }

    public float getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(float finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<FeeDTO> getFees() {
        return fees;
    }

    public void setFees(List<FeeDTO> fees) {
        this.fees = fees;
    }

}
