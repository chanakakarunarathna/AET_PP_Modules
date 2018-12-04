package com.placepass.booking.application.booking.admin.dto;

import io.swagger.annotations.ApiModel;

import com.placepass.booking.application.booking.admin.dto.GetBookingDTO;

@ApiModel(value = "GetBookingResponse")
public class GetBookingRS {

    private GetBookingDTO booking;

    public GetBookingDTO getBooking() {
        return booking;
    }

    public void setBooking(GetBookingDTO booking) {
        this.booking = booking;
    }

}