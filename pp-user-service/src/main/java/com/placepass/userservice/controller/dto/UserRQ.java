package com.placepass.userservice.controller.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

/**
 * The Class User.
 * 
 */
@ApiModel(value = "UserRQ")
public class UserRQ {

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
	
	/** The password. */
	private String password;
	
	/** The subscribed to newsletter. */
	private boolean subscribedToNewsletter;
	
	/** The additional information. */
	private List<AdditionalInformation> additionalInformation;
	
	/** The external user id. */
	private String externalUserId;

	/**
	 * Instantiates a new user rq.
	 */
	public UserRQ() {

	}
	
	/**
	 * Instantiates a new user rq.
	 *
	 * @param email the email
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param phone the phone
	 * @param address1 the address1
	 * @param address2 the address2
	 * @param state the state
	 * @param city the city
	 * @param countryCode the country code
	 * @param zipCode the zip code
	 * @param dateOfBirth the date of birth
	 * @param password the password
	 * @param subscribedToNewsletter the subscribed to newsletter
	 * @param externalUserId the external user id
	 * @param title the title
	 * @param phoneCountryCode the phone country code
	 */
	public UserRQ(String email, String firstName, String lastName, String phone, String address1, String address2,
			String state, String city, String countryCode, String zipCode, String dateOfBirth, String password, boolean subscribedToNewsletter, String externalUserId, String title, String phoneCountryCode) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address1 = address1;
		this.address2 = address2;
		this.state = state;
		this.city = city;
		this.countryCode = countryCode;
		this.zipCode = zipCode;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.subscribedToNewsletter = subscribedToNewsletter;
		this.externalUserId = externalUserId;
		this.title = title;
		this.phoneCountryCode = phoneCountryCode;
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
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
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
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the phone country code.
     *
     * @return the phone country code
     */
    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

	

}
