package com.placepass.utils.pricematch;

public class PriceMatchPriceBreakDown {

    private PriceMatchPricePerAgeBand pricePerAgeBand;

    private PriceMatchTotalPricePerAgeBand totalPricePerAgeBand;

    public PriceMatchPricePerAgeBand getPricePerAgeBand() {
        return pricePerAgeBand;
    }

    public void setPricePerAgeBand(PriceMatchPricePerAgeBand pricePerAgeBand) {
        this.pricePerAgeBand = pricePerAgeBand;
    }

    public PriceMatchTotalPricePerAgeBand getTotalPricePerAgeBand() {
        return totalPricePerAgeBand;
    }

    public void setTotalPricePerAgeBand(PriceMatchTotalPricePerAgeBand totalPricePerAgeBand) {
        this.totalPricePerAgeBand = totalPricePerAgeBand;
    }

}
