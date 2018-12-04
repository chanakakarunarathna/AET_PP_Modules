package com.placepass.booking.application.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.RefundMode;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.CancelBookingTransaction;
import com.placepass.booking.domain.booking.cancel.RefundSummary;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.VendorErrorCode;

/**
 * The Class PartialRefundSpecification checks if the contract fulfills to do a partial refund.
 * 
 * @author naveen.w
 */
@Component
public class PartialRefundSpecification {

    @Autowired
    RefundStrategy refundStrategy;

    /**
     * Checks if is satisfied by.
     * 
     * @param cancelBookingRS the cancel booking RS
     * @param booking the booking
     * @param cancelBooking the cancel booking
     * @return true, if is satisfied by
     */
    public boolean isSatisfiedBy(CancelBookingRS cancelBookingRS, Booking booking, CancelBooking cancelBooking,
            CancelBookingTransaction cancelBookingTransaction) {

        RefundSummary refundSummary = refundStrategy.getRefundSummary(booking, cancelBooking, cancelBookingTransaction);

        boolean satisfied = false;
        if (VendorErrorCode.SUCCESS.getId() == cancelBookingRS.getResultType().getCode()
                && RefundMode.PARTIAL.equals(refundSummary.getMode())) {
            satisfied = true;
        }
        return satisfied;
    }
}
