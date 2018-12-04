package com.stripe.payment.connector.application;

public enum TransactionFieldsConstant {

    TX_AMOUNT("The Stripe transaction amount."), TX_TYPE("The transaction type"), AVS_ERROR_RESPONSE_CODE(
            "This field is populated if there was an error when checking AVS."), AVS_POSTAL_CODE_RESPONSE_CODE(
            "This is populated if the processor supports the address verification system (AVS)."), AVS_STREET_ADDRESS_RESPONSE_CODE(
            "This is populated if the processor supports the address verification system (AVS)."), TX_CURRENCY(
            "The Stripe transaction currency."), PAYMENT_INSTRUMENT_TYPE(
            "The method of payment used to process the transaction."), MERCHANT_ACCOUNT_ID(
            "The merchant account IDs associated with transactions."), CUSTOMER_ID(
            "A string value representing an existing customer in your Vault that is associated with the transaction."), TX_STATUS(
            "The Stripe transaction status."), ORDER_ID("The order ID of the transaction."), ERROR_MESSAGE(
            "Gateway response message");

    private final String description;

    private TransactionFieldsConstant(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
