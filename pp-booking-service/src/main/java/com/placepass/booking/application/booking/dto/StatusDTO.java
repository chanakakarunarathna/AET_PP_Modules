package com.placepass.booking.application.booking.dto;

import java.util.Map;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Status")
public class StatusDTO {

    private String status;

    private Map<String, String> externalStatus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getExternalStatus() {
        return externalStatus;
    }

    public void setExternalStatus(Map<String, String> externalStatus) {
        this.externalStatus = externalStatus;
    }

}
