package com.placepass.exutil;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String placepassStatusCode, String message, String externalMessage) {
        super(HttpStatus.BAD_REQUEST, placepassStatusCode, message, externalMessage);
    }

    public BadRequestException(String placepassStatusCode, String message) {
        super(HttpStatus.BAD_REQUEST, placepassStatusCode, message);
    }

    public BadRequestException(String placepassStatusCode) {
        super(HttpStatus.BAD_REQUEST, placepassStatusCode);
    }

}
