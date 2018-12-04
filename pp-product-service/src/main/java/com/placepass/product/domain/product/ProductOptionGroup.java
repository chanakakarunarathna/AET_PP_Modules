package com.placepass.product.domain.product;

import java.util.List;

public class ProductOptionGroup {

	private String name;

	private String type;

	private String description;

	private String time;

	private List<Language> languages = null;

	private List<ProductOption> productOptions = null;

	private List<ProductOptionGroup> productOptionGroups = null;

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

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<ProductOption> getProductOptions() {
		return productOptions;
	}

	public void setProductOptions(List<ProductOption> productOptions) {
		this.productOptions = productOptions;
	}

	public List<ProductOptionGroup> getProductOptionGroups() {
		return productOptionGroups;
	}

	public void setProductOptionGroups(List<ProductOptionGroup> productOptionGroups) {
		this.productOptionGroups = productOptionGroups;
	}

}
