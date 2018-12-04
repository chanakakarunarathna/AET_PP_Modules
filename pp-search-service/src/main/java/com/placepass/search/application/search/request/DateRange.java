package com.placepass.search.application.search.request;

import javax.validation.constraints.NotNull;

public class DateRange {
    
    @NotNull
    private String startDate;
    
    @NotNull
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
}
