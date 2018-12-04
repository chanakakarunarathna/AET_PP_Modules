package com.placepass.userservice.domain.user.strategy;

import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.VerificationCode;

/**
 * The Interface CredentialRecoveryStrategy.
 */
public interface CredentialRecoveryStrategy {

	/**
	 * Creates the forgot password verification code.
	 *
	 * @param partnerId
	 *            the partner id
	 * @param user
	 *            the user
	 * @return the verification code
	 */
	public VerificationCode createForgotPasswordVerificationCode(String partnerId, User user);

	/**
	 * Validate forgot password verification code.
	 *
	 * @param partnerId the partner id
	 * @param code the code
	 * @return the verification code
	 */
	public VerificationCode validateForgotPasswordVerificationCode(String partnerId, String code);

}
