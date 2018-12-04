package com.placepass.booking.application.booking;

import org.springframework.stereotype.Component;

import com.placepass.booking.application.booking.dto.ManualCancelBookingRQ;
import com.placepass.booking.application.booking.paymentcondto.PaymentProcessStatus;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.ManualRefundType;
import com.placepass.booking.domain.booking.cancel.VendorCancellationReasons;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.vendorproduct.Vendor;

@Component
public class BookingManualCancelSpecification {

    public boolean isSatisfiedBy(ManualCancelBookingRQ manualCancelBookingRequest, Booking booking,
            CancelBooking cancelBooking) {

        boolean satisfied = true;

        if (manualCancelBookingRequest.getRefundType().equals(ManualRefundType.AMOUNT.name())
                && (booking.getTotal().getFinalTotal() < manualCancelBookingRequest.getCancellationAmount()
                        || manualCancelBookingRequest.getCancellationAmount() <= 0)) {

            throw new BadRequestException(
                    PlacePassExceptionCodes.INVALID_REFUND_AMOUNT.toString(),
                    PlacePassExceptionCodes.INVALID_REFUND_AMOUNT.getDescription());
        }

        if (booking.getBookingOptions().get(0).getVendor().equals(Vendor.VIATOR.name()) && !VendorCancellationReasons
                .getViatorCancellationReasons().containsKey(manualCancelBookingRequest.getCancellationReasonCode())) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_CANCELLATION_REASON.toString(),
                    PlacePassExceptionCodes.INVALID_CANCELLATION_REASON.getDescription());

        }

        if (cancelBooking != null) {
            
            if (cancelBooking.getCancelBookingTransactions().stream()
                    .filter(tr -> PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS.name()
                            .equals(tr.getRefunds().get(0).getRefundStatus().name())) != null) {

                throw new BadRequestException(PlacePassExceptionCodes.BOOKING_AMOUNT_ALREADY_REFUND.toString(),
                        PlacePassExceptionCodes.BOOKING_AMOUNT_ALREADY_REFUND.getDescription());
            }
        }

        return satisfied;
    }

}