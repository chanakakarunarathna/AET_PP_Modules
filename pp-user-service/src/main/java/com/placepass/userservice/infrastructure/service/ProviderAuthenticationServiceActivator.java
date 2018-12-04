package com.placepass.userservice.infrastructure.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.GSRequest;
import com.gigya.socialize.GSResponse;
import com.placepass.userservice.controller.dto.AdditionalInformation;
import com.placepass.userservice.controller.dto.AuthenticationByProviderRQ;
import com.placepass.userservice.domain.user.exception.AuthenticationFailedException;
import com.placepass.userservice.domain.user.strategy.GigyaCryptographyStrategy;
import com.placepass.userservice.infrastructure.gigya.GSErrorCode;
import com.placepass.userservice.infrastructure.gigya.GigyaAccountInfo;
import com.placepass.userservice.infrastructure.gigya.GigyaIdentity;
import com.placepass.userservice.platform.common.CommonConstants;
import com.placepass.userservice.platform.common.CommonUtils;

@MessageEndpoint
public class ProviderAuthenticationServiceActivator {

    private static final String GIGYA_PARAM_SAML_DATA = "samlData";

    private static final String GIGYA_PARAM_EXTRA_PROFILE_FIELDS = "extraProfileFields";

    private static final String GIGYA_PARAM_INCLUDE_IDENTITIES_ALL = "identities-all";

    private static final String GIGYA_PARAM_INCLUDE = "include";

    @Value("${placepass.auth.provider.gigya.apikey}")
    private String apiKey;

    @Value("${placepass.auth.provider.gigya.userkey}")
    private String userKey;

    @Value("${placepass.auth.provider.gigya.usersecretkey}")
    private String userSecretKey;

    @Value("${placepass.auth.provider.gigya.exchangeuidsignature}")
    private String accountsExchangeUIDSignature;

    @Value("${placepass.auth.provider.gigya.getaccountinfo}")
    private String accountsGetAccountInfo;
    
    @Value("${placepass.auth.provider.gigya.rewards.id.length:9}")
    private int rewardsIdLength;
    
    @Autowired
    private GigyaCryptographyStrategy gigyaCryptographyStrategy;

    private static final String GIGYA_USER_SECRET = "secret";

    private static final String GIGYA_USER_KEY = "userKey";

    private static final String GIGYA_SIGNATURE_TIMESTAMP = "signatureTimestamp";

    private static final String GIGYA_UID_SIGNATURE = "UIDSignature";

    private static final String GIGYA_UID = "UID";
    
    

