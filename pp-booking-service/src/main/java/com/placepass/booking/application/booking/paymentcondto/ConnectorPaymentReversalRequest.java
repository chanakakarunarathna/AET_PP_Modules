package com.placepass.booking.application.booking.paymentcondto;

import java.time.Instant;
import java.util.Map;

public class ConnectorPaymentReversalRequest {

    private String partnerId;

    private String userId;

    private String reversalTxId;

    private String reversalReason;

    private String bookingTxId;

    private String bookingId;

    private PaymentTransaction originalPaymentTx;

    // Mandatory: Determines payment gateway
    private String gatewayName;

    // Mandatory: Determines payment exchange
    private String exchangeName;

    private Instant txCreatedTime;

    private String paymentToken;
    
    private int refundAmount;

    private boolean isTimeoutRevesal;

    private Map<String, String> additionalAttributes;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReversalTxId() {
        return reversalTxId;
    }

    public void setReversalTxId(String reversalTxId) {
        this.reversalTxId = reversalTxId;
    }

    public PaymentTransaction getOriginalPaymentTx() {
        return originalPaymentTx;
    }

    public void setOriginalPaymentTx(PaymentTransaction originalPaymentTx) {
        this.originalPaymentTx = originalPaymentTx;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public Instant getTxCreatedTime() {
        return txCreatedTime;
    }

    public void setTxCreatedTime(Instant txCreatedTime) {
        this.txCreatedTime = txCreatedTime;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public Map<String, String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public boolean isTimeoutRevesal() {
        return isTimeoutRevesal;
    }

    public void setTimeoutRevesal(boolean isTimeoutRevesal) {
        this.isTimeoutRevesal = isTimeoutRevesal;
    }

    public String getBookingTxId() {
        return bookingTxId;
    }

    public void setBookingTxId(String bookingTxId) {
        this.bookingTxId = bookingTxId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getReversalReason() {
        return reversalReason;
    }

    public void setReversalReason(String reversalReason) {
        this.reversalReason = reversalReason;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }

}
