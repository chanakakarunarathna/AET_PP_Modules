package com.placepass.booking.application.booking.admin.dto;

import java.util.List;

import com.placepass.booking.application.booking.admin.dto.GetBookingDTO;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "SearchBookingsResponse")
public class SearchBookingsRS {

    private List<GetBookingDTO> bookings;

    private long totalRecords;

    private int totalPages;

    public List<GetBookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<GetBookingDTO> bookings) {
        this.bookings = bookings;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
