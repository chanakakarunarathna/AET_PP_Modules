package com.viator.connector.domain.viator.tourgrades;

import java.util.List;

import com.viator.connector.domain.viator.common.AgeBand;

public class ViatorTourgradesRequest {
    
    private String productCode;

    private String bookingDate;

    private String currencyCode;

    private List<AgeBand> ageBands = null;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
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
