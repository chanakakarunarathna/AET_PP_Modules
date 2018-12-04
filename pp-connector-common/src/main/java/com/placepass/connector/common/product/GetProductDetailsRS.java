package com.placepass.connector.common.product;

import com.placepass.connector.common.common.BaseRS;

public class GetProductDetailsRS extends BaseRS {
	
	private ProductDetails productDetails;

	public ProductDetails getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(ProductDetails productDetails) {
		this.productDetails = productDetails;
	}

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetProductDetailsRS [" + (productDetails != null ? "productDetails=" + productDetails : "") + "]";
    }

}
