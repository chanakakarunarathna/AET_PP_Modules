package com.placepass.connector.common.cart;

public class Traveler {

    private String title;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String email;

    private String phoneNumber;

    private String countryCode;

    private boolean isLeadTraveler;

    private int ageBandId;

    private String ageBandLabel;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isLeadTraveler() {
        return isLeadTraveler;
    }

    public void setLeadTraveler(boolean isLeadTraveler) {
        this.isLeadTraveler = isLeadTraveler;
    }

    public int getAgeBandId() {
        return ageBandId;
    }

    public void setAgeBandId(int ageBandId) {
        this.ageBandId = ageBandId;
    }

    public String getAgeBandLabel() {
        return ageBandLabel;
    }

    public void setAgeBandLabel(String ageBandLabel) {
        this.ageBandLabel = ageBandLabel;
    }

}
