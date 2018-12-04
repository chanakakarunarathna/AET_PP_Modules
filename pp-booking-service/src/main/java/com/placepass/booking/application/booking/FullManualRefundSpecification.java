package com.placepass.booking.application.booking;

import org.springframework.stereotype.Component;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.RefundMode;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.CancelBookingTransaction;
import com.placepass.booking.domain.booking.cancel.RefundSummary;

@Component
public class FullManualRefundSpecification {

    /**
     * Checks if is satisfied by.
     * 
     * @param booking the booking
     * @param cancelBooking the cancel booking
     * @return true, if is satisfied by
     */
    
    public boolean isSatisfiedBy(Booking booking, CancelBooking cancelBooking,
            CancelBookingTransaction cancelBookingTransaction,RefundSummary refundSummary) {

        boolean satisfied = false;
        if (RefundMode.FULL.equals(refundSummary.getMode())) {
            satisfied = true;
        }
        return satisfied;
    }
}
