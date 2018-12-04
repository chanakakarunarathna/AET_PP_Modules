package com.placepass.userservice.domain.user.exception;

/**
 * The Class AuthenticationFailedException.
 */
public class AuthenticationFailedException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new authentication failed exception.
	 *
	 * @param message
	 *            the message
	 */
	public AuthenticationFailedException(String message) {
		super(message);

	}

	/**
	 * Instantiates a new authentication failed exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public AuthenticationFailedException(String message, Throwable cause) {
		super(message, cause);

	}
}
