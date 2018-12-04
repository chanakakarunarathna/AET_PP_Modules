package com.placepass.booking.domain.booking;

import com.placepass.booking.domain.platform.PlatformStatus;

/**
 * Indicates the life cycle stages of a {@link Booking}.
 * 
 * <p>
 * For example, from payment initiation (out bound call to payment connector) to payment success/failure (response from
 * payment connector), booking event stage could be {@link BookingState#PAYMENT}.
 * 
 * @author wathsala.w
 * 
 * @see PlatformStatus
 *
 */
public enum BookingState {

    /**
     * Start of life cycle.
     */
     OPEN,

    /**
     * Payment attempted for the booking. This usually is a sale.
     */
    PAYMENT,

    /**
     * Soft booking, where platform acknowledges to the user that a booking is placed with a reference number for
     * support calls.
     */
    BOOKING_TENTATIVE,

    /**
     * Booking is in a Pending State
     */
    BOOKING_PENDING,

    /**
     * Hard booking where platform places the booking with a vendor.
     * 
     */
    BOOKING_CONFIRMATION,

    /**
     * Payment reversal in play. This could happen if {@link BookingState#BOOKING_CONFIRMATION} processing
     * resulted in a failure. Same goes for {@link BookingState#BOOKING_TENTATIVE}, but this failure is rare.
     */
    PAYMENT_REVERSAL,

    /**
     * At a later time, a successful {@link LifecycleEventType#BOOKING_CONFIRMATION} can be cancelled with a vendor.
     */
    BOOKING_CANCEL,

    /**
     * Goes in pair with {@link LifecycleEventType#BOOKING_CANCEL} to process a successful cancel.
     */
    PAYMENT_REFUND,

    /**
     * Has reached end of life cycle.
     */
//     CLOSE;
    
}
