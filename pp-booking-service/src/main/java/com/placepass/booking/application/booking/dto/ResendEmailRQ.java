package com.placepass.booking.application.booking.dto;

public class ResendEmailRQ {

	private ResendEmailType emailType;
	
	private String  receiverEmail;

	public ResendEmailType getEmailType() {
		return emailType;
	}

	public void setEmailType(ResendEmailType emailType) {
		this.emailType = emailType;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	
}
