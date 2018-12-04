package com.stripe.payment.connector.application;

public enum PaymentProcessStatus {

	PAYMENT_SUCCESS("The Payment is succuess"),
    PAYMENT_FAILED("The Payment transaction is failed"),
    PAYMENT_PENDING("The Payment transaction is pending"),
    PAYMENT_GATEWAY_PERMISSION_ERROR("Stripe payment gateway, Permission error."),
    PAYMENT_GATEWAY_CONNECTION_ERROR("Stripe payment gateway, Network communication with Stripe failed."),
    PAYMENT_GATEWAY_AUTHENTICATION_ERROR("Stripe payment gateway, Failure to properly authenticate yourself in the request."),
    PAYMENT_GATEWAY_CARD_ERROR("Stripe payment gateway, Card error."),
    PAYMENT_GATEWAY_RATE_LIMIT_ERROR("Stripe payment gateway, Too many requests hit Stripe API too quickly"),
    PAYMENT_GATEWAY_INVALID_REQ_ERROR("Stripe payment gateway, Invalid request error."),
    PAYMENT_GATEWAY_API_ERROR("Stripe payment gateway, API level error"),
    UNKNOWN_PAYMENT_GATEWAY_ERROR("Unknown payment gateway error"),
    INSUFFICIENT_FUNDS("The payment could not be completed due to insufficient funds. You can try again with a different card."),
    INVALID_PAYMENT_DETAILS("The payment could not be completed. This could be due to incorrect card details or another issue. Please try again."),
    PAYMENT_PROCESSING_ERROR("Looks like we had a problem processing your order. Please try again in a few minutes."),
    PAYMENT_REVERSAL_SUCCESS("The Payment reversal is succuess"),
    PAYMENT_REVERSAL_FAILED("The Payment reversal is failed"),

    PAYMENT_ISSUER_TIMEOUT("Looks like we had a problem processing your order. Please try again in a few minutes."), 
    CARD_EXPIRED("The payment could not be completed as your card has expired. Please check the expiry date on your card."),
    PAYMENT_TX_NOT_FOUND("Looks like we had a problem processing your order. Please try again in a few minutes."),

    PARTNER_CONFIG_NOT_FOUND("No Partner Config Found"),
    PAYMENT_GATEWAY_INVALID_NUMBER_CARD_ERROR("The card number is not a valid credit card number."),
    PAYMENT_GATEWAY_INVALID_EXIPRY_MONTH_ERROR("The card's expiration month is invalid."),
    PAYMENT_GATEWAY_INVALID_EXIPRY_YEAR_ERROR("The card's expiration year is invalid."),
    PAYMENT_GATEWAY_INVALID_CVC_ERROR("The card's security code is invalid."),
    PAYMENT_GATEWAY_INVALID_SWIPE_DATA("The card's swipe data is invalid."),
    PAYMENT_GATEWAY_INCORRECT_NUMBER_CARD_ERROR("The card number is incorrect."),
    PAYMENT_GATEWAY_EXPIRED_CARD_ERROR("The card has expired."),
    PAYMENT_GATEWAY_INCORRECT_CVC_ERROR("The card's security code is incorrect."),
    PAYMENT_GATEWAY_INCORRECT_ZIP_ERROR("The card's zip code failed validation."),
    PAYMENT_GATEWAY_CARD_DECLINED("The card was declined."),
    PAYMENT_GATEWAY_MISSING_CARD_ERROR("There is no card on a customer that is being charged."),
    PAYMENT_GATEWAY_PROCESSING_ERROR("An error occurred while processing the card.");

    private final String description;

    private PaymentProcessStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
