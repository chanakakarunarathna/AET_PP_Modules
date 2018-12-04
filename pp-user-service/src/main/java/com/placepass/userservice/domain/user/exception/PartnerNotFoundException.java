package com.placepass.userservice.domain.user.exception;

/**
 * The Class PartnerNotFoundException.
 * 
 * 
 */
public class PartnerNotFoundException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new partner not found exception.
     *
     * @param message the message
     */
    public PartnerNotFoundException(String message) {
        super(message);

    }

    /**
     * Instantiates a new partner not found exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public PartnerNotFoundException(String message, Throwable cause) {
        super(message, cause);

    }
}