    /** The logger. */
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ProviderAuthenticationServiceActivator.class);

    @ServiceActivator(inputChannel = "provider.authentication.request.channel")
    public Map<String, String> authenticateByProvider(AuthenticationByProviderRQ authenticationByProviderRQ) {

        logger.info("Invoking Gigya service");
        Map<String, String> userInfoMap =  new HashMap<String, String>();
        validateUIDSignature(authenticationByProviderRQ);
        userInfoMap = retrieveAccountInformation(authenticationByProviderRQ.getExternalUserId());
        return userInfoMap;
    }

    private GSResponse validateUIDSignature(AuthenticationByProviderRQ authenticationByProviderRQ) {

        String signatureTimeStamp = getSignatureTimeStamp(authenticationByProviderRQ.getAdditionalInformation());
        Assert.hasText(signatureTimeStamp, "The signatureTimestamp is required as additional information.");

        GSObject clientParams = new GSObject();
        clientParams.put(GIGYA_UID, authenticationByProviderRQ.getExternalUserId());
        clientParams.put(GIGYA_UID_SIGNATURE, authenticationByProviderRQ.getProviderToken());
        clientParams.put(GIGYA_SIGNATURE_TIMESTAMP, signatureTimeStamp);
        clientParams.put(GIGYA_USER_KEY, userKey);
        clientParams.put(GIGYA_USER_SECRET, userSecretKey);

        GSRequest request = new GSRequest(apiKey, userSecretKey, accountsExchangeUIDSignature, clientParams, true,
                userKey);

        GSResponse response = request.send();

        // TODO : Refactor error handling
        if (GSErrorCode.SUCCESS.getErrorCode() == response.getErrorCode()) {
            logger.info("Authentication success on accounts.exchangeUIDSignature service ");
        } else if (GSErrorCode.REQUEST_HAS_EXPIRED.getErrorCode() == response.getErrorCode()) {
            logger.info("Authentication failed on accounts.exchangeUIDSignature : " + response.getErrorMessage());
            throw new AuthenticationFailedException(
                    "Authentication failed, " + GSErrorCode.REQUEST_HAS_EXPIRED.getMessage());
        } else if (GSErrorCode.INVALID_PARAMETER_VALUE.getErrorCode() == response.getErrorCode()) {
            logger.info("Authentication failed on accounts.exchangeUIDSignature : " + response.getErrorMessage());
            throw new AuthenticationFailedException(
                    "Authentication failed, " + GSErrorCode.INVALID_PARAMETER_VALUE.getMessage());
        } else if (GSErrorCode.GENERAL_SERVER_ERROR.getErrorCode() == response.getErrorCode()) {
            logger.info("Authentication failed on accounts.exchangeUIDSignature : " + response.getErrorMessage());
            throw new AuthenticationFailedException(
                    "Authentication failed, " + GSErrorCode.GENERAL_SERVER_ERROR.getMessage());
        } else {
            logger.info("Authentication failed on accounts.exchangeUIDSignature : " + response.getErrorMessage());
            throw new AuthenticationFailedException("Authentication failed");
        }

        return response;
    }

    
    private Map<String, String> retrieveAccountInformation(String externalUserId) {

        GSObject clientParams = new GSObject();
        clientParams.put(GIGYA_UID, externalUserId);
        clientParams.put(GIGYA_USER_KEY, userKey);
        clientParams.put(GIGYA_USER_SECRET, userSecretKey);
        clientParams.put(GIGYA_PARAM_INCLUDE, GIGYA_PARAM_INCLUDE_IDENTITIES_ALL);
        clientParams.put(GIGYA_PARAM_EXTRA_PROFILE_FIELDS, GIGYA_PARAM_SAML_DATA);

        GSRequest request = new GSRequest(apiKey, userSecretKey, accountsGetAccountInfo, clientParams, true, userKey);

        GSResponse response = request.send();

        if (GSErrorCode.SUCCESS.getErrorCode() == response.getErrorCode()) {
            logger.info("Get Account Info success on accounts.getAccountInfo service ");
        } else {
            logger.info("Get Account Info failed on accounts.getAccountInfo : " + response.getLog());
            throw new AuthenticationFailedException("Authentication Failed");
        }

        GigyaAccountInfo gigyaAccountInfo = CommonUtils.convertToObject(GigyaAccountInfo.class,
                response.getResponseText());

        Map<String, String> userInfo = new HashMap<String, String>();

        if (gigyaAccountInfo != null && gigyaAccountInfo.getGigyaIdentities() != null
                && !gigyaAccountInfo.getGigyaIdentities().isEmpty()) {
            for (GigyaIdentity gigyaIdentity : gigyaAccountInfo.getGigyaIdentities()) {
                if (gigyaIdentity.getGigyaSAMLData() != null
                        && StringUtils.hasText(gigyaIdentity.getGigyaSAMLData().getRewardsID())) {
                    String rewardsID = gigyaIdentity.getGigyaSAMLData().getRewardsID();
                    if (rewardsID.length() == rewardsIdLength) {
                        rewardsID = gigyaCryptographyStrategy.encrypt(gigyaIdentity.getGigyaSAMLData().getRewardsID());
                    }
                    userInfo.put(CommonConstants.REWARDS_ID, rewardsID);
                    logger.info("RewardsId available for this user.");
                    break;
                }
            }
        }

        return userInfo;
    }

    private String getSignatureTimeStamp(List<AdditionalInformation> additionalInformationList) {

        for (AdditionalInformation additionalInformation : additionalInformationList) {
            if (GIGYA_SIGNATURE_TIMESTAMP.equals(additionalInformation.getKey())) {
                return additionalInformation.getValue();
            }
        }

        return null;
    }

}
