package com.placepass.booking.application.booking.dto;

public class RulesDTO {

    private Integer maxHoursInAdvance;

    private float refundMultiplier;

    private Integer minHoursInAdvance;

    public Integer getMaxHoursInAdvance() {
        return maxHoursInAdvance;
    }

    public void setMaxHoursInAdvance(Integer maxHoursInAdvance) {
        this.maxHoursInAdvance = maxHoursInAdvance;
    }

    public float getRefundMultiplier() {
        return refundMultiplier;
    }

    public void setRefundMultiplier(float refundMultiplier) {
        this.refundMultiplier = refundMultiplier;
    }

    public Integer getMinHoursInAdvance() {
        return minHoursInAdvance;
    }

    public void setMinHoursInAdvance(Integer minHoursInAdvance) {
        this.minHoursInAdvance = minHoursInAdvance;
    }

}
