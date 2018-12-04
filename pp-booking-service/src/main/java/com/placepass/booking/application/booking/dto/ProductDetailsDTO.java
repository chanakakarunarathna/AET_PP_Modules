package com.placepass.booking.application.booking.dto;

public class ProductDetailsDTO {

    private String cancellationPolicy;

    private CancellationRulesDTO cancellationRules;

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public CancellationRulesDTO getCancellationRules() {
        return cancellationRules;
    }

    public void setCancellationRules(CancellationRulesDTO cancellationRules) {
        this.cancellationRules = cancellationRules;
    }

}
