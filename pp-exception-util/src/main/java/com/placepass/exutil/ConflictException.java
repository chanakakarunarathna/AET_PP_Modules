package com.placepass.exutil;

import org.springframework.http.HttpStatus;

public class ConflictException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public ConflictException(String placepassStatusCode, String message, String externalMessage) {
        super(HttpStatus.CONFLICT, placepassStatusCode, message, externalMessage);
    }

    public ConflictException(String placepassStatusCode, String message) {
        super(HttpStatus.CONFLICT, placepassStatusCode, message);
    }

    public ConflictException(String placepassStatusCode) {
        super(HttpStatus.CONFLICT, placepassStatusCode);
    }

}
