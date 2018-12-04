package com.placepass.userservice.controller.dto;

/**
 * The Class AuthenticationRS.
 */
public class AuthenticationRS {
	
	/** The token. */
	private String token;
	
	/** The timeout. */
	private long timeout;

	/**
	 * Instantiates a new authentication rs.
	 */
	public AuthenticationRS() {
		super();
	}

	/**
	 * Instantiates a new authentication rs.
	 *
	 * @param token the token
	 * @param timeout the timeout
	 */
	public AuthenticationRS(String token, long timeout) {
		super();
		this.token = token;
		this.timeout = timeout;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	public long getTimeout() {
		return timeout;
	}

}
