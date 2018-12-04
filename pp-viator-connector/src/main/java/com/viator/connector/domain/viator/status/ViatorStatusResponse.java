package com.viator.connector.domain.viator.status;

import java.util.List;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorStatusResponse extends ViatorGenericResponse {
	
	private List<ViatorStatusResponseInfo> data;

	public List<ViatorStatusResponseInfo> getData() {
		return data;
	}

	public void setData(List<ViatorStatusResponseInfo> data) {
		this.data = data;
	}

}
