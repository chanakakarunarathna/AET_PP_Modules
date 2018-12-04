package com.stripe.payment.connector.application.charge;

import java.time.Instant;
import java.util.Map;

public class ConnectorPaymentRequest {

    private String partnerId;

    private String userId;

    private String merchantId;

    private String locationId;

    private String paymentTxId;

    private String paymentStatementDescriptor;

    private String bookingId;

    private String bookingTxId;

    private Instant txCreatedTime;

    private String gatewayName;

    private String exchangeName;

    private String paymentToken;

    private int paymentAmount;

    private String formattedPaymentAmount;

    private Map<String, String> additionalAttributes;

    public String getFormattedPaymentAmount() {
        return this.formattedPaymentAmount;
    }

    public void setFormattedPaymentAmount(String formattedPaymentAmount) {
        this.formattedPaymentAmount = formattedPaymentAmount;
    }

    public Instant getTxCreatedTime() {
        return txCreatedTime;
    }

    public void setTxCreatedTime(Instant txCreatedTime) {
        this.txCreatedTime = txCreatedTime;
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

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getPaymentTxId() {
        return paymentTxId;
    }

    public void setPaymentTxId(String paymentTxId) {
        this.paymentTxId = paymentTxId;
    }

    public String getPaymentStatementDescriptor() {
		return paymentStatementDescriptor;
	}

	public void setPaymentStatementDescriptor(String paymentStatementDescriptor) {
		this.paymentStatementDescriptor = paymentStatementDescriptor;
    }

    public Map<String, String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public String getBookingTxId() {
        return bookingTxId;
    }

    public void setBookingTxId(String bookingTxId) {
        this.bookingTxId = bookingTxId;
    }

}
