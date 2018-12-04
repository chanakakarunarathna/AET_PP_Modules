package com.placepass.booking.application.cart.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.placepass.utills.email.Email;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Booker")
public class BookerDTO {

    @NotEmpty(message = "Title is required")    
    @Pattern(regexp = "\\b[M][r][s][.]\\B|\\b[M][r][.]\\B|\\b[M][s][.]\\B", message = "Invalid title format(Mr. / Mrs. / Ms.)")
    private String title;

    @NotEmpty(message = "First name is required")    
    private String firstName;

    @NotEmpty(message = "Last name is required")    
    private String lastName;

    @NotEmpty(message = "Email address is required")    
    @Email(message = "Invalid Email address provided")
    private String email;

    @NotEmpty(message = "Phone number is required")   
    @Pattern(regexp = "[ ]*[+]?[0-9 ]+", message = "Invalid Phone number provided")
    private String phoneNumber;

    @NotEmpty(message = "Country ISO code is required")    
    @Pattern(regexp = "[A-Z a-z]+", message = "Invalid country ISO code.(Numbers not allow)")
    private String countryISOCode;

    private String dateOfBirth;

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
