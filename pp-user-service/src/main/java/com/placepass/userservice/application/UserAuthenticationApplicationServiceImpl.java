package com.placepass.userservice.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.placepass.userservice.controller.dto.AuthenticationByProviderRQ;
import com.placepass.userservice.controller.dto.AuthenticationByProviderRS;
import com.placepass.userservice.controller.dto.AuthenticationRQ;
import com.placepass.userservice.controller.dto.AuthenticationRS;
import com.placepass.userservice.controller.dto.DomainToDtoTransformer;
import com.placepass.userservice.controller.dto.Provider;
import com.placepass.userservice.controller.dto.TokenVerificationRS;
import com.placepass.userservice.domain.partner.strategy.PartnerSpecificationRetrievalStrategy;
import com.placepass.userservice.domain.user.AdditionalInformation;
import com.placepass.userservice.domain.user.UserAuthenticationToken;
import com.placepass.userservice.domain.user.strategy.UserAuthenticationStrategy;
import com.placepass.userservice.infrastructure.service.ProviderAuthenticationGatewayService;
import com.placepass.userservice.platform.common.CommonConstants;
import com.placepass.userservice.platform.common.LoggingEvent;
import com.placepass.userservice.platform.common.LoggingUtil;

@Service
public class UserAuthenticationApplicationServiceImpl implements UserAuthenticationApplicationService {

    /** The logger. */
    private org.slf4j.Logger logger = LoggerFactory.getLogger(UserAuthenticationApplicationServiceImpl.class);
    
    /** The user authentication strategy. */
	@Autowired
	private UserAuthenticationStrategy userAuthenticationStrategy;
	
	/** The user request validator. */
	@Autowired
	private UserRequestValidator userRequestValidator;
	
	/** The partner specification retrieval strategy. */
	@Autowired
    private PartnerSpecificationRetrievalStrategy partnerSpecificationRetrievalStrategy;
	
	@Autowired
    private ProviderAuthenticationGatewayService providerAuthenticationGatewayService;
	
    @Override
    public AuthenticationRS authenticate(AuthenticationRQ authenticationRQ, String partnerId) {

        userRequestValidator.validatePartnerId(partnerId);
        Assert.hasText(authenticationRQ.getUsername(), "Username is required.");
        Assert.hasText(authenticationRQ.getPassword(), "Password is required.");

        long authTokenTimeout = partnerSpecificationRetrievalStrategy.retrieveUserAuthenticationTokenTimeout(partnerId);

        UserAuthenticationToken userAuthenticationToken = userAuthenticationStrategy.authenticate(partnerId,
                authenticationRQ.getUsername(), authenticationRQ.getPassword(), authTokenTimeout);

        AuthenticationRS authenticationRS = new AuthenticationRS(userAuthenticationToken.getId(), authTokenTimeout);
        return authenticationRS;
    }

	@Override
	public TokenVerificationRS verifyToken(String token, String partnerId) {
		
		userRequestValidator.validatePartnerId(partnerId);
		
		UserAuthenticationToken userAuthenticationToken = userAuthenticationStrategy.verifyToken(partnerId, token);
		TokenVerificationRS tokenVerificationRS = DomainToDtoTransformer.transformUserToTokenVerificationRS(userAuthenticationToken);
		
		Map<String, String> logMap = new HashMap<String, String>();
        logMap.put(CommonConstants.PARTNER_ID, partnerId);
        logMap.put(CommonConstants.USER_ID, userAuthenticationToken.getUserId());
        
        LoggingUtil.logInfo(logger, "Verify token success.", logMap, LoggingEvent.VERIFY_AUTHENTICATION_TOKEN);
		
		return tokenVerificationRS;
	}

	@Override
	public AuthenticationRS generateGuestUserAuthenticationToken(String partnerId) {
		
		userRequestValidator.validatePartnerId(partnerId);
		long authTokenTimeout = partnerSpecificationRetrievalStrategy.retrieveUserAuthenticationTokenTimeout(partnerId);
		
		UserAuthenticationToken userAuthenticationToken = userAuthenticationStrategy.generateGuestUserAuthenticationToken(partnerId, authTokenTimeout);
		
		AuthenticationRS authenticationRS = new AuthenticationRS(userAuthenticationToken.getId(), authTokenTimeout);
		return authenticationRS;
	}
	
	@Override
	public void removeUserAuthenticationToken(String token, String partnerId) {
		
		userRequestValidator.validatePartnerId(partnerId);
		userAuthenticationStrategy.removeUserAuthenticationToken(partnerId, token);
		
	}

    @Override
    public AuthenticationByProviderRS authenticationByProvider(String partnerId,
            AuthenticationByProviderRQ authenticationByProviderRQ) {
        userRequestValidator.validatePartnerId(partnerId);
        Assert.notNull(authenticationByProviderRQ, "Authentication by provider is required.");
        Assert.hasText(authenticationByProviderRQ.getProvider(), "Provider is required.");
        Assert.isTrue(Provider.contains(authenticationByProviderRQ.getProvider()), "Invalid Provider specified.");
        Assert.hasText(authenticationByProviderRQ.getExternalUserId(), "External user id is required.");
        Assert.hasText(authenticationByProviderRQ.getProviderToken(), "Provider token is required.");

        Map<String, String> providerAdditionalInformation = providerAuthenticationGatewayService.authenticateByProvider(authenticationByProviderRQ);
        List<AdditionalInformation> additionalInformationList = new ArrayList<AdditionalInformation>();
        
        for(String key : providerAdditionalInformation.keySet()){
            AdditionalInformation additionalInformation = new AdditionalInformation(key, providerAdditionalInformation.get(key));
            additionalInformationList.add(additionalInformation);
        }
        
        long authTokenTimeout = partnerSpecificationRetrievalStrategy.retrieveUserAuthenticationTokenTimeout(partnerId);

        UserAuthenticationToken userAuthenticationToken = userAuthenticationStrategy.authenticationByProvider(partnerId,
                authenticationByProviderRQ.getExternalUserId(), authTokenTimeout, additionalInformationList);

        AuthenticationByProviderRS authenticationByProviderRS = new AuthenticationByProviderRS(
                userAuthenticationToken.getId(), userAuthenticationToken.getUserId(), authTokenTimeout);

        Map<String, String> logMap = new HashMap<String, String>();
        logMap.put(CommonConstants.PARTNER_ID, partnerId);
        logMap.put(CommonConstants.USER_ID, userAuthenticationToken.getUserId());
        logMap.put(CommonConstants.TIMEOUT_VALUE, String.valueOf(authTokenTimeout));
        
        LoggingUtil.logInfo(logger, "Authenticate with provider success.", logMap, LoggingEvent.AUTHENTICATE_WITH_PROVIDER);
        
        return authenticationByProviderRS;
    }

}
