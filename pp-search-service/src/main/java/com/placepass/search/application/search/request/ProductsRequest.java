package com.placepass.search.application.search.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ProductsRequest {
    @NotNull
    private List<String> productIds;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
