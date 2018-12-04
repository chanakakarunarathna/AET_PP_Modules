package com.viator.connector.domain.viator.book;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorBookResponse extends ViatorGenericResponse {

	private ViatorBookResDetails data;

	public ViatorBookResDetails getData() {
		return data;
	}

	public void setData(ViatorBookResDetails data) {
		this.data = data;
	}

}
