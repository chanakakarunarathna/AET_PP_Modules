package com.placepass.connector.common.booking;

public class BookingQuestion {

	private String title;

	private Integer questionId;

	private String StringQuestionId;

	private String subTitle;

	private Boolean required;

	private String message;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getStringQuestionId() {
		return StringQuestionId;
	}

	public void setStringQuestionId(String stringQuestionId) {
		StringQuestionId = stringQuestionId;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
