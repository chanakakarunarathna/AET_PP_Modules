package com.placepass.userservice.domain.user;

import java.util.Date;

/**
 * The Class UserProfile.
 */
public class UserProfile {

    /** The title. */
    private String title;
    
    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

    /** The phone. */
    private String phone;

    /** The address. */
    private Address address;

    /** The date of birth. */
    private Date dateOfBirth;
    
    /** The phone country code. */
    private String phoneCountryCode;

    /**
     * Instantiates a new user profile.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param phone the phone
     * @param address the address
     * @param dateOfBirth the date of birth
     * @param title the title
     * @param phoneCountryCode the phone country code
     */
    public UserProfile(String firstName, String lastName, String phone, Address address, Date dateOfBirth, String title, String phoneCountryCode) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.title = title;
        this.phoneCountryCode = phoneCountryCode;
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
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
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
     * Gets the address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets the date of birth.
     *
     * @return the date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
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
     * Sets the last name.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth the new date of birth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
