package com.placepass.connector.common.cart;

import com.placepass.connector.common.common.ResultType;

public class GetProductOptionsRS {

    private ResultType resultType;

    private ProductOptionGroup productOptionGroup;

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public ProductOptionGroup getProductOptionGroup() {
        return productOptionGroup;
    }

    public void setProductOptionGroup(ProductOptionGroup productOptionGroup) {
        this.productOptionGroup = productOptionGroup;
    }

}
