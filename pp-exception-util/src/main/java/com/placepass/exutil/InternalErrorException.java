package com.placepass.exutil;

import org.springframework.http.HttpStatus;

public class InternalErrorException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public InternalErrorException(String placepassStatusCode, Throwable e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, placepassStatusCode, e);
    }

    public InternalErrorException(String placepassStatusCode, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, placepassStatusCode, message);
    }

    public InternalErrorException(String placepassStatusCode, String message, Throwable e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, placepassStatusCode, message, e);
    }

    public InternalErrorException(String placepassStatusCode, String message, String externalMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, placepassStatusCode, message, externalMessage);
    }

    public InternalErrorException(String placepassStatusCode, String message, String externalMessage, Throwable e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, placepassStatusCode, message, externalMessage, e);
    }

    public InternalErrorException(Throwable e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "ERROR", e);
    }

}
