package com.placepass.connector.common.common;

import java.util.HashMap;
import java.util.Map;

public class ResultType {

    private int code;

    private String message;

    private Map<String, String> extendedAttributes = new HashMap<String, String>();

    public ResultType() {
    }

    public ResultType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultType(int code, String message, Map<String, String> extendedAttributes) {
        this.code = code;
        this.message = message;
        this.extendedAttributes = extendedAttributes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the extendedAttributes
     */
    public Map<String, String> getExtendedAttributes() {
        if (extendedAttributes == null) {
            extendedAttributes = new HashMap<String, String>();
        }
        return extendedAttributes;
    }

    /**
     * @param extendedAttributes the extendedAttributes to set
     */
    public void setExtendedAttributes(Map<String, String> extendedAttributes) {
        this.extendedAttributes = extendedAttributes;
    }

}
