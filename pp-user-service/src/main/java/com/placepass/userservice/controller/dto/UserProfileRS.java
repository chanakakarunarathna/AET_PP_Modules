package com.placepass.userservice.controller.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * The Class UserProfileRS.
 * 
 */
@ApiModel(value = "UserProfileRS")
public class UserProfileRS {

    /** The id. */
    private String userId;

    /** The email. */
    private String email;
    
    /** The title. */
    private String title;

    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The phone country code. */
    private String phoneCountryCode;
    
    /** The phone. */
    private String phone;

    /** The address1. */
    private String address1;

    /** The address2. */
    private String address2;

    /** The state. */
    private String state;

    /** The city. */
    private String city;

    /** The country code. */
    private String countryCode;

    /** The zipCode. */
    private String zipCode;

    /** The date of birth. */
    private String dateOfBirth;

    /** The subscribed to newsletter. */
    private boolean subscribedToNewsletter;

    /** The additional information. */
    private List<AdditionalInformation> additionalInformation;

    /** The external user id. */
    private String externalUserId;

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone the new phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the address1.
     *
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the address1.
     *
     * @param address1 the new address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Gets the address2.
     *
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the address2.
     *
     * @param address2 the new address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the country code.
     *
     * @return the country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the country code.
     *
     * @param countryCode the new country code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Gets the zip code.
     *
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the zip code.
     *
     * @param zipCode the new zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth the new date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Checks if is subscribed to newsletter.
     *
     * @return true, if is subscribed to newsletter
     */
    public boolean isSubscribedToNewsletter() {
        return subscribedToNewsletter;
    }

    /**
     * Sets the subscribed to newsletter.
     *
     * @param subscribedToNewsletter the new subscribed to newsletter
     */
    public void setSubscribedToNewsletter(boolean subscribedToNewsletter) {
        this.subscribedToNewsletter = subscribedToNewsletter;
    }

    /**
     * Gets the additional information.
     *
     * @return the additional information
     */
    public List<AdditionalInformation> getAdditionalInformation() {
        if (additionalInformation == null) {
            additionalInformation = new ArrayList<AdditionalInformation>();
        }

        return additionalInformation;
    }

    /**
     * Gets the external user id.
     *
     * @return the external user id
     */
    public String getExternalUserId() {
        return externalUserId;
    }

    /**
     * Sets the external user id.
     *
     * @param externalUserId the new external user id
     */
    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the phone country code.
     *
     * @return the phone country code
     */
    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    /**
     * Sets the phone country code.
     *
     * @param phoneCountryCode the new phone country code
     */
    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }
    
    

}
