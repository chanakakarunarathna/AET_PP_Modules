package com.gobe.connector.domain.gobe.price;


import java.util.List;

/**
 * Created on 8/7/2017.
 */
public class GobePricesRS {
    private List<GobePrice> prices;

    public List<GobePrice> getPrices() {
        return prices;
    }

    public void setPrices(List<GobePrice> prices) {
        this.prices = prices;
    }
}
