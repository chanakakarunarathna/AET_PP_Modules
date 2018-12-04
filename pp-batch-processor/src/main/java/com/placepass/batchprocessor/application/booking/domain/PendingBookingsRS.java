package com.placepass.batchprocessor.application.booking.domain;

import java.util.List;

public class PendingBookingsRS {

    private List<PendingBooking> pendingBookings;

    public List<PendingBooking> getPendingBookings() {
        return pendingBookings;
    }

    public void setPendingBookings(List<PendingBooking> pendingBookings) {
        this.pendingBookings = pendingBookings;
    }
}
