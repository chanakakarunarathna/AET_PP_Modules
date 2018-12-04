package com.placepass.userservice.controller.dto;

import io.swagger.annotations.ApiModel;

/**
 * The Class ResendVerificationRQ.
 */
@ApiModel(value = "ResendVerificationRQ")
public class ResendVerificationRQ {
	
	private String email;

	public String getEmail() {
		return email;
	}
	
	

}
