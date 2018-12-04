package com.placepass.booking.domain.platform;

// FIXME: move to common place - pp-util or new pp-common?

/**
 * Defines the statuses of the platform. These are used to communicate status of the API call to it's clients (whether
 * it's an integrating partner or another domain services).
 * 
 * <p>
 * 
 * TODO: HTTP vs custom identify. Should include numeric code or name is sufficient?? If so define ranges of code.
 * 
 * @author wathsala.w
 *
 */
public enum PlatformStatus {

    /**
     * Success of any type of activity.
     */
    SUCCESS, // ("", ""),

    /**
     * Platform treats this as a possible type of success, specially on bookings.
     */
    PENDING,

    /**
     * General payment failure.
     */
    PAYMENT_FAILED,

    PAYMENT_TIMEOUT,

    INSUFFICIENT_FUNDS,

    INVALID_PAYMENT_DETAILS,

    CARD_EXPIRED,

    CARD_ERROR,

    BOOKING_FAILED,

    BOOKING_REJECTED,

    BOOKING_TIMEOUT,

    BOOKING_CANCEL_FAILED,

    BOOKING_CANCEL_TIMEOUT,

    REFUND_FAILED,

    REFUND_TIMEOUT,

    CANCELLED,

    INTERNAL_SERVER_ERROR,

    UNKNOWN_PAYMENT_GATEWAY_ERROR,

    CARD_CVV_FAILURE,

    CARD_AVS_FAILURE,

    CARD_DECLINED;
}
