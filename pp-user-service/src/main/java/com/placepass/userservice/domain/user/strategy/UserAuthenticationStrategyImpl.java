package com.placepass.userservice.domain.user.strategy;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.placepass.userservice.domain.partner.strategy.PartnerSpecificationRetrievalStrategy;
import com.placepass.userservice.domain.user.AdditionalInformation;
import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.UserAuthenticationToken;
import com.placepass.userservice.domain.user.UserAuthenticationTokenService;
import com.placepass.userservice.domain.user.UserManagementService;
import com.placepass.userservice.domain.user.UserSecurityProfile;
import com.placepass.userservice.domain.user.UserStatus;
import com.placepass.userservice.domain.user.UserType;
import com.placepass.userservice.domain.user.exception.AuthenticationFailedException;
import com.placepass.userservice.domain.user.exception.AccessDeniedException;
import com.placepass.userservice.platform.common.CommonConstants;

@Service
public class UserAuthenticationStrategyImpl implements UserAuthenticationStrategy {

	@Autowired
	private CryptographyStrategy cryptographyStrategy;

	@Autowired
	private UserPersistanceAndRetrievalStrategy userPersistanceAndRetrievalStrategy;

	@Autowired
	private AuthTokenGenerationStrategy authTokenGenerationStrategy;

	@Autowired
	private UserAuthenticationTokenService userAuthenticationTokenService;
	
	@Autowired
	private PartnerSpecificationRetrievalStrategy partnerVerificationStrategy;
	
	@Override
	public UserAuthenticationToken authenticate(String partnerId, String username, String password, long authTokenTimeout) {

		User user = userPersistanceAndRetrievalStrategy.retrieveUserByEmail(partnerId, username);

		if (!user.isActive()) {
			throw new AuthenticationFailedException("User is not active.");
		}

		boolean verificationEnabled = partnerVerificationStrategy.checkVerificationEnabled(partnerId);
		
		if (verificationEnabled && !UserStatus.VERIFIED.equals(user.getUserStatus())) {
			throw new AuthenticationFailedException("User is has not been verified.");
		}

		UserSecurityProfile securityProfile = userPersistanceAndRetrievalStrategy.retrieveUserSecurityProfile(partnerId,
				user.getId(), false);

		if (securityProfile == null || !cryptographyStrategy.verifyHash(password, securityProfile.getPassword())) {
			throw new AuthenticationFailedException("Invalid username or password");
		}

		String token = authTokenGenerationStrategy.generateToken();
		
		String firstName = null;
		String lastName = null;
		
		if (user.getUserProfile() != null) {
			firstName = user.getUserProfile().getFirstName();
			lastName = user.getUserProfile().getLastName();
		}
		
		UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(token, partnerId, user.getId(), UserType.ENROLLED, authTokenTimeout, username, firstName, lastName, securityProfile.getUserPermissions());
		userAuthenticationTokenService.saveUserAuthenticationToken(userAuthenticationToken);

		securityProfile.setLastLoginDate(new Date());
		userPersistanceAndRetrievalStrategy.updateUserSecurityProfile(partnerId, securityProfile);

		return userAuthenticationToken;
	}

	@Override
	public UserAuthenticationToken verifyToken(String partnerId, String token) {
	    
	    if(!StringUtils.hasText(token)) {
            throw new AccessDeniedException("User Authentication Token not found.");
        }
	    
		UserAuthenticationToken userAuthenticationToken = retrieveUserAuthenticationToken(partnerId, token, true);

		Assert.isTrue(partnerId.equals(userAuthenticationToken.getPartnerId()), "User Authentication Token not found.");

		return userAuthenticationToken;

	}

	@Override
	public UserAuthenticationToken generateGuestUserAuthenticationToken(String partnerId, long authTokenTimeout) {
		
		String token = authTokenGenerationStrategy.generateToken();
		String guestUserId = CommonConstants.GUEST_USER_PREFIX + UUID.randomUUID().toString().replaceAll(CommonConstants.DASH, CommonConstants.EMPTY);
		
		UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(token, partnerId, guestUserId, UserType.GUEST, authTokenTimeout);
		userAuthenticationTokenService.saveUserAuthenticationToken(userAuthenticationToken);
		return userAuthenticationToken;
	}
	
	@Override
    public UserAuthenticationToken retrieveUserAuthenticationToken(String partnerId, String token,
            boolean enableThrowException) {

        if (enableThrowException && !StringUtils.hasText(token)) {
            throw new AccessDeniedException("User Authentication Token not found.");
        }

        UserAuthenticationToken userAuthenticationToken = userAuthenticationTokenService
                .retrieveUserAuthenticationToken(partnerId, token);

        if (enableThrowException) {
            if (userAuthenticationToken == null) {
                throw new AccessDeniedException("User Authentication Token not found.");
            } else if (!partnerId.equals(userAuthenticationToken.getPartnerId())) {
                throw new AccessDeniedException("User Authentication Token not found.");
            }
        }

        return userAuthenticationToken;
    }
	
	@Override
	public void updateUserAuthenticationToken(UserAuthenticationToken userAuthenticationToken) {
		userAuthenticationTokenService.updateUserAuthenticationToken(userAuthenticationToken);
	}
	
	@Override
	public void removeUserAuthenticationToken(String partnerId, String token) {
		retrieveUserAuthenticationToken(partnerId, token, true);
		userAuthenticationTokenService.removeUserAuthenticationToken(token);
	}

	@Override
	public UserAuthenticationToken authenticationByProvider(String partnerId, String externalUserId, long authTokenTimeout, List<AdditionalInformation> additionalInformationList) {
		User user = userPersistanceAndRetrievalStrategy.createUserWithExternalUserId(partnerId, externalUserId, additionalInformationList);
		String token = authTokenGenerationStrategy.generateToken();
		
		UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(token, partnerId, user.getId(), user.getUserType(), authTokenTimeout, null);
		userAuthenticationTokenService.saveUserAuthenticationToken(userAuthenticationToken);
		
		return userAuthenticationToken;
	}
	
	

}
