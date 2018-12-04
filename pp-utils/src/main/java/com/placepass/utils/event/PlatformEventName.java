package com.placepass.utils.event;

public enum PlatformEventName {

    USER_CREATED,
    USER_EMAIL_UPDATED,
    USER_PROFILE_UPDATED,
    USER_PASSWORD_UPDATED,
    USER_FORGOT_PASSWORD,
    USER_RESEND_VERIFICATION,
    USER_DELETION,
    // TODO - more customer related events

    BOOKING_CONFIRMATION,
    BOOKING_PENDING,
    BOOKING_CANCELLED_FULL_REFUND,
    BOOKING_CANCELLED_PARTIAL_REFUND,
    BOOKING_CANCELLED_NO_REFUND,
    BOOKING_CANCELLATION,
    BOOKING_REJECTED,
    
    RESEND_VOUCHER,
    
    ADMIN_USER_VERIFICATION,
    ADMIN_USER_FORGOT_PASSWORD,
    ADMIN_USER_ROLE_ASSIGNMENT,
    ADMIN_USER_ROLE_CHANGE,
    ADMIN_USER_ROLE_DELETION,
    ADMIN_USER_SUPER_ADMIN_ROLE_ASSIGNMENT,
    ADMIN_USER_SUPER_ADMIN_ROLE_DELETION;

}