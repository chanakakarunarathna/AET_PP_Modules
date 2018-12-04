package com.placepass.booking.application.cart.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ValidateCartResponse")
public class ValidateCartRS {

    private List<String> validateMessages ;

    public List<String> getValidateMessages() {
        return validateMessages;
    }

    public void setValidateMessages(List<String> validateMessages) {
        this.validateMessages = validateMessages;
    }
    
}
