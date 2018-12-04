package com.placepass.userservice.domain.user.strategy;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.UserManagementService;
import com.placepass.userservice.domain.user.UserType;
import com.placepass.userservice.domain.user.VerificationChannelType;
import com.placepass.userservice.domain.user.VerificationCode;
import com.placepass.userservice.domain.user.VerificationCodeGenerationType;
import com.placepass.userservice.domain.user.VerificationService;
import com.placepass.userservice.domain.user.VerificationStatus;
import com.placepass.userservice.domain.user.VerificationType;
import com.placepass.userservice.infrastructure.service.EventPublisherGatewayService;
import com.placepass.userservice.platform.common.CommonConstants;

/**
 * The Class VerificationStrategyImpl.
 * 
 * @author shanakak
 */
@Service
public class VerificationStrategyImpl implements VerificationStrategy {

	/** The logger. */
	private org.slf4j.Logger logger = LoggerFactory.getLogger(VerificationStrategyImpl.class);
	
	/** The user management service. */
	@Autowired
	private UserManagementService userManagementService;
		
	/** The verification service. */
	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private VerificationCodeGenerationStrategyImpl verificationCodeGenerationStrategy;
	
	/** The event management service. */
	@Autowired
	private EventPublisherGatewayService eventPublisherGatewayService;
	
	/** The email notification enabled. */
	@Value("${placepass.user.email.notification.enabled}")
	private boolean emailNotificationEnabled;
	
	/** The email verification url. */
	@Value("${placepass.user.email.verification.url}")
	private String emailVerificationUrl;
	
	/** The verification code generation type. */
	@Value("${placepass.user.verification.code.generation.type:UUID_GENERATION}")
	private String verificationCodeGenerationType;
	
	/** The verification channel type. */
	@Value("${placepass.user.verification.channel.type:EMAIL}")
	private String verificationChannelType;
		
	@Override
	public VerificationCode createVerificationCode(String partnerId, UserType userType, User user, String eventName) {

		String code = verificationCodeGenerationStrategy.generateVerificationCode(VerificationCodeGenerationType.valueOf(verificationCodeGenerationType));
		
		VerificationCode verificationCode = new VerificationCode(user.getId(), partnerId, code,
				VerificationType.VERIFICATION, VerificationChannelType.valueOf(verificationChannelType));
		verificationCode = verificationService.createVerificationCode(verificationCode);

		String verificationUrl = emailVerificationUrl + verificationCode.getCode();
		
		if (emailNotificationEnabled) {
			Map<String, String> notificationEventParams = constructVerificationEventDetails(partnerId, user, userType,
					verificationUrl, eventName);

			eventPublisherGatewayService.publishEvent(notificationEventParams);
		}

		logger.debug("Email verification url : " + verificationUrl);
		
		return verificationCode;
	}
		
	@Override
	public void verifyCode(String partnerId, String code) {
		
		VerificationCode verificationCode = verificationService.retrieveVerificationCode(partnerId, code, VerificationType.VERIFICATION);
		
		Assert.notNull(verificationCode, "Invalid verification code");
		Assert.isTrue(!VerificationStatus.EXPIRED.equals(verificationCode.getVerificationStatus()), "The verification code has expired");
		
		User user = userManagementService.retrieveUser(verificationCode.getPartnerId(), verificationCode.getUserId());
		
		Assert.notNull(user, "User not found");
		user.markAsVerified();
		userManagementService.updateUser(user);
		
		expireVerificationCode(verificationCode);
	}

	public void expireVerificationCode(VerificationCode verificationCode) {
		verificationCode.markAsExpired();
		verificationService.updateVerificationCode(verificationCode);
	}
	
	/**
	 * Construct verification event details.
	 *
	 * @param partnerId the partner id
	 * @param user the user
	 * @param userType the user type
	 * @param verificationUrl the verification url
	 * @param eventName the event name
	 * @return the map
	 */
	private Map<String, String> constructVerificationEventDetails(String partnerId, User user, UserType userType,
			String verificationUrl, String eventName) {
		Map<String, String> eventMap = new HashMap<String, String>();
		eventMap.put(CommonConstants.PARTNER_ID, partnerId);
		eventMap.put(CommonConstants.USER_ID, user.getId());
		eventMap.put(CommonConstants.EMAIL, user.getEmail());
		eventMap.put(CommonConstants.FIRST_NAME, user.getUserProfile().getFirstName());
		eventMap.put(CommonConstants.LAST_NAME, user.getUserProfile().getLastName());
		eventMap.put(CommonConstants.VERIFICATION_URL, verificationUrl);
		eventMap.put(CommonConstants.PLATFORM_EVENT_NAME, eventName);

		return eventMap;
	}
	
}
