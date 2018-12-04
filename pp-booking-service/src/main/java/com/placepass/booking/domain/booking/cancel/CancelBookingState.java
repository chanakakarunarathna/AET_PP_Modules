package com.placepass.booking.domain.booking.cancel;

/**
 * Indicates the life cycle stages of a {@link CancelBookingTransaction}.
 *
 */
public enum CancelBookingState {

    /**
     * At a later time, a successful {@link LifecycleEventType#BOOKING_CONFIRMATION} can be cancelled with a vendor.
     */
    BOOKING_CANCEL,

    /**
     * Goes in pair with {@link LifecycleEventType#BOOKING_CANCEL} to process a successful refund.
     */
    PAYMENT_REFUND
}
