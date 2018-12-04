package com.placepass.userservice.domain.user;

/**
 * The Interface UserSecurityProfileService.
 * 
 * @author shanakak
 */
public interface UserSecurityProfileService {

	
	/**
	 * Creates the user security profile.
	 *
	 * @param userSecurityProfile the user security profile
	 * @return the user security profile
	 */
	UserSecurityProfile createUserSecurityProfile(UserSecurityProfile userSecurityProfile);

	
	/**
	 * Update user security profile.
	 *
	 * @param user the user
	 */
	void updateUserSecurityProfile(UserSecurityProfile user);


	/**
	 * Retrieve user security profile.
	 *
	 * @param partnerId the partner id
	 * @param userId the user id
	 * @return the user security profile
	 */
	UserSecurityProfile retrieveUserSecurityProfile(String partnerId, String userId);
	
}
