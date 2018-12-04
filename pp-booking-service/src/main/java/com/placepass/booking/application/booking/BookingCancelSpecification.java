package com.placepass.booking.application.booking;

import org.springframework.stereotype.Component;

import com.placepass.booking.application.booking.dto.CancelBookingRQ;
import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.cancel.CancelBooking;
import com.placepass.booking.domain.booking.cancel.VendorCancellationReasons;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.PlacePassExceptionCodes;
import com.placepass.utils.vendorproduct.Vendor;

@Component
public class BookingCancelSpecification {

    public boolean isSatisfiedBy(CancelBookingRQ cancelBookingRequest, Booking booking, CancelBooking cancelBooking) {
        boolean satisfied = true;

        if (booking.isPending()) {
            // TODO - log before throw
            throw new BadRequestException(PlacePassExceptionCodes.PENDING_BOOKING_CANCELLATION_FAILED.toString(),
                    PlacePassExceptionCodes.PENDING_BOOKING_CANCELLATION_FAILED.getDescription());
        } else if (booking.getBookingSummary() != null && booking.getBookingSummary().getOverallStatus() != null
                && (PlatformStatus.BOOKING_REJECTED == booking.getBookingSummary().getOverallStatus().getStatus())) {

            throw new BadRequestException(PlacePassExceptionCodes.REJECTED_BOOKING_CANCELLATION_FAILED.toString(),
                    PlacePassExceptionCodes.REJECTED_BOOKING_CANCELLATION_FAILED.getDescription());

        }

        if (booking.getBookingOptions().get(0).getVendor().equals(Vendor.VIATOR.name())
                && !VendorCancellationReasons.getViatorCancellationReasons().containsKey(
                        cancelBookingRequest.getCancellationReasonCode())) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_CANCELLATION_REASON.toString(),
                    PlacePassExceptionCodes.INVALID_CANCELLATION_REASON.getDescription());

        }

        if (cancelBooking != null) {
            if (PlatformStatus.SUCCESS == cancelBooking.getCancelStatus().getStatus()) {
                throw new BadRequestException(PlacePassExceptionCodes.BOOKING_ALREADY_CANCELED.toString(),
                        PlacePassExceptionCodes.BOOKING_ALREADY_CANCELED.getDescription());

            } else if (PlatformStatus.REFUND_FAILED == cancelBooking.getCancelStatus().getStatus()
                    || PlatformStatus.REFUND_TIMEOUT == cancelBooking.getCancelStatus().getStatus()) {
                throw new BadRequestException(
                        PlacePassExceptionCodes.CANCEL_BOOKING_ALREADY_SUCCEEDED_REFUND_FAILED.toString(),
                        PlacePassExceptionCodes.CANCEL_BOOKING_ALREADY_SUCCEEDED_REFUND_FAILED.getDescription());

            }
        }

        return satisfied;
    }

}
