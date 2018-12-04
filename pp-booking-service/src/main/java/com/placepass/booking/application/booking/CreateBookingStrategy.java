package com.placepass.booking.application.booking;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.placepass.booking.domain.booking.Booking;
import com.placepass.booking.domain.booking.Payment;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.booking.domain.platform.Status;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.exutil.BadRequestException;
import com.placepass.exutil.InternalErrorException;
import com.placepass.exutil.PlacePassExceptionCodes;

/**
 * @author naveen.w
 * 
 *         This strategy handles PlatformStatuses for create booking scenario.
 *
 */
@Component
public class CreateBookingStrategy {

    public void processPlatformStatus(Status bStatus, Payment payment, Booking booking) {

        PlatformStatus status = bStatus.getStatus();
        Map<String, String> exStatus = bStatus.getExternalStatus();

        if (status.equals(PlatformStatus.CARD_ERROR)) {

            throw new BadRequestException(PlacePassExceptionCodes.CARD_ERROR.toString(),
                    PlacePassExceptionCodes.CARD_ERROR.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.INTERNAL_SERVER_ERROR)) {

            throw new InternalErrorException(PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.toString(),
                    PlacePassExceptionCodes.INTERNAL_SERVER_ERROR.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.UNKNOWN_PAYMENT_GATEWAY_ERROR)) {

            throw new BadRequestException(PlacePassExceptionCodes.UNKNOWN_PAYMENT_GATEWAY_ERROR.toString(),
                    PlacePassExceptionCodes.UNKNOWN_PAYMENT_GATEWAY_ERROR.getDescription(),
                    payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.CARD_CVV_FAILURE)) {

            throw new BadRequestException(PlacePassExceptionCodes.CARD_CVV_FAILURE.toString(),
                    PlacePassExceptionCodes.CARD_CVV_FAILURE.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.CARD_AVS_FAILURE)) {

            throw new BadRequestException(PlacePassExceptionCodes.CARD_AVS_FAILURE.toString(),
                    PlacePassExceptionCodes.CARD_AVS_FAILURE.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.CARD_DECLINED)) {

            throw new BadRequestException(PlacePassExceptionCodes.CARD_DECLINED.toString(),
                    PlacePassExceptionCodes.CARD_DECLINED.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.CARD_EXPIRED)) {

            throw new BadRequestException(PlacePassExceptionCodes.CARD_EXPIRED.toString(),
                    PlacePassExceptionCodes.CARD_EXPIRED.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.CARD_ERROR)) {

            throw new BadRequestException(PlacePassExceptionCodes.CARD_ERROR.toString(),
                    PlacePassExceptionCodes.CARD_ERROR.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.INSUFFICIENT_FUNDS)) {

            throw new BadRequestException(PlacePassExceptionCodes.INSUFFICIENT_FUNDS.toString(),
                    PlacePassExceptionCodes.INSUFFICIENT_FUNDS.getDescription(), payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.INVALID_PAYMENT_DETAILS)) {

            throw new BadRequestException(PlacePassExceptionCodes.INVALID_PAYMENT_DETAILS.toString(),
                    PlacePassExceptionCodes.INVALID_PAYMENT_DETAILS.getDescription(),
                    payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.PAYMENT_TIMEOUT) || status.equals(PlatformStatus.PAYMENT_FAILED)) {

            throw new InternalErrorException(PlacePassExceptionCodes.PAYMENT_GATEWAY_TIMEOUT.toString(),
                    PlacePassExceptionCodes.PAYMENT_GATEWAY_TIMEOUT.getDescription(),
                    payment.getExternalStatuses() + "");

        } else if (status.equals(PlatformStatus.BOOKING_TIMEOUT)) {

            throw new InternalErrorException(PlacePassExceptionCodes.BOOKING_VENDOR_TIMEOUT.toString(),
                    PlacePassExceptionCodes.BOOKING_VENDOR_TIMEOUT.getDescription(),
                    booking.getBookingStatus().getExternalStatus() + "");
        } else if (status.equals(PlatformStatus.BOOKING_FAILED)) {

            throw new InternalErrorException(PlacePassExceptionCodes.BOOKING_FAILED.toString(),
                    PlacePassExceptionCodes.BOOKING_FAILED.getDescription(),
                    booking.getBookingStatus().getExternalStatus() + "");
        } else if (status.equals(PlatformStatus.BOOKING_REJECTED)) {

            if (String.valueOf(VendorErrorCode.PRODUCT_NOT_FOUND.getId()).equals(exStatus.get("code"))
                    || String.valueOf(VendorErrorCode.PRODUCT_UNAVAILABLE.getId()).equals(exStatus.get("code"))) {

                throw new InternalErrorException(PlacePassExceptionCodes.PRODUCT_NOT_AVAILABLE.toString(),
                        PlacePassExceptionCodes.PRODUCT_NOT_AVAILABLE.getDescription(),
                        booking.getBookingStatus().getExternalStatus() + "");
            } else if (String.valueOf(VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId())
                    .equals(exStatus.get("code"))) {

                throw new InternalErrorException(PlacePassExceptionCodes.BOOKING_VENDOR_TIMEOUT.toString(),
                        PlacePassExceptionCodes.BOOKING_VENDOR_TIMEOUT.getDescription(),
                        booking.getBookingStatus().getExternalStatus() + "");

            } else if (String.valueOf(VendorErrorCode.BOOKING_DETAILS_INCOMPLETE.getId())
                    .equals(exStatus.get("code"))) {

                throw new InternalErrorException(PlacePassExceptionCodes.BOOKING_DETAILS_INCOMPLETE.toString(),
                        PlacePassExceptionCodes.BOOKING_DETAILS_INCOMPLETE.getDescription(),
                        booking.getBookingStatus().getExternalStatus() + "");

            } else if (String.valueOf(VendorErrorCode.BOOKING_PARAMS_INVALID.getId()).equals(exStatus.get("code"))) {

                throw new InternalErrorException(PlacePassExceptionCodes.BOOKING_PARAMS_INVALID.toString(),
                        PlacePassExceptionCodes.BOOKING_PARAMS_INVALID.getDescription(),
                        booking.getBookingStatus().getExternalStatus() + "");

            } else if (String.valueOf(VendorErrorCode.PAYMENT_DECLINED.getId()).equals(exStatus.get("code"))
                    || String.valueOf(VendorErrorCode.UNKNOWN_VENDOR_ERROR.getId()).equals(exStatus.get("code"))) {

                throw new InternalErrorException(PlacePassExceptionCodes.UNKNOWN_VENDOR_ERROR.toString(),
                        PlacePassExceptionCodes.UNKNOWN_VENDOR_ERROR.getDescription(),
                        booking.getBookingStatus().getExternalStatus() + "");

            } else {

                throw new InternalErrorException(PlacePassExceptionCodes.BOOKING_REJECTED.toString(),
                        PlacePassExceptionCodes.BOOKING_REJECTED.getDescription(),
                        booking.getBookingStatus().getExternalStatus() + "");
            }

        }
    }
}
