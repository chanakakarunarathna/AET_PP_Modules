package com.placepass.booking.infrastructure.discount;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ApplyDiscountRQ.
 */
public class RedeemDiscountRQ {

    private List<DiscountDTO> discounts = new ArrayList<DiscountDTO>();

    private CartDTO cart;

    private String bookingId;

    private String bookingReference;

    private String vendorBookingRefId;

    private String customerId;

    public List<DiscountDTO> getDiscounts() {
        return discounts;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getVendorBookingRefId() {
        return vendorBookingRefId;
    }

    public void setVendorBookingRefId(String vendorBookingRefId) {
        this.vendorBookingRefId = vendorBookingRefId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
