package com.gobe.connector.domain.gobe.product;

import java.util.List;

/**
 * Created on 8/7/2017.
 */
public class GobeProductsRS {
    private List<GobeProduct> products;

    private int totalProductCount;

    public List<GobeProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GobeProduct> products) {
        this.products = products;
    }

    public int getTotalProductCount() {
        return totalProductCount;
    }

    public void setTotalProductCount(int totalProductCount) {
        this.totalProductCount = totalProductCount;
    }
}
