package com.placepass.booking.application.booking.dto;

import java.util.Map;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "CancelBookingRequest")
public class CancelBookingRQ {

    private String cancellationReasonCode;

    private Map<String, String> extendedAttributes;

    public Map<String, String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public String getCancellationReasonCode() {
        return cancellationReasonCode;
    }

    public void setCancellationReasonCode(String cancellationReasonCode) {
        this.cancellationReasonCode = cancellationReasonCode;
    }

}
