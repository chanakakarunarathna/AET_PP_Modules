package com.placepass.booking.domain.booking.cancel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.placepass.booking.domain.platform.Status;

public class CancelBooking {

    @Id
    private String id;

    private String bookingId;

    private String partnerId;

    private String customerId;

    private String cartId;

    private String bookingReference;

    List<CancelBookingTransaction> cancelBookingTransactions;

    // this will hold the status of the last CancelBookingTransaction status
    private Status cancelStatus;

    private Instant createdTime;

    private Instant updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public List<CancelBookingTransaction> getCancelBookingTransactions() {
        if (cancelBookingTransactions == null) {
            cancelBookingTransactions = new ArrayList<>();
        }
        return cancelBookingTransactions;
    }

    public void setCancelBookingTransactions(List<CancelBookingTransaction> cancelBookingTransactions) {
        this.cancelBookingTransactions = cancelBookingTransactions;
    }

    public Status getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Status cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

}
