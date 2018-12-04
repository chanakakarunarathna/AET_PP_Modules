package com.viator.connector.domain.viator.pricingmatrix;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorPricingMatrixResponse extends ViatorGenericResponse {

	private ViatorPricingMatrixResInfo data;

	public ViatorPricingMatrixResInfo getData() {
		return data;
	}

	public void setData(ViatorPricingMatrixResInfo data) {
		this.data = data;
	}
}
