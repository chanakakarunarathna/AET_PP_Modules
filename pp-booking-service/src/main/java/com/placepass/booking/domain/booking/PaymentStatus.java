package com.placepass.booking.domain.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PaymentStatus {
	
	PAYMENT_SUCCESS("The Payment is succuess"),
    PAYMENT_FAILED("The Payment transaction is failed"),
    PAYMENT_PENDING("The Payment transaction is pending"),
    PAYMENT_GATEWAY_PERMISSION_ERROR("Payment gateway, Permission error."),
    PAYMENT_GATEWAY_CONNECTION_ERROR("Payment gateway, Network communication failed."),
    PAYMENT_GATEWAY_AUTHENTICATION_ERROR("Payment gateway, Failure to properly authenticate yourself in the request."),
    PAYMENT_GATEWAY_CARD_ERROR("Payment gateway, Card error."),
    PAYMENT_GATEWAY_RATE_LIMIT_ERROR("Payment gateway, Too many requests hit the API too quickly"),
    PAYMENT_GATEWAY_INVALID_REQ_ERROR("Payment gateway, Invalid request error."),
    PAYMENT_GATEWAY_API_ERROR("Payment gateway, API level error"),
    PAYMENT_PROCESSING_ERROR("Looks like we had a problem processing your order. Please try again in a few minutes."),
    PAYMENT_REVERSAL_SUCCESS("The Payment reversal is succuess"),
    PAYMENT_REVERSAL_FAILED("The Payment reversal is failed"),

    PAYMENT_ISSUER_TIMEOUT("Looks like we had a problem processing your payment. Please try again in a few minutes."), 
    CARD_EXPIRED("The payment could not be completed as your card has expired. Please check the expiry date on your card."),
    PAYMENT_TX_NOT_FOUND("Looks like we had a problem processing your payment. Please try again in a few minutes.");
	
    private static final Logger logger = LoggerFactory.getLogger(PaymentStatus.class);

    private final String description;

    private PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentStatus getPaymentStatus(String name) {
        try {
            return PaymentStatus.valueOf(name);
        } catch (IllegalArgumentException e) {
            logger.error("PaymentStatus did not match for the external status: " + name);
            return PaymentStatus.PAYMENT_FAILED;
        }
    }

}
