package com.placepass.booking.application.cart.dto;

import java.util.List;

import com.placepass.booking.application.booking.dto.FeeDTO;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ViewCartResponse")
public class ViewCartRS {

    private BookerDTO bookerDetails;

    private List<BookingOptionDTO> bookingOptions;

    private TotalDTO total;

    private List<FeeDTO> fees;

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

    public List<FeeDTO> getFees() {
        return fees;
    }

    public void setFees(List<FeeDTO> fees) {
        this.fees = fees;
    }

}
