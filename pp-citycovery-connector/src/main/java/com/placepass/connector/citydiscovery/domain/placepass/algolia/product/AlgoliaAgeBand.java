package com.placepass.connector.citydiscovery.domain.placepass.algolia.product;

public class AlgoliaAgeBand {

    private String id;

    private int minAge;

    private int maxAge;

    private String type;

    private String rangeDisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRangeDisplay() {
        return rangeDisplay;
    }

    public void setRangeDisplay(String rangeDisplay) {
        this.rangeDisplay = rangeDisplay;
    }

}
