package com.placepass.booking.application.booking.dto;

import java.util.Map;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ManualCancelBookingRequest")
public class ManualCancelBookingRQ {

    @NotEmpty(message = "Cancellation reason is required")
    private String cancellationReasonCode;

    @NotEmpty(message = "Refund type is required")
    private String refundType;

    private float cancellationAmount;

    private Map<String, String> extendedAttributes;

    public String getCancellationReasonCode() {
        return cancellationReasonCode;
    }

    public void setCancellationReasonCode(String cancellationReasonCode) {
        this.cancellationReasonCode = cancellationReasonCode;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public float getCancellationAmount() {
        return cancellationAmount;
    }

    public void setCancellationAmount(float cancellationAmount) {
        this.cancellationAmount = cancellationAmount;
    }

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

}
