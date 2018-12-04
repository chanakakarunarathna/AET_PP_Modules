package com.placepass.userservice.infrastructure.gigya;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class GigyaProfile.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GigyaProfile {
    /** The email. */
    private String email;
    
    /** The first name. */
    private String firstName;

    /** The last name. */
    private String lastName;

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

    
    
}
