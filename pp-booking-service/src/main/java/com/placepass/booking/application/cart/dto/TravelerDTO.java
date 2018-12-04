package com.placepass.booking.application.cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Traveler")
public class TravelerDTO {

    @NotEmpty(message = "Title is required")    
    @Pattern(regexp = "\\b[M][r][s][.]\\B|\\b[M][r][.]\\B|\\b[M][s][.]\\B", message = "Invalid title format (Mr. / Mrs. / Ms.)")
    private String title;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")    
    private String lastName;

    private String dateOfBirth;

    private String email;

    private String phoneNumber;

    private String countryISOCode;

    private boolean isLeadTraveler;

    @NotNull(message = "Age band id is required")
    private Integer ageBandId;

    /* private String ageBandLabel; */

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

    public String getCountryISOCode() {
        return countryISOCode;
    }

    public void setCountryISOCode(String countryISOCode) {
        this.countryISOCode = countryISOCode;
    }

    public boolean isLeadTraveler() {
        return isLeadTraveler;
    }

    public void setLeadTraveler(boolean isLeadTraveler) {
        this.isLeadTraveler = isLeadTraveler;
    }

    public Integer getAgeBandId() {
        return ageBandId;
    }

    public void setAgeBandId(Integer ageBandId) {
        this.ageBandId = ageBandId;
    }

}
