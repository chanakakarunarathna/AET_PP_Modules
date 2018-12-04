package com.placepass.booking.domain.booking;

public enum RefundMode {

    NONE, PARTIAL, FULL;

    public static RefundMode getRefundMode(String mode) {
        try {
            return RefundMode.valueOf(mode);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid refund mode: " + mode);
        }
    }

}
