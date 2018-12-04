package com.placepass.booking.application.booking.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "GetBookingsHistoryResponse")
public class GetBookingsHistoryRS {

    private List<GetBookingDTO> bookingsHistory;

    private long totalRecords;

    private int totalPages;

    public List<GetBookingDTO> getBookingsHistory() {
        return bookingsHistory;
    }

    public void setBookingsHistory(List<GetBookingDTO> bookingsHistory) {
        this.bookingsHistory = bookingsHistory;
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
