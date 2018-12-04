package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import java.util.List;

public class ActivityAvailabilityResultType {

    private int code;

    private List<String> extendedAttributes;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getExtendedAttributes() {
        return extendedAttributes;
    }

    public void setExtendedAttributes(List<String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
