package com.placepass.booking.domain.booking;

import java.time.Instant;
import java.util.Map;

public class Payment {

    private String paymentId;

    private float paymentAmount;

    private String currencyCode;

    private String paymentToken;

    private String statementDescriptor;

    private String last4CardNumber;

    private String cardType;

    private String expiryDate;

    private String holderName;

    private String paymentGatewayName;

    private Instant processedTime;

    private int amountTendered;

    private String extPaymentTxId;

    private Map<String, String> externalStatuses;

    private PaymentStatus paymentStatus;

    private boolean isReversalTriggered;

    private ReversalDetails reversalDetails;

    private Instant createdTime;

    private Instant updatedTime;

    // This is deprecated, since we use a separate Refund domain to maintain refund information
    @Deprecated
    private PaymentType paymentType;

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return Dollar payment amount
     */
    public float getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    public String getLast4CardNumber() {
        return last4CardNumber;
    }

    public void setLast4CardNumber(String last4CardNumber) {
        this.last4CardNumber = last4CardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
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

    /**
     * @return cent amount returned from the payment connector
     */
    public int getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(int amountTendered) {
        this.amountTendered = amountTendered;
    }

    public String getExtPaymentTxId() {
        return extPaymentTxId;
    }

    public void setExtPaymentTxId(String extPaymentTxId) {
        this.extPaymentTxId = extPaymentTxId;
    }

    public Map<String, String> getExternalStatuses() {
        return externalStatuses;
    }

    public void setExternalStatuses(Map<String, String> externalStatuses) {
        this.externalStatuses = externalStatuses;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
