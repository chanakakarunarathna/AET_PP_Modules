package com.placepass.booking.domain.booking;

/**
 * Indicates the life cycle stages of a {@link Cart}.
 * 
 * @author wathsala.w
 *
 */
public enum CartState {

    /**
     * Start of life cycle.
     */
    OPEN,

    /**
     * Cart validation in play.
     */
    VALIDATE,

    /**
     * Cart sent for booking.
     */
    BOOKING,

    /**
     * Has reached end of life cycle.
     */
    CLOSE;
}
