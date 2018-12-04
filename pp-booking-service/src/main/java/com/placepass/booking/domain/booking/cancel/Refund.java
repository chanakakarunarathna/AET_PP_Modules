package com.placepass.booking.domain.booking.cancel;

import java.time.Instant;
import java.util.Map;

import com.placepass.booking.domain.booking.Payment;
import com.placepass.booking.domain.booking.PaymentStatus;
import com.placepass.booking.domain.booking.ReversalDetails;

public class Refund {

    private String refundId;

    private float refundAmount;

    private String currencyCode;

    private String paymentGatewayName;

    private Instant processedTime;

    private String extRefundTxId;

    private Map<String, String> externalStatuses;

    private String refundReason;

    private PaymentStatus refundStatus;

    private boolean isReversalTriggered;

    private ReversalDetails reversalDetails;

    private Payment originalPayment;

    private Instant createdTime;

    private Instant updatedTime;

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public Instant getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Instant processedTime) {
        this.processedTime = processedTime;
    }

    public String getExtRefundTxId() {
        return extRefundTxId;
    }

    public void setExtRefundTxId(String extRefundTxId) {
        this.extRefundTxId = extRefundTxId;
    }

    public Map<String, String> getExternalStatuses() {
        return externalStatuses;
    }

    public void setExternalStatuses(Map<String, String> externalStatuses) {
        this.externalStatuses = externalStatuses;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public PaymentStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(PaymentStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public boolean isReversalTriggered() {
        return isReversalTriggered;
    }

    public void setReversalTriggered(boolean isReversalTriggered) {
        this.isReversalTriggered = isReversalTriggered;
    }

    public ReversalDetails getReversalDetails() {
        return reversalDetails;
    }

    public void setReversalDetails(ReversalDetails reversalDetails) {
        this.reversalDetails = reversalDetails;
    }

    public Payment getOriginalPayment() {
        return originalPayment;
    }

    public void setOriginalPayment(Payment originalPayment) {
        this.originalPayment = originalPayment;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

}
