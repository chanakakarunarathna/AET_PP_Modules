package com.placepass.booking.application.booking;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.placepass.booking.application.booking.paymentcondto.ConnectorPaymentReversalResponse;
import com.placepass.booking.application.booking.paymentcondto.PaymentProcessStatus;
import com.placepass.connector.common.booking.CancelBookingRS;
import com.placepass.connector.common.booking.VendorErrorCode;
import com.placepass.booking.domain.platform.PlatformStatus;
import com.placepass.booking.domain.platform.Status;

@Component
public class CancelBookingStatusTranslator {

    public Status translate(CancelBookingRS cancelBookingCRS, ConnectorPaymentReversalResponse refundResponse) {
        Status status = new Status();
        PlatformStatus platformStatus = null;
        if (cancelBookingCRS.getResultType().getCode() == VendorErrorCode.SUCCESS.getId()) {
            if (refundResponse == null
                    || PaymentProcessStatus.PAYMENT_REVERSAL_SUCCESS == refundResponse.getPaymentStatus()) {
                platformStatus = PlatformStatus.SUCCESS;

            } else if (PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT == refundResponse.getPaymentStatus()
                    || PaymentProcessStatus.PAYMENT_PROCESSING_ERROR == refundResponse.getPaymentStatus()
                    || PaymentProcessStatus.PAYMENT_GATEWAY_CONNECTION_ERROR == refundResponse.getPaymentStatus()) {
                platformStatus = PlatformStatus.REFUND_TIMEOUT;

            } else {
                platformStatus = PlatformStatus.REFUND_FAILED;

            }
        } else if (cancelBookingCRS.getResultType().getCode() == VendorErrorCode.VENDOR_CONNECTION_TIMEOUT_ERROR.getId()
                || cancelBookingCRS.getResultType().getCode() == VendorErrorCode.VENDOR_READ_TIMEOUT_ERROR.getId()
                || cancelBookingCRS.getResultType().getCode() == VendorErrorCode.VENDOR_CONNECTOR_TIMEOUT_ERROR.getId()
                || cancelBookingCRS.getResultType().getCode() == VendorErrorCode.VENDOR_CONNECTOR_CONNECTION_ERROR
                        .getId()) {
            platformStatus = PlatformStatus.BOOKING_CANCEL_TIMEOUT;

        } else {
            platformStatus = PlatformStatus.BOOKING_CANCEL_FAILED;
        }
        status.setStatus(platformStatus);
        Map<String, String> vendorStatuses = new HashMap<>();
        vendorStatuses.put("CANCEL_BOOKING_EXTERNAL_RESULT",
                cancelBookingCRS.getResultType().getCode() + ":" + cancelBookingCRS.getResultType().getMessage());
        status.setExternalStatus(vendorStatuses);
        return status;
    }

}
