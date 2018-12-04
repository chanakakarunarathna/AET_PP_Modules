package com.placepass.userservice.domain.user.strategy;

import java.util.List;
import java.util.Map;

import com.placepass.userservice.domain.user.AdditionalInformation;
import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.UserSecurityProfile;

/**
 * The Interface UserPersistanceAndRetrievalStrategy.
 * 
 * @author shanakak
 */
public interface UserPersistanceAndRetrievalStrategy {

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @param userSecurityProfile the user security profile
	 * @return the user
	 */
	User createUser(User user, UserSecurityProfile userSecurityProfile);
		
	/**
	 * Retrieve user.
	 *
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @param enableThrowException the enable throw exception
	 * @return the user
	 */
	User retrieveUser(String partnerId, String userId, boolean enableThrowException);
		
	/**
	 * Update user.
	 *
	 * @param partnerId the partner id
	 * @param previousEmail the previous email
	 * @param user the user
	 */
	void updateUser(String partnerId, String previousEmail, User user);
	
	/**
	 * Retrieve user security profile.
	 *
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @param enableThrowException the enable throw exception
	 * @return the user security profile
	 */
	UserSecurityProfile retrieveUserSecurityProfile(String partnerId, String userId, boolean enableThrowException);
	
	/**
	 * Update user security profile.
	 *
	 * @param partnerId the partner id
	 * @param userSecurityProfile the user security profile
	 */
	void updateUserSecurityProfile(String partnerId, UserSecurityProfile userSecurityProfile);
	
	/**
	 * Update user security profile.
	 *
	 * @param partnerId the partner id
	 * @param userSecurityProfile the user security profile
	 * @param previousHashedPassword the previous hashed password
	 * @param previousPassword the previous password
	 */
	void updateUserPassword(String partnerId, UserSecurityProfile userSecurityProfile, String previousHashedPassword, String previousPassword);
	
	/**
	 * Retrieve user by email.
	 *
	 * @param partnerId the partner id
	 * @param email the email
	 * @return the user
	 */
	User retrieveUserByEmail(String partnerId, String email);

	/**
	 * Reset password.
	 *
	 * @param userSecurityProfile the user security profile
	 */
	void resetPassword(UserSecurityProfile userSecurityProfile);

    /**
     * Creates the user with external user id.
     *
     * @param partnerId the partner id
     * @param externalUserId the external user id
     * @param providerAdditionalInformation the provider additional information
     * @return the user
     */
    User createUserWithExternalUserId(String partnerId, String externalUserId, List<AdditionalInformation> additionalInformationList);
}
