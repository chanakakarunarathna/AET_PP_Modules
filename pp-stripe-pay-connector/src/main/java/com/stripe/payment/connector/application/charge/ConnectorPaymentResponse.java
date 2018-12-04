package com.stripe.payment.connector.application.charge;

import java.time.Instant;
import java.util.Map;

import com.stripe.payment.connector.application.PaymentProcessStatus;
import com.stripe.payment.connector.application.ReversalDetails;

public class ConnectorPaymentResponse {

    private CardInfo cardInfo;

    private Instant processedTime;

    private int amountTendered;

    private String extPaymentTxRefId;

    private Map<String, String> externalStatuses;

    private PaymentProcessStatus paymentStatus;

    private boolean paymentReversalTriggered;

    private ReversalDetails reversalDetails;

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public Instant getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Instant processedTime) {
        this.processedTime = processedTime;
    }

    public int getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(int amountTendered) {
        this.amountTendered = amountTendered;
    }

    public String getExtPaymentTxRefId() {
        return extPaymentTxRefId;
    }

    public void setExtPaymentTxRefId(String extPaymentTxRefId) {
        this.extPaymentTxRefId = extPaymentTxRefId;
    }

    public Map<String, String> getExternalStatuses() {
        return externalStatuses;
    }

    public void setExternalStatuses(Map<String, String> externalStatuses) {
        this.externalStatuses = externalStatuses;
    }

    public boolean isPaymentReversalTriggered() {
        return paymentReversalTriggered;
    }

    public void setPaymentReversalTriggered(boolean paymentReversalTriggered) {
        this.paymentReversalTriggered = paymentReversalTriggered;
    }

    public ReversalDetails getReversalDetails() {
        return reversalDetails;
    }

    public void setReversalDetails(ReversalDetails reversalDetails) {
        this.reversalDetails = reversalDetails;
    }

    public PaymentProcessStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentProcessStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
