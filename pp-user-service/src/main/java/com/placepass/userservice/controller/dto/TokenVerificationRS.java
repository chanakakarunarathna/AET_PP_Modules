package com.placepass.userservice.controller.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class TokenVerificationRS.
 */
public class TokenVerificationRS {
	
	/** The user id. */
	private String userId;
	
	/** The username. */
	private String username;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The permissions. */
	private List<UserPermission> permissions = new ArrayList<UserPermission>();
	
	/**
	 * Instantiates a new token verification rs.
	 */
	public TokenVerificationRS(){
		super();
	}
	
	/**
	 * Instantiates a new token verification rs.
	 *
	 * @param userId the user id
	 * @param username the username
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param permissions the permissions
	 */
	public TokenVerificationRS(String userId, String username, String firstName, String lastName, List<UserPermission> permissions) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.permissions = permissions;
	}
	
	/**
	 * Instantiates a new token verification rs.
	 *
	 * @param userId the user id
	 */
	public TokenVerificationRS(String userId) {
		super();
		this.userId = userId;
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
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the permissions.
	 *
	 * @return the permissions
	 */
	public List<UserPermission> getPermissions() {
		return permissions;
	}
		
}
