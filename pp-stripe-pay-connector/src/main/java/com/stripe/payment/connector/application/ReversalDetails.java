package com.stripe.payment.connector.application;

import java.time.Instant;
import java.util.Map;

public class ReversalDetails {

    private Instant attemptedTime;

    private boolean reversalSuccessful;

    private String reversalReason;

    private Map<String, String> externalStatuses;

    private String extReversalTxRefId;

    private Map<String, String> additionalAttributes;

    public Instant getAttemptedTime() {
        return attemptedTime;
    }

    public void setAttemptedTime(Instant attemptedTime) {
        this.attemptedTime = attemptedTime;
    }

    public boolean isReversalSuccessful() {
        return reversalSuccessful;
    }

    public void setReversalSuccessful(boolean reversalSuccessful) {
        this.reversalSuccessful = reversalSuccessful;
    }

    public Map<String, String> getExternalStatuses() {
        return externalStatuses;
    }

    public void setExternalStatuses(Map<String, String> externalStatuses) {
        this.externalStatuses = externalStatuses;
    }

    public String getExtReversalTxRefId() {
        return extReversalTxRefId;
    }

    public void setExtReversalTxRefId(String extReversalTxRefId) {
        this.extReversalTxRefId = extReversalTxRefId;
    }

    public Map<String, String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getReversalReason() {
        return reversalReason;
    }

    public void setReversalReason(String reversalReason) {
        this.reversalReason = reversalReason;
    }

}
