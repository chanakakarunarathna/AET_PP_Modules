package com.viator.connector.domain.viator.availability;

import java.util.List;

import com.viator.connector.domain.viator.common.AgeBand;

public class ViatorAvailabilityRequest {

    private String productCode;

    private String month;

    private String year;

    private String currencyCode;

    private List<AgeBand> ageBands;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<AgeBand> getAgeBands() {
        return ageBands;
    }

    public void setAgeBands(List<AgeBand> ageBands) {
        this.ageBands = ageBands;
    }

}
