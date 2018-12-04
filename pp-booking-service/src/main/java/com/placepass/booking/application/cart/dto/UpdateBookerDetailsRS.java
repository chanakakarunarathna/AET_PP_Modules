package com.placepass.booking.application.cart.dto;

import java.util.List;

public class UpdateBookerDetailsRS {

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
