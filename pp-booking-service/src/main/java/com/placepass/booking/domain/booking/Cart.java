package com.placepass.booking.domain.booking;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.placepass.booking.domain.platform.PlatformStatus;

public class Cart {

    @Id
    private String cartId;

    private String partnerId;

    private Instant createdTime;

    private Instant updatedTime;

    private Booker bookerDetails;

    private List<BookingOption> bookingOptions;

    private CartState cartState;

    private PlatformStatus status;

    // FIXME Optional customerId to support logged-in-user (later). A logged in user vs a guest user cart lookup
    // difference;
    // logged-in-user can probably retrieve cart (only one open or non-closed cart allowed) from any client (different
    // browsers), while a guest user can retrieve only if cartId is known (cookies etc..). For a guest user, we probably
    // need to invalidate previous cart whenever new cart (from a different brower) is created. These flows are not yet
    // confirmed and complexity is not in scope for demo.
    private String customerId;

    // Total of all the booking options in the cart.
    private Total total;
    
    private List<Fee> fees = new ArrayList<Fee>();

    public Cart() {
        this.cartState = CartState.OPEN;
        this.createdTime = Instant.now();
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

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public CartState getCartState() {
        return cartState;
    }

    public void setCartState(CartState cartState) {
        this.cartState = cartState;
    }

    public PlatformStatus getStatus() {
        return status;
    }

    public void setStatus(PlatformStatus status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Fee> getFees() {
        return fees;
    }

}
