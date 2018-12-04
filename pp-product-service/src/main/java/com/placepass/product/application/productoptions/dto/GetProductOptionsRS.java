package com.placepass.product.application.productoptions.dto;

import java.util.List;

public class GetProductOptionsRS {

	private List<ProductOptionDTO> productOptionList = null;

	private List<ProductOptionGroupDTO> productOptionGroupList = null;

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
