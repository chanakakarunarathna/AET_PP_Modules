package com.viator.connector.domain.viator.tourgrades;

public class ViatorTourgradesResAgeBandDetail {
	
    private Integer bandId;

    private Integer count;

    private Double bandTotal;

    private Double pricePerTraveler;

    private String pricePerTravelerFormatted;

    private String bandTotalFormatted;

    private String currencyCode;

    public Integer getBandId() {
        return bandId;
    }

    public void setBandId(Integer bandId) {
        this.bandId = bandId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getBandTotal() {
        return bandTotal;
    }

    public void setBandTotal(Double bandTotal) {
        this.bandTotal = bandTotal;
    }

    public Double getPricePerTraveler() {
        return pricePerTraveler;
    }

    public void setPricePerTraveler(Double pricePerTraveler) {
        this.pricePerTraveler = pricePerTraveler;
    }

    public String getPricePerTravelerFormatted() {
        return pricePerTravelerFormatted;
    }

    public void setPricePerTravelerFormatted(String pricePerTravelerFormatted) {
        this.pricePerTravelerFormatted = pricePerTravelerFormatted;
    }

    public String getBandTotalFormatted() {
        return bandTotalFormatted;
    }

    public void setBandTotalFormatted(String bandTotalFormatted) {
        this.bandTotalFormatted = bandTotalFormatted;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
