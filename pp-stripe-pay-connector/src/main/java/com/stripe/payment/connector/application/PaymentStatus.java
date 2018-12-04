package com.stripe.payment.connector.application;

public enum PaymentStatus {

    PAYMENT_TX_NOT_FOUND("Not Found Exception occured."), SETTLED("The transaction has been settled."), SETTLING(
            "The transaction is in the process of being settled."), SUBMITTED_FOR_SETTLEMENT(
            "The transaction has been submitted for settlement."), VOIDED("The transaction was voided.");

    private final String description;

    private PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
