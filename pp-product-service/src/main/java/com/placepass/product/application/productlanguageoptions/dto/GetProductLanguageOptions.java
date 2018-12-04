package com.placepass.product.application.productlanguageoptions.dto;

import java.util.List;

import com.placepass.product.application.productdetails.dto.KeyValuePair;

import io.swagger.annotations.ApiModel;

@ApiModel
public class GetProductLanguageOptions {

	private List<KeyValuePair<String, String>> languageServices;

	public List<KeyValuePair<String, String>> getLanguageServices() {
		return languageServices;
	}

	public void setLanguageServices(List<KeyValuePair<String, String>> langServices) {
		if (this.languageServices == null) {
			this.languageServices = langServices;
		} else {
			this.languageServices.addAll(langServices);
		}
	}
}