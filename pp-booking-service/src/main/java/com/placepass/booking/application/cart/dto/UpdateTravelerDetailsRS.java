package com.placepass.booking.application.cart.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "UpdateTravelerDetailsResponse")
public class UpdateTravelerDetailsRS {

    private BookerDTO bookerDetails;

    private List<BookingOptionDTO> bookingOptions;

    private TotalDTO total;

    public BookerDTO getBookerDetails() {
        return bookerDetails;
    }

    public void setBookerDetails(BookerDTO bookerDetails) {
        this.bookerDetails = bookerDetails;
    }

    public List<BookingOptionDTO> getBookingOptions() {
        return bookingOptions;
    }

    public void setBookingOptions(List<BookingOptionDTO> bookingOptions) {
        this.bookingOptions = bookingOptions;
    }

    public TotalDTO getTotal() {
        return total;
    }

    public void setTotal(TotalDTO total) {
        this.total = total;
    }

}
