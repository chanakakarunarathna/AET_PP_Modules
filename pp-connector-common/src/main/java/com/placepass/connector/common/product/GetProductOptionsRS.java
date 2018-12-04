package com.placepass.connector.common.product;

import com.placepass.connector.common.common.BaseRS;

public class GetProductOptionsRS extends BaseRS {
	
	private ProductOptionGroup productOptionGroup;

	public ProductOptionGroup getProductOptionGroup() {
		return productOptionGroup;
	}

	public void setProductOptionGroup(ProductOptionGroup productOptionGroup) {
		this.productOptionGroup = productOptionGroup;
	}

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetProductOptionsRS [" + (productOptionGroup != null ? "productOptionGroup=" + productOptionGroup : "")
                + "]";
    }

}
