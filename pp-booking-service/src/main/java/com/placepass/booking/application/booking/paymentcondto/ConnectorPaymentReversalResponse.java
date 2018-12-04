package com.placepass.booking.application.booking.paymentcondto;

import java.time.Instant;
import java.util.Map;

public class ConnectorPaymentReversalResponse {

    private Instant processedTime;

    private String extReversalTxId;

    private String reversalReason;

    private Map<String, String> externalStatuses;

    private PaymentProcessStatus paymentStatus;

    public Instant getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Instant processedTime) {
        this.processedTime = processedTime;
    }

    public String getExtReversalTxId() {
        return extReversalTxId;
    }

    public void setExtReversalTxId(String extReversalTxId) {
        this.extReversalTxId = extReversalTxId;
    }

    public Map<String, String> getExternalStatuses() {
        return externalStatuses;
    }

    public void setExternalStatuses(Map<String, String> externalStatuses) {
        this.externalStatuses = externalStatuses;
    }

    public PaymentProcessStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentProcessStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReversalReason() {
        return reversalReason;
    }

    public void setReversalReason(String reversalReason) {
        this.reversalReason = reversalReason;
    }

}
