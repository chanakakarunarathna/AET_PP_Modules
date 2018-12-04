package com.placepass.booking.infrastructure.discount;

import java.util.List;

/**
 * The Class DiscountFeeRQ.
 */
public class DiscountFeeRS {

    private float totalPrice;

    private float discountAmount;

    private float totalPriceAfterDiscount;

    private List<FeeDTO> fees;

    private float finalTotalPrice;

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

    public List<FeeDTO> getFees() {
        return fees;
    }

    public void setFees(List<FeeDTO> fees) {
        this.fees = fees;
    }

    public float getFinalTotalPrice() {
        return finalTotalPrice;
    }

    public void setFinalTotalPrice(float finalTotalPrice) {
        this.finalTotalPrice = finalTotalPrice;
    }

}
