package com.placepass.userservice.domain.user;

/**
 * The Interface UserAuthenticationTokenService.
 * 
 * @author shanakak
 */
public interface UserAuthenticationTokenService {

	/**
	 * Save user authentication token.
	 *
	 * @param userAuthenticationToken the user authentication token
	 * @return the user authentication token
	 */
	UserAuthenticationToken saveUserAuthenticationToken(UserAuthenticationToken userAuthenticationToken);
	
	/**
	 * Update user authentication token.
	 *
	 * @param userAuthenticationToken the user authentication token
	 */
	void updateUserAuthenticationToken(UserAuthenticationToken userAuthenticationToken);
	
	/**
	 * Retrieve user authentication token.
	 *
	 * @param partnerId the partner id
	 * @param token the token
	 * @return the user authentication token
	 */
	UserAuthenticationToken retrieveUserAuthenticationToken(String partnerId, String token);
	
	/**
	 * Removes the user authentication token.
	 *
	 * @param token the token
	 */
	void removeUserAuthenticationToken(String token);
}
