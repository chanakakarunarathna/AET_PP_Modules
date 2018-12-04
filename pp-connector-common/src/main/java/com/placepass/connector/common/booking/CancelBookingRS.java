package com.placepass.connector.common.booking;

import java.util.List;

import com.placepass.connector.common.common.BaseRS;

public class CancelBookingRS extends BaseRS {

    private String bookingReferenceNo;
    
    private String bookingId;
    
    private Float cancellationFee;
    
    private List<CancelledBookingItem> cancelledBookingItems;

    public String getBookingReferenceNo() {
        return bookingReferenceNo;
    }

    public void setBookingReferenceNo(String bookingReferenceNo) {
        this.bookingReferenceNo = bookingReferenceNo;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Float getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(Float cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public List<CancelledBookingItem> getCancelledBookingItems() {
        return cancelledBookingItems;
    }

    public void setCancelledBookingItems(List<CancelledBookingItem> cancelledBookingItems) {
        this.cancelledBookingItems = cancelledBookingItems;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CancelBookingRS ["
                + (bookingReferenceNo != null ? "bookingReferenceNo=" + bookingReferenceNo + ", " : "")
                + (bookingId != null ? "bookingId=" + bookingId + ", " : "")
                + (cancellationFee != null ? "cancellationFee=" + cancellationFee + ", " : "")
                + (cancelledBookingItems != null ? "cancelledBookingItems=" + cancelledBookingItems : "") + "]";
    }

}
