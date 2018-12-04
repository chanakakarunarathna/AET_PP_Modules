package com.placepass.userservice.domain.user;

/**
 * The Class Address.
 */
public class Address {

    /** The address1. */
    private String address1;

    /** The address2. */
    private String address2;

    /** The state. */
    private String state;

    /** The city. */
    private String city;

    /** The countryCode. */
    private String countryCode;

    /** The zipCode. */
    private String zipCode;

    /**
     * Instantiates a new address.
     *
     * @param address1 the address1
     * @param address2 the address2
     * @param state the state
     * @param city the city
     * @param countryCode the country code
     * @param zipCode the zip code
     */
    public Address(String address1, String address2, String state, String city, String countryCode, String zipCode) {
        super();
        this.address1 = address1;
        this.address2 = address2;
        this.state = state;
        this.city = city;
        this.countryCode = countryCode;
        this.zipCode = zipCode;
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
     * Gets the address2.
     *
     * @return the address2
     */
    public String getAddress2() {
        return address2;
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
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
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
     * Gets the zipCode.
     *
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
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
     * Sets the address2.
     *
     * @param address2 the new address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
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
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
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
     * Sets the zip code.
     *
     * @param zipCode the new zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
