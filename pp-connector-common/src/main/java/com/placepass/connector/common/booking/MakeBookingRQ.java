package com.placepass.connector.common.booking;

import java.util.List;

public class MakeBookingRQ {

    private String bookingId;

    private Booker bookerDetails;

    private List<BookingOption> bookingOptions;

    private Total total;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public List<BookingOption> getBookingOptions() {
        return bookingOptions;
    }

    public void setBookingOptions(List<BookingOption> bookingOptions) {
        this.bookingOptions = bookingOptions;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    public Booker getBookerDetails() {
        return bookerDetails;
    }

    public void setBookerDetails(Booker bookerDetails) {
        this.bookerDetails = bookerDetails;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MakeBookingRQ [" + (bookingId != null ? "bookingId=" + bookingId + ", " : "")
                + (bookerDetails != null ? "bookerDetails=" + bookerDetails + ", " : "")
                + (bookingOptions != null ? "bookingOptions=" + bookingOptions : "") + "]";
    }

}
