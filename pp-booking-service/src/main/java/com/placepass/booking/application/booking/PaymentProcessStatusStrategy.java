package com.placepass.booking.application.booking;

import org.springframework.stereotype.Component;

import com.placepass.booking.application.booking.paymentcondto.PaymentProcessStatus;
import com.placepass.booking.domain.platform.PlatformStatus;

/**
 * @author naveen.w
 * 
 * This Strategy handles PaymentProcessStatus to PlatformStatus mapping
 */
@Component
public class PaymentProcessStatusStrategy {

    public PlatformStatus getPlatformStatus(PaymentProcessStatus ppStatus) {
        PlatformStatus status = null;

        if (ppStatus.equals(PaymentProcessStatus.PAYMENT_ISSUER_TIMEOUT)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_PROCESSING_ERROR)) {
            status = PlatformStatus.PAYMENT_TIMEOUT;
        } else if (ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_PERMISSION_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_AUTHENTICATION_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_RATE_LIMIT_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_REQ_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_CONNECTION_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PARTNER_CONFIG_NOT_FOUND)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_MISSING_CARD_ERROR)) {
            status = PlatformStatus.INTERNAL_SERVER_ERROR;
        } else if (ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_CARD_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_NUMBER_CARD_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_EXIPRY_MONTH_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_EXIPRY_YEAR_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_CVC_ERROR)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_SWIPE_DATA)
                || ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INCORRECT_NUMBER_CARD_ERROR)) {
            status = PlatformStatus.CARD_ERROR;
        } else if (ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_EXPIRED_CARD_ERROR)) {
            status = PlatformStatus.CARD_EXPIRED;
        } else if (ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INCORRECT_CVC_ERROR)) {
            status = PlatformStatus.CARD_CVV_FAILURE;
        } else if (ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_INCORRECT_ZIP_ERROR)) {
            status = PlatformStatus.CARD_AVS_FAILURE;
        } else if (ppStatus.equals(PaymentProcessStatus.PAYMENT_GATEWAY_CARD_DECLINED)) {
            status = PlatformStatus.CARD_DECLINED;
        } else {
            status = PlatformStatus.UNKNOWN_PAYMENT_GATEWAY_ERROR;
        }

        return status;
    }
}
