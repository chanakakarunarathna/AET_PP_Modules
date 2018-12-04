package com.placepass.userservice.domain.user.exception;

/**
 * The Class AccessDeniedException.
 * 
 * 
 */
public class AccessDeniedException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unauthorized exception.
	 *
	 * @param message the message
	 */
	public AccessDeniedException(String message) {
		super(message);

	}

	/**
	 * Instantiates a new unauthorized exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);

	}
}
