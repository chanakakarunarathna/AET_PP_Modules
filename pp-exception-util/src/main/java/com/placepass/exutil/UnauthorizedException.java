package com.placepass.exutil;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String placepassStatusCode, String message) {
        super(HttpStatus.UNAUTHORIZED, placepassStatusCode, message);
    }

    public UnauthorizedException(String placepassStatusCode, String message, String externalMessage) {
        super(HttpStatus.UNAUTHORIZED, placepassStatusCode, message, externalMessage);
    }

    public UnauthorizedException(String placepassStatusCode, String message, Throwable e) {
        super(HttpStatus.UNAUTHORIZED, placepassStatusCode, message, e);
    }

}
