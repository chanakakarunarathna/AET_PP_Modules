package com.placepass.userservice.domain.user.exception;

/**
 * The Class PartnerInvalidException.
 * 
 * 
 */
public class PartnerInvalidException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new partner invalid exception.
     *
     * @param message the message
     */
    public PartnerInvalidException(String message) {
        super(message);

    }

    /**
     * Instantiates a new partner invalid exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PartnerInvalidException(String message, Throwable cause) {
        super(message, cause);

    }
}
