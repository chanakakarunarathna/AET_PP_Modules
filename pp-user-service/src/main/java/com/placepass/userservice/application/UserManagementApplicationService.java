package com.placepass.userservice.application;

import com.placepass.userservice.controller.dto.ForgotPasswordRQ;
import com.placepass.userservice.controller.dto.ForgotPasswordVerificationCode;
import com.placepass.userservice.controller.dto.ResendVerificationRQ;
import com.placepass.userservice.controller.dto.ResetPasswordRQ;
import com.placepass.userservice.controller.dto.RetrieveEmailRQ;
import com.placepass.userservice.controller.dto.UpdatePasswordRQ;
import com.placepass.userservice.controller.dto.UpdateUserRQ;
import com.placepass.userservice.controller.dto.UserProfileRS;
import com.placepass.userservice.controller.dto.UserRQ;
import com.placepass.userservice.controller.dto.UserRS;
import com.placepass.userservice.domain.user.UserType;

/**
 * The Interface UserManagementApplicationService.
 * 
 * @author shanakak
 */
public interface UserManagementApplicationService {

	/**
	 * Creates the user.
	 *
	 * @param partnerId the partner id
	 * @param userRQ the user rq
	 * @param userType the user type
	 * @return the user rs
	 */
	UserRS createUser(String partnerId, UserRQ userRQ, UserType userType);

	/**
	 * Update user.
	 *
	 * @param partnerId the partner id
	 * @param updateUserRQ the update user rq
	 * @param userAuthenticationTokenValue the user authentication token value
	 */
	void updateUser(String partnerId, UpdateUserRQ updateUserRQ, String userAuthenticationTokenValue);

	/**
	 * Retrieve user by email.
	 *
	 * @param partnerId the partner id
	 * @param retrieveEmailRQ the retrieve email rq
	 * @return the user rs
	 */
	UserRS retrieveUserByEmail(String partnerId, RetrieveEmailRQ retrieveEmailRQ);

	/**
	 * Verify code.
	 *
	 * @param code the code
	 */
	void verifyCode(String partnerId, String code);

	/**
	 * Update password.
	 *
	 * @param partnerId the partner id
	 * @param updatePasswordRQ the update password rq
	 */
	void updatePassword(String partnerId, UpdatePasswordRQ updatePasswordRQ, String userAuthenticationTokenValue);

	/**
	 * Verify forgot password code.
	 *
	 * @param partnerId the partner id
	 * @param code the code
	 * @return the forgot password verification code
	 */
	ForgotPasswordVerificationCode verifyForgotPasswordCode(String partnerId, String code);

	/**
	 * Forgot password.
	 *
	 * @param partnerId the partner id
	 * @param forgotPasswordRQ the forgot password rq
	 */
	void forgotPassword(String partnerId, ForgotPasswordRQ forgotPasswordRQ);

	/**
	 * Reset password.
	 *
	 * @param partnerId the partner id
	 * @param resetPasswordRQ the reset password rq
	 */
	void resetPassword(String partnerId, ResetPasswordRQ resetPasswordRQ);

	/**
	 * Resend verification.
	 *
	 * @param partnerId the partner id
	 * @param resendVerificationRQ the resend verification rq
	 */
	void resendVerification(String partnerId, ResendVerificationRQ resendVerificationRQ);

	/**
	 * Retrieve user by token.
	 *
	 * @param partnerId the partner id
	 * @param userAuthenticationTokenValue the user authentication token value
	 * @return the user profile rs
	 */
	UserProfileRS retrieveUserByToken(String partnerId, String userAuthenticationTokenValue);

}
