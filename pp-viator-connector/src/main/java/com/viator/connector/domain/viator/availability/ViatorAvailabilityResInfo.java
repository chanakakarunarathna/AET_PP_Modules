package com.viator.connector.domain.viator.availability;

import java.util.List;

public class ViatorAvailabilityResInfo {
    private String productCode;

    private String firstAvailableDate;

    private String lastAvailableDate;

    private List<ViatorAvailabilityResAvailability> availability = null;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getFirstAvailableDate() {
        return firstAvailableDate;
    }

    public void setFirstAvailableDate(String firstAvailableDate) {
        this.firstAvailableDate = firstAvailableDate;
    }

    public String getLastAvailableDate() {
        return lastAvailableDate;
    }

    public void setLastAvailableDate(String lastAvailableDate) {
        this.lastAvailableDate = lastAvailableDate;
    }

    public List<ViatorAvailabilityResAvailability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<ViatorAvailabilityResAvailability> availability) {
        this.availability = availability;
    }
}
