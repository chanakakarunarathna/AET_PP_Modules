package com.placepass.booking.domain.platform;

public enum NewBookingStatus {

    CONFIRMED,

    /** Use {@link NewBookingStatus#REJECTED} instead. */
    @Deprecated
    FAILED,

    REJECTED;

}
