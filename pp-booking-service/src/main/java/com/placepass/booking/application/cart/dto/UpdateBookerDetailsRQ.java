package com.placepass.booking.application.cart.dto;

import javax.validation.Valid;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "UpdateBookerDetailsRequest")
public class UpdateBookerDetailsRQ {

    @Valid
    private BookerDTO bookerRequest;

    public BookerDTO getBookerRequest() {
        return bookerRequest;
    }

    public void setBookerRequest(BookerDTO bookerRequest) {
        this.bookerRequest = bookerRequest;
    }

}
