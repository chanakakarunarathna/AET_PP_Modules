package com.viator.connector.domain.viator.tourgrades;

public class ViatorTourgradesResAgeBandsRequired {
	
    private Integer bandId;

    private Integer minimumCountRequired;

    private Integer maximumCountRequired;

    public Integer getBandId() {
        return bandId;
    }

    public void setBandId(Integer bandId) {
        this.bandId = bandId;
    }

    public Integer getMinimumCountRequired() {
        return minimumCountRequired;
    }

    public void setMinimumCountRequired(Integer minimumCountRequired) {
        this.minimumCountRequired = minimumCountRequired;
    }

    public Integer getMaximumCountRequired() {
        return maximumCountRequired;
    }

    public void setMaximumCountRequired(Integer maximumCountRequired) {
        this.maximumCountRequired = maximumCountRequired;
    }

}
