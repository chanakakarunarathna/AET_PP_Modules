package com.placepass.booking.application.booking.dto;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Payment")
public class PaymentRQ {

    @NotEmpty(message = "Tokenized number is required")    
    private String tokenizedNumber;

    /**
     * @return the tokenizedNumber
     */
    public String getTokenizedNumber() {
        return tokenizedNumber;
    }

    /**
     * @param tokenizedNumber the tokenizedNumber to set
     */
    public void setTokenizedNumber(String tokenizedNumber) {
        this.tokenizedNumber = tokenizedNumber;
    }

}
