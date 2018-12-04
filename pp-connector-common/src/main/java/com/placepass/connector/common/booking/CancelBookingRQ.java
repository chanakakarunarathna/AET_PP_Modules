package com.placepass.connector.common.booking;

import java.util.List;

public class CancelBookingRQ {

    private String bookingReferenceId;

    private String bookingId;

    private String cancelDescription;

    private List<BookingItem> bookingItems;

    private String cancellationReasonCode;

    private String bookingStatus;

    private String cancelationType;

    public String getBookingReferenceId() {
        return bookingReferenceId;
    }

    public void setBookingReferenceId(String bookingReferenceId) {
        this.bookingReferenceId = bookingReferenceId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCancelDescription() {
        return cancelDescription;
    }

    public void setCancelDescription(String cancelDescription) {
        this.cancelDescription = cancelDescription;
    }

    public List<BookingItem> getBookingItems() {
        return bookingItems;
    }

    public void setBookingItems(List<BookingItem> bookingItems) {
        this.bookingItems = bookingItems;
    }

    public String getCancellationReasonCode() {
        return cancellationReasonCode;
    }

    public void setCancellationReasonCode(String cancellationReasonCode) {
        this.cancellationReasonCode = cancellationReasonCode;

    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCancelationType() {
        return cancelationType;
    }

    public void setCancelationType(String cancelationType) {
        this.cancelationType = cancelationType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CancelBookingRQ ["
                + (bookingReferenceId != null ? "bookingReferenceId=" + bookingReferenceId + ", " : "")
                + (bookingId != null ? "bookingId=" + bookingId + ", " : "")
                + (cancelDescription != null ? "cancelDescription=" + cancelDescription + ", " : "")
                + (bookingItems != null ? "bookingItems=" + bookingItems : "") + "]";

    }

}
