package com.placepass.userservice.controller.dto;

import io.swagger.annotations.ApiModel;

/**
 * The Class User.
 */
@ApiModel(value = "UserRS")
public class UserRS {

	/** The id. */
	private String id;

	/**
	 * Instantiates a new user rs.
	 */
	public UserRS() {

	}

	/**
	 * Instantiates a new user rs.
	 *
	 * @param id            the id
	 */
	public UserRS(String id) {
		super();
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
