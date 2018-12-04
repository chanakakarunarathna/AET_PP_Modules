package com.placepass.userservice.domain.user.strategy;

import java.util.List;
import java.util.Map;

import com.placepass.userservice.domain.user.AdditionalInformation;
import com.placepass.userservice.domain.user.UserAuthenticationToken;

/**
 * The Interface UserAuthenticationStrategy.
 * 
 * @author shanakak
 */
public interface UserAuthenticationStrategy {
	
	/**
	 * Authenticate.
	 *
	 * @param partnerId the partner id
	 * @param username the username
	 * @param password the password
	 * @param authTokenTimeout the auth token timeout
	 * @return the user authentication token
	 */
	UserAuthenticationToken authenticate(String partnerId, String username, String password, long authTokenTimeout);
	
	/**
	 * Verify token.
	 *
	 * @param partnerId the partner id
	 * @param token the token
	 * @return the user authentication token
	 */
	UserAuthenticationToken verifyToken(String partnerId, String token);

	/**
	 * Generate guest user authentication token.
	 *
	 * @param partnerId the partner id
	 * @param authTokenTimeout the auth token timeout
	 * @return the user authentication token
	 */
	UserAuthenticationToken generateGuestUserAuthenticationToken(String partnerId, long authTokenTimeout);
	
	/**
	 * Retrieve user authentication token.
	 *
	 * @param partnerId the partner id
	 * @param token the token
	 * @param enableThrowException the enable throw exception
	 * @return the user authentication token
	 */
	UserAuthenticationToken retrieveUserAuthenticationToken(String partnerId, String token, boolean enableThrowException);
	
	/**
	 * Update user authentication token.
	 *
	 * @param userAuthenticationToken the user authentication token
	 */
	void updateUserAuthenticationToken(UserAuthenticationToken userAuthenticationToken);
	
	/**
	 * Removes the user authentication token.
	 *
	 * @param partnerId the partner id
	 * @param token the token
	 */
	void removeUserAuthenticationToken(String partnerId, String token);

	/**
	 * Authentication by provider.
	 *
	 * @param partnerId the partner id
	 * @param externalUserId the external user id
	 * @param authTokenTimeout the auth token timeout
	 * @param additionalInformationList the additional information list
	 * @return the user authentication token
	 */
	UserAuthenticationToken authenticationByProvider(String partnerId, String externalUserId, long authTokenTimeout, List<AdditionalInformation> additionalInformationList);
}
