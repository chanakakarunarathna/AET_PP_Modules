package com.placepass.userservice.domain.user;

/**
 * The Interface VerificationService.
 * 
 * @author shanakak
 */
public interface VerificationService {
	
	/**
	 * Creates the verification code.
	 *
	 * @param verificationCode the verification code
	 * @return the verification code
	 */
	VerificationCode createVerificationCode(VerificationCode verificationCode);
		
	/**
	 * Update verification code.
	 *
	 * @param verificationCode the verification code
	 */
	void updateVerificationCode(VerificationCode verificationCode);
	
	/**
	 * Retrieve verification code.
	 *
	 * @param partnerId the partner id
	 * @param code the code
	 * @param verificationType the verification type
	 * @return the verification code
	 */
	VerificationCode retrieveVerificationCode(String partnerId, String code, VerificationType verificationType);

}
