package com.placepass.userservice.domain.user;

/**
 * The Interface UserManagementService.
 * 
 * @author shanakak
 */
public interface UserManagementService {

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user
	 */
	User createUser(User user);

	/**
	 * Update user.
	 *
	 * @param user the user
	 */
	void updateUser(User user);

	/**
	 * Retrieve user by email.
	 *
	 * @param partnerId the partner id
	 * @param email the email
	 * @return the user
	 */
	User retrieveUserByEmail(String partnerId, String email);
	
	/**
	 * Retrieve user.
	 *
	 * @param partnerId the partner id
	 * @param id the id
	 * @return the user
	 */
	User retrieveUser(String partnerId, String id);
	
	/**
	 * Retrieve user by external user id.
	 *
	 * @param partnerId the partner id
	 * @param externalUserId the external user id
	 * @return the user
	 */
	User retrieveUserByExternalUserId(String partnerId, String externalUserId);

}
