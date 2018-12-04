package com.stripe.payment.connector.domain.common;

import org.springframework.stereotype.Component;

import com.stripe.exception.CardException;
import com.stripe.payment.connector.application.PaymentProcessStatus;

/**
 * @author naveen.w
 * 
 *         This Strategy handles Stripe's CardException to PaymentProcessStatus mapping
 *
 */
@Component
public class CardExceptionStrategy {

    public PaymentProcessStatus getPaymentProcessStatus(CardException e) {
        PaymentProcessStatus status = null;

        if (e.getCode().equals("invalid_number")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_NUMBER_CARD_ERROR;
        } else if (e.getCode().equals("invalid_expiry_month")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_EXIPRY_MONTH_ERROR;
        } else if (e.getCode().equals("invalid_expiry_year")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_EXIPRY_YEAR_ERROR;
        } else if (e.getCode().equals("invalid_cvc")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_CVC_ERROR;
        } else if (e.getCode().equals("invalid_swipe_data")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INVALID_SWIPE_DATA;
        } else if (e.getCode().equals("incorrect_number")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INCORRECT_NUMBER_CARD_ERROR;
        } else if (e.getCode().equals("expired_card")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_EXPIRED_CARD_ERROR;
        } else if (e.getCode().equals("incorrect_cvc")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INCORRECT_CVC_ERROR;
        } else if (e.getCode().equals("incorrect_zip")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_INCORRECT_ZIP_ERROR;
        } else if (e.getCode().equals("card_declined")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_CARD_DECLINED;
        } else if (e.getCode().equals("missing")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_MISSING_CARD_ERROR;
        } else if (e.getCode().equals("processing_error")) {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_PROCESSING_ERROR;
        } else {
            status = PaymentProcessStatus.PAYMENT_GATEWAY_CARD_ERROR;
        }
        return status;
    }
}
