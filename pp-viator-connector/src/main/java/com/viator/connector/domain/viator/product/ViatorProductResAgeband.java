package com.viator.connector.domain.viator.product;

public class ViatorProductResAgeband {
    private Integer sortOrder;

    private Integer ageFrom;

    private Integer ageTo;

    private Boolean adult;

    private Integer bandId;

    private String pluralDescription;

    private Boolean treatAsAdult;

    private Integer count;

    private String description;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Integer getBandId() {
        return bandId;
    }

    public void setBandId(Integer bandId) {
        this.bandId = bandId;
    }

    public String getPluralDescription() {
        return pluralDescription;
    }

    public void setPluralDescription(String pluralDescription) {
        this.pluralDescription = pluralDescription;
    }

    public Boolean getTreatAsAdult() {
        return treatAsAdult;
    }

    public void setTreatAsAdult(Boolean treatAsAdult) {
        this.treatAsAdult = treatAsAdult;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
