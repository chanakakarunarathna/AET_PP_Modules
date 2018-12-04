package com.placepass.booking.application.cart.dto;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "CreateCartRequest")
public class CreateCartRQ {

    @Valid
    private List<BookingOptionDTO> bookingOptions = null;

    public List<BookingOptionDTO> getBookingOptions() {
        return bookingOptions;
    }

    public void setBookingOptions(List<BookingOptionDTO> bookingOptions) {
        this.bookingOptions = bookingOptions;
    }

}
