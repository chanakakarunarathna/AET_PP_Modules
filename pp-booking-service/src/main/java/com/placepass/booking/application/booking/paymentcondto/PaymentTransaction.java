package com.placepass.booking.application.booking.paymentcondto;

import java.time.Instant;
import java.util.Map;

public class PaymentTransaction {

    private String partnerId;

    private String merchantId;

    private String locationId;

    private String paymentTxId;

    private String bookingId;

    private Instant txCreatedTime;

    private String gatewayName;

    private int paymentAmount;

    // ---------------- RESPONSE --------------//
    private Instant processedTime;

    private int amountTendered;

    private String extPaymentTxId;

    private Map<String, String> externalStatuses;

    private PaymentProcessStatus paymentStatus;

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

    public Map<String, String> getExternalStatuses() {
        return externalStatuses;
    }

    public void setExternalStatuses(Map<String, String> externalStatuses) {
        this.externalStatuses = externalStatuses;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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

    public String getPaymentTxId() {
        return paymentTxId;
    }

    public void setPaymentTxId(String paymentTxId) {
        this.paymentTxId = paymentTxId;
    }

    public String getExtPaymentTxId() {
        return extPaymentTxId;
    }

    public void setExtPaymentTxId(String extPaymentTxId) {
        this.extPaymentTxId = extPaymentTxId;
    }

    public PaymentProcessStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentProcessStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
