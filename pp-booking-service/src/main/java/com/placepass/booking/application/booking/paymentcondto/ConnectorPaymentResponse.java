package com.placepass.booking.application.booking.paymentcondto;

import java.time.Instant;
import java.util.Map;

public class ConnectorPaymentResponse {

    private CardInfoDTO cardInfo;

    private Instant processedTime;

    private int amountTendered;

    private String extPaymentTxRefId;

    private Map<String, String> externalStatuses;

    private PaymentProcessStatus paymentStatus;

    private boolean paymentReversalTriggered;

    private ReversalDetailsDTO reversalDetails;

    public CardInfoDTO getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfoDTO cardInfo) {
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

    public ReversalDetailsDTO getReversalDetails() {
        return reversalDetails;
    }

    public void setReversalDetails(ReversalDetailsDTO reversalDetails) {
        this.reversalDetails = reversalDetails;
    }

    public PaymentProcessStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentProcessStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
