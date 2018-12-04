package com.placepass.userservice.controller.dto;

public class ResetPasswordRQ {

	private String code;
	
	private String password;
	
	public ResetPasswordRQ(){
		
	}

	public ResetPasswordRQ(String code, String password) {
		super();
		this.code = code;
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public String getPassword() {
		return password;
	}
	
	
	
}
