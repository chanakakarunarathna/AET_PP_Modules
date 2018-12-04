package com.placepass.product.application.pricematch.dto;

import java.util.List;

public class PriceSummary {

    //Total Price in Cart
    private float totalPrice;

    //Discount amount
    private float discountAmount;

    //Total price-discount
    private float totalPriceAfterDiscount;

    private List<Fee> fees;

    //(Total price-discount) + fee
    private float finalTotalPrice;


    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

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
}