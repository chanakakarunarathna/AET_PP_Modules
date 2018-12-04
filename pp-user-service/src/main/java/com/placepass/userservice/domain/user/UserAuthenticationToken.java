package com.placepass.userservice.domain.user;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

/**
 * The Class UserAuthenticationToken.
 */
@RedisHash(value = "user_authentication_token")
public class UserAuthenticationToken {

	/** The id. */
	@Id
	private String id;

	/** The user id. */
	private String userId;

	/** The partner id. */
	private String partnerId;
	
	/** The username. */
	private String username;
	
	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;
	
	/** The user permissions. */
	private List<UserPermission> userPermissions;
	
	/** The user type. */
	private UserType userType;

	/** The created date. */
	private Date createdDate;

	/** The modified date. */
	private Date modifiedDate;
	
	/** The modified date. */
	@TimeToLive
	private long expireToken;

	/** The version. */
	private String version = "1.0.0";

	
	/**
	 * Instantiates a new user authentication token.
	 */
	public UserAuthenticationToken(){
		super();
	}
	
	/**
	 * Instantiates a new user authentication token.
	 *
	 * @param id the id
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @param userType the user type
	 * @param expireToken the expire token
	 */
	public UserAuthenticationToken(String id, String partnerId, String userId,  UserType userType, long expireToken) {
		super();
		this.id = id;
		this.partnerId = partnerId;
		this.userId = userId;
		this.userType = userType;
		this.expireToken = expireToken;
	}
	
	/**
	 * Instantiates a new user authentication token.
	 *
	 * @param id the id
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @param userType the user type
	 * @param expireToken the expire token
	 * @param username the username
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param permissions the permissions
	 */
	public UserAuthenticationToken(String id, String partnerId, String userId,  UserType userType, long expireToken, String username, String firstName, String lastName, List<UserPermission> permissions) {
		super();
		this.id = id;
		this.partnerId = partnerId;
		this.userId = userId;
		this.userType = userType;
		this.expireToken = expireToken;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userPermissions = permissions;
	}
	
	
	/**
	 * Instantiates a new user authentication token.
	 *
	 * @param id the id
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @param userType the user type
	 * @param expireToken the expire token
	 * @param permissions the permissions
	 */
	public UserAuthenticationToken(String id, String partnerId, String userId,  UserType userType, long expireToken, List<UserPermission> permissions) {
		super();
		this.id = id;
		this.partnerId = partnerId;
		this.userId = userId;
		this.userType = userType;
		this.expireToken = expireToken;
		this.userPermissions = permissions;
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
	
	/**
	 * Gets the user type.
	 *
	 * @return the user type
	 */
	public UserType getUserType() {
		return userType;
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
	 * Gets the user permissions.
	 *
	 * @return the user permissions
	 */
	public List<UserPermission> getUserPermissions() {
		return userPermissions;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
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
	
	
}
