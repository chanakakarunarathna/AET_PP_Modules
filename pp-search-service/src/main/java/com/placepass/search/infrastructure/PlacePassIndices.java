package com.placepass.search.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlacePassIndices {
    /* PlacePass Indices */
    @Value("${pp.search.index}")
    private String ppSearchIndex;

    @Value("${pp.search.index}")
    private String ppSearchIndexRecommended;

    @Value("${pp.search.index_topRated}")
    private String ppSearchIndexTopRated;

    @Value("${pp.search.index_popularity}")
    private String ppSearchIndexPopularity;

    @Value("${pp.search.index_priceLowHigh}")
    private String ppSearchIndexPriceLowHigh;

    @Value("${pp.search.index_priceHighLow}")
    private String ppSearchIndexPriceHighLow;
    
    @Value("${pp.shelves.index}")
    private String ppShelvesIndex;

    public String getPpSearchIndex() {
        return ppSearchIndex;
    }

    public void setPpSearchIndex(String ppSearchIndex) {
        this.ppSearchIndex = ppSearchIndex;
    }

    public String getPpSearchIndexRecommended() {
        return ppSearchIndexRecommended;
    }

    public void setPpSearchIndexRecommended(String ppSearchIndexRecommended) {
        this.ppSearchIndexRecommended = ppSearchIndexRecommended;
    }

    public String getPpSearchIndexTopRated() {
        return ppSearchIndexTopRated;
    }

    public void setPpSearchIndexTopRated(String ppSearchIndexTopRated) {
        this.ppSearchIndexTopRated = ppSearchIndexTopRated;
    }

    public String getPpSearchIndexPopularity() {
        return ppSearchIndexPopularity;
    }

    public void setPpSearchIndexPopularity(String ppSearchIndexPopularity) {
        this.ppSearchIndexPopularity = ppSearchIndexPopularity;
    }

    public String getPpSearchIndexPriceLowHigh() {
        return ppSearchIndexPriceLowHigh;
    }

    public void setPpSearchIndexPriceLowHigh(String ppSearchIndexPriceLowHigh) {
        this.ppSearchIndexPriceLowHigh = ppSearchIndexPriceLowHigh;
    }

    public String getPpSearchIndexPriceHighLow() {
        return ppSearchIndexPriceHighLow;
    }

    public void setPpSearchIndexPriceHighLow(String ppSearchIndexPriceHighLow) {
        this.ppSearchIndexPriceHighLow = ppSearchIndexPriceHighLow;
    }

    public String getPpShelvesIndex() {
        return ppShelvesIndex;
    }

    public void setPpShelvesIndex(String ppShelvesIndex) {
        this.ppShelvesIndex = ppShelvesIndex;
    }

}
