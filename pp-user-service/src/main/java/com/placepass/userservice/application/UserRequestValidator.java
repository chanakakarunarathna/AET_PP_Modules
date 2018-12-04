package com.placepass.userservice.application;

import com.placepass.userservice.controller.dto.UpdateUserRQ;
import com.placepass.userservice.controller.dto.UserRQ;
import com.placepass.userservice.domain.user.UserType;

/**
 * The Interface UserRequestValidator.
 * 
 * @author shanakak
 */
public interface UserRequestValidator {
		
	/**
	 * Validate partner id.
	 *
	 * @param partnerId the partner id
	 */
	void validatePartnerId(String partnerId);

	/**
	 * Validate user.
	 *
	 * @param partnerId the partner id
	 * @param userRQ the user rq
	 * @param userType the user type
	 */
	void validateUser(String partnerId, UserRQ userRQ, UserType userType);

	/**
	 * Validate update user.
	 *
	 * @param partnerId the partner id
	 * @param updateUserRQ the update user rq
	 */
	void validateUpdateUser(String partnerId, UpdateUserRQ updateUserRQ);
	
	/**
	 * Validate email.
	 *
	 * @param email the email
	 */
	void validateEmail(String email);

	/**
	 * Validate password.
	 *
	 * @param password the password
	 */
	void validatePassword(String password);

}
