package com.placepass.userservice.domain.user.exception;

/**
 * The Class VerificationCodeExpiredException.
 */
public class VerificationCodeExpiredException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new verification code expired exception.
	 *
	 * @param message the message
	 */
	public VerificationCodeExpiredException(String message) {
		super(message);

	}

	/**
	 * Instantiates a new verification code expired exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public VerificationCodeExpiredException(String message, Throwable cause) {
		super(message, cause);

	}
}
