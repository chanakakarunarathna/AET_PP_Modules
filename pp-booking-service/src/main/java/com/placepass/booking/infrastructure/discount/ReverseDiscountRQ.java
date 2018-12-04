package com.placepass.booking.infrastructure.discount;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ApplyDiscountRQ.
 */
public class ReverseDiscountRQ {

    /** The booking id. */
    private String bookingId;

    /** The cart id. */
    private String cartId;

    /** The discount codes. */
    private List<String> discountCodes = new ArrayList<String>();

    /**
     * Gets the booking id.
     *
     * @return the booking id
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking id.
     *
     * @param bookingId the new booking id
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the cart id.
     *
     * @return the cart id
     */
    public String getCartId() {
        return cartId;
    }

    /**
     * Sets the cart id.
     *
     * @param cartId the new cart id
     */
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    /**
     * Gets the discount codes.
     *
     * @return the discount codes
     */
    public List<String> getDiscountCodes() {
        return discountCodes;
    }

    /**
     * Sets the discount codes.
     *
     * @param discountCodes the new discount codes
     */
    public void setDiscountCodes(List<String> discountCodes) {
        this.discountCodes = discountCodes;
    }

}
