package com.placepass.exutil;

import org.springframework.http.HttpStatus;

public class HTTPException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String statusCode;

    private String statusMessage;

    private String externalMessage;

    private HttpStatus httpStatus;

    private boolean logException = true;

    public HTTPException(HttpStatus httpStatus, String statusCode) {
        super(statusCode);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

    public HTTPException(HttpStatus httpStatus, String statusCode, String statusMessage) {
        super(statusCode);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.statusMessage = statusMessage;
    }

    public HTTPException(HttpStatus httpStatus, String statusCode, String statusMessage, String externalMessage) {
        super(statusCode);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.statusMessage = statusMessage;
        this.externalMessage = externalMessage;
    }

    public HTTPException(HttpStatus httpStatus, String statusCode, Throwable e) {
        super(statusCode, e);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

    public HTTPException(HttpStatus httpStatus, String statusCode, String statusMessage, Throwable e) {
        super(statusCode, e);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.statusMessage = statusMessage;
    }

    public HTTPException(HttpStatus httpStatus, String statusCode, String statusMessage, String externalMessage,
            Throwable e) {
        super(statusCode, e);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.statusMessage = statusMessage;
        this.externalMessage = externalMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getExternalMessage() {
        return externalMessage;
    }

    public void setExternalMessage(String externalMessage) {
        this.externalMessage = externalMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean isLogException() {
        return logException;
    }

    public void setLogException(boolean logException) {
        this.logException = logException;
    }

}
