package com.viator.connector.domain.viator.book;

public class ViatorBookReqTraveller {

    private Integer bandId;

    private String firstname;

    private String surname;

    private String title;

    private Boolean leadTraveller;

    public Integer getBandId() {
        return bandId;
    }

    public void setBandId(Integer bandId) {
        this.bandId = bandId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getLeadTraveller() {
        return leadTraveller;
    }

    public void setLeadTraveller(Boolean leadTraveller) {
        this.leadTraveller = leadTraveller;
    }
}
