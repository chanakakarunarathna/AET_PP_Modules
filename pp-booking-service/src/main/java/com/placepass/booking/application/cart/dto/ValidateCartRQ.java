package com.placepass.booking.application.cart.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ValidateCartRequest")
public class ValidateCartRQ {
    private String validateScope;

    public String getValidateScope() {
        return validateScope;
    }

    public void setValidateScope(String validateScope) {
        this.validateScope = validateScope;
    }
}
