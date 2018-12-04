package com.placepass.userservice.domain.user;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class UserSecurityProfile.
 */
@Document(collection = "user_security_profiles")
public class UserSecurityProfile {

	/** The id. */
	@Id
	private String id;

	/** The partner id. */
	private String partnerId;

	/** The partner id. */
	private String userId;

	/** The password. */
	// FIXME: Change the password type to be char[] or CharSequence so that it
	// can be cleared from memory
	private String password;

	/** The system generated password. */
	private boolean systemGeneratedPassword;

	/** The created date. */
	private Date createdDate;

	/** The modified date. */
	private Date modifiedDate;

	/** The modified date. */
	private Date lastLoginDate;

	private List<UserPermission> userPermissions;

	/** The version. */
	private String version = "1.0.0";

	/**
	 * Instantiates a new user security profile.
	 *
	 * @param partnerId
	 *            the partner id
	 * @param password
	 *            the password
	 */
	public UserSecurityProfile(String partnerId, String password) {
		super();
		this.partnerId = partnerId;
		this.password = password;
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

	/**
	 * Gets the last login date.
	 *
	 * @return the last login date
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * Checks if is system generated password.
	 *
	 * @return true, if is system generated password
	 */
	public boolean isSystemGeneratedPassword() {
		return systemGeneratedPassword;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the system generated password.
	 *
	 * @param systemGeneratedPassword
	 *            the new system generated password
	 */
	public void setSystemGeneratedPassword(boolean systemGeneratedPassword) {
		this.systemGeneratedPassword = systemGeneratedPassword;
	}

	/**
	 * Sets the last login date.
	 *
	 * @param lastLoginDate
	 *            the new last login date
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId
	 *            the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userPermissions
	 */
	public List<UserPermission> getUserPermissions() {
		return userPermissions;
	}

	/**
	 * @param userPermissions
	 *            the userPermissions to set
	 */
	public void setUserPermissions(List<UserPermission> userPermissions) {
		this.userPermissions = userPermissions;
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
	 * @param createdDate
	 *            the new created date
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
	 * @param modifiedDate
	 *            the new modified date
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
