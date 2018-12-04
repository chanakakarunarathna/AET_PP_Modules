package com.placepass.userservice.domain.user.strategy;

import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.UserType;
import com.placepass.userservice.domain.user.VerificationCode;

/**
 * The Interface VerificationStrategy.
 * 
 * @author shanakak
 */
public interface VerificationStrategy {

	
	/**
	 * Creates the verification code.
	 *
	 * @param partnerId the partner id
	 * @param userType the user type
	 * @param user the user
	 * @param eventName the event name
	 * @return the verification code
	 */
	public VerificationCode createVerificationCode(String partnerId, UserType userType, User user, String eventName);
	
	/**
	 * Verify code.
	 *
	 * @param partnerId the partner id
	 * @param code the code
	 */
	public void verifyCode(String partnerId, String code);
	
	/**
	 * Expire verification code.
	 *
	 * @param verificationCode the verification code
	 */
	public void expireVerificationCode(VerificationCode verificationCode);

	
}
