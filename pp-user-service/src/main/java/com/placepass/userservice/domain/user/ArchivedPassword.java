package com.placepass.userservice.domain.user;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class ArchivedPassword.
 */
@Document(collection = "archived_passwords")
public class ArchivedPassword {

	/** The id. */
	@Id
	private String id;
	
	/** The partner id. */
	private String partnerId;
	
	/** The user id. */
	private String userId;
	
	/** The created date. */
	private Date createdDate;

	/** The modified date. */
	private Date modifiedDate;
	
	/** The password. */
	private String password;
	
	/** The version. */
	private String version = "1.0.0";

	/**
	 * Instantiates a new archived password.
	 *
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @param password the password
	 */
	public ArchivedPassword(String partnerId, String userId, String password) {
		super();
		this.partnerId = partnerId;
		this.userId = userId;
		this.password = password;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the modified date.
	 *
	 * @return the modified date
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * Sets the modified date.
	 *
	 * @param modifiedDate the new modified date
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the partner id.
	 *
	 * @return the partner id
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	
	
	
	
}
