package com.placepass.userservice.controller.dto;

import io.swagger.annotations.ApiModel;

/**
 * The Class UpdatePasswordRQ.
 */
@ApiModel(value = "UpdatePasswordRQ")
public class UpdatePasswordRQ {
	
	private String previousPassword;
	
	/** The password. */
	private String newPassword;
	
	/**
	 * Instantiates a new update password rq.
	 */
	public UpdatePasswordRQ(){
		
	}

	/**
	 * Instantiates a new update password rq.
	 *
	 * @param previousPassword the previous password
	 * @param newPassword the new password
	 */
	public UpdatePasswordRQ(String previousPassword, String newPassword) {
		super();
		this.previousPassword = previousPassword;
		this.newPassword = newPassword;
	}

	/**
	 * Gets the previous password.
	 *
	 * @return the previous password
	 */
	public String getPreviousPassword() {
		return previousPassword;
	}

	/**
	 * Gets the new password.
	 *
	 * @return the new password
	 */
	public String getNewPassword() {
		return newPassword;
	}

	
	
}
