package com.viator.connector.domain.viator.common;

import java.util.List;

public class ViatorGenericResponse {

    private String errorReference;

    private String dateStamp;

    private String errorType;

    private List<String> errorCodes;

    private List<String> errorMessage;

    private String errorName;

    private Boolean success;

    private Integer totalCount;

    private List<String> errorMessageText;

    private String vmid;

    public String getErrorReference() {
        return errorReference;
    }

    public void setErrorReference(String errorReference) {
        this.errorReference = errorReference;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<String> getErrorMessageText() {
        return errorMessageText;
    }

    public void setErrorMessageText(List<String> errorMessageText) {
        this.errorMessageText = errorMessageText;
    }

    public String getVmid() {
        return vmid;
    }

    public void setVmid(String vmid) {
        this.vmid = vmid;
    }
}
