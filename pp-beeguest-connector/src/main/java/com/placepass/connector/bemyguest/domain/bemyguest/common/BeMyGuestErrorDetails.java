package com.placepass.connector.bemyguest.domain.bemyguest.common;

import java.util.List;

public class BeMyGuestErrorDetails {

    private String code;

    private int http_code;

    private String message;

    private List<String> reasons;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

}
