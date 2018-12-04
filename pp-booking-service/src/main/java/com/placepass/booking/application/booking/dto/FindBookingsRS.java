package com.placepass.booking.application.booking.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "FindBookingsResponse")
public class FindBookingsRS {

    private GetBookingDTO booking;

    public GetBookingDTO getBooking() {
        return booking;
    }

    public void setBooking(GetBookingDTO booking) {
        this.booking = booking;
    }

}
