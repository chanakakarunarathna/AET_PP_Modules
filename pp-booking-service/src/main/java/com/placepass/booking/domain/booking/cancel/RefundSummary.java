package com.placepass.booking.domain.booking.cancel;

import com.placepass.booking.domain.booking.RefundMode;
import com.placepass.utils.currency.AmountFormatter;

public class RefundSummary {

    private RefundMode mode;

    private float multiplier;

    private int centAmount;

    public RefundSummary() {
    }

    public RefundSummary(RefundMode mode, float multiplier, int centAmount) {
        this.mode = mode;
        this.multiplier = multiplier;
        this.centAmount = centAmount;
    }

    public RefundMode getMode() {
        return mode;
    }

    public void setMode(RefundMode mode) {
        this.mode = mode;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public int getCentAmount() {
        return centAmount;
    }

    public void setCentAmount(int centAmount) {
        this.centAmount = centAmount;
    }

    public float getDollarAmount() {
        return AmountFormatter.getHighestUnit(centAmount);
    }

}
