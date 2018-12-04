package com.placepass.exutil;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String placepassStatusCode) {
        super(HttpStatus.NOT_FOUND, placepassStatusCode);
    }

    public NotFoundException(String placepassStatusCode, String message) {
        super(HttpStatus.NOT_FOUND, placepassStatusCode, message);
    }

    public NotFoundException(String placepassStatusCode, String message, String externalMessage) {
        super(HttpStatus.NOT_FOUND, placepassStatusCode, message, externalMessage);
    }

    public NotFoundException(String placepassStatusCode, String message, Throwable e) {
        super(HttpStatus.NOT_FOUND, placepassStatusCode, message, e);
    }

}
