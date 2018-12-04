package com.placepass.product.application.productoptions.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ProductOptionGroup")
public class ProductOptionGroupDTO {

	private String name;

	private String type;

	private String description;

	private String time;

	private List<LanguageDTO> languageList = null;

	private List<ProductOptionDTO> productOptionList = null;

	private List<ProductOptionGroupDTO> productOptionGroupList = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<LanguageDTO> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(List<LanguageDTO> languageList) {
		this.languageList = languageList;
	}

	public List<ProductOptionDTO> getProductOptionList() {
		return productOptionList;
	}

	public void setProductOptionList(List<ProductOptionDTO> productOptionList) {
		this.productOptionList = productOptionList;
	}

	public List<ProductOptionGroupDTO> getProductOptionGroupList() {
		return productOptionGroupList;
	}

	public void setProductOptionGroupList(List<ProductOptionGroupDTO> productOptionGroupList) {
		this.productOptionGroupList = productOptionGroupList;
	}

}
