package com.placepass.userservice.domain.user.strategy;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.VerificationChannelType;
import com.placepass.userservice.domain.user.VerificationCode;
import com.placepass.userservice.domain.user.VerificationCodeGenerationType;
import com.placepass.userservice.domain.user.VerificationService;
import com.placepass.userservice.domain.user.VerificationStatus;
import com.placepass.userservice.domain.user.VerificationType;
import com.placepass.userservice.domain.user.exception.VerificationCodeExpiredException;
import com.placepass.userservice.infrastructure.service.EventPublisherGatewayService;
import com.placepass.userservice.platform.common.CommonConstants;

/**
 * The Class CredentialRecoveryStrategyImpl.
 */
@Service
public class CredentialRecoveryStrategyImpl implements CredentialRecoveryStrategy {
	
	/** The logger. */
	private org.slf4j.Logger logger = LoggerFactory.getLogger(CredentialRecoveryStrategyImpl.class);
	
	/** The verification service. */
	@Autowired
	private VerificationService verificationService;
	
	/** The verification strategy. */
	@Autowired
	private VerificationStrategy verificationStrategy;
	
	/** The event management service. */
	@Autowired
	private EventPublisherGatewayService eventPublisherGatewayService;
	
	/** The verification code generation strategy. */
	@Autowired
	private VerificationCodeGenerationStrategyImpl verificationCodeGenerationStrategy;
	
	/** The email notification enabled. */
	@Value("${placepass.user.email.notification.enabled}")
	private boolean emailNotificationEnabled;
	
	/** The forgot password url. */
	@Value("${placepass.forgot.password.url}")
	private String forgotPasswordUrl;	
	
	/** The forgot password verification code expire time. */
	@Value("${placepass.forgot.password.verification.code.expire.time}")
	private int forgotPasswordVerificationCodeExpireTime;
	
	/** The verification code generation type. */
	@Value("${placepass.user.verification.code.generation.type:UUID_GENERATION}")
	private String verificationCodeGenerationType;
	
	/** The verification channel type. */
	@Value("${placepass.user.verification.channel.type:EMAIL}")
	private String verificationChannelType;
	
	@Value("${placepass.user.forgot.password.event.name}")
	private String userForgotPasswordEventName;
	
	@Override
	public VerificationCode createForgotPasswordVerificationCode(String partnerId, User user) {
		
		String code = verificationCodeGenerationStrategy.generateVerificationCode(VerificationCodeGenerationType.valueOf(verificationCodeGenerationType));
		VerificationCode verificationCode = new VerificationCode(user.getId(), partnerId, code,
				VerificationType.FORGOT_PASSWORD, VerificationChannelType.valueOf(verificationChannelType));
		verificationCode = verificationService.createVerificationCode(verificationCode);

		String forgotPasswordVerificationUrl = forgotPasswordUrl + verificationCode.getCode();

		if (emailNotificationEnabled) {
			Map<String, String> forgotPasswordNotificationEventParams = constructForgotPasswordEventDetails(partnerId,
					user, forgotPasswordVerificationUrl, userForgotPasswordEventName);

			eventPublisherGatewayService.publishEvent(forgotPasswordNotificationEventParams);
		}

		logger.debug("Forgot password url : " + forgotPasswordVerificationUrl);

		return verificationCode;

	}
	
	@Override
	public VerificationCode validateForgotPasswordVerificationCode(String partnerId, String code) {
		VerificationCode verificationCode = verificationService.retrieveVerificationCode(partnerId, code, VerificationType.FORGOT_PASSWORD);
		
		Assert.notNull(verificationCode, "Invalid forgot password verification code.");
		Assert.isTrue(!VerificationStatus.EXPIRED.equals(verificationCode.getVerificationStatus()), "Forgot password verification code is expired.");
		
		validateForgotPasswordVerificationCodeExpiry(verificationCode);
		
		return verificationCode;
	}
	
	
	/**
	 * Validate forgot password verification code expiry.
	 *
	 * @param verificationCode the verification code
	 */
	private void validateForgotPasswordVerificationCodeExpiry(VerificationCode verificationCode) {
		Calendar expireTime = Calendar.getInstance();
		expireTime.setTime(verificationCode.getCreatedDate());
		expireTime.add(Calendar.MINUTE, forgotPasswordVerificationCodeExpireTime);
		Date expiryDate = expireTime.getTime();
		Date currentDate = new Date();

		if (currentDate.after(expiryDate)) {
			verificationStrategy.expireVerificationCode(verificationCode);
			throw new VerificationCodeExpiredException("Forgot password verification code is expired.");
		}

	}

	/**
	 * Construct forgot password event details.
	 *
	 * @param partnerId the partner id
	 * @param user the user
	 * @param forgotPasswordUrl the forgot password url
	 * @param eventName the event name
	 * @return the map
	 */
	private Map<String, String> constructForgotPasswordEventDetails(String partnerId, User user,
			String forgotPasswordUrl, String eventName) {
		Map<String, String> eventMap = new HashMap<String, String>();
		eventMap.put(CommonConstants.PARTNER_ID, partnerId);
		eventMap.put(CommonConstants.USER_ID, user.getId());
		eventMap.put(CommonConstants.EMAIL, user.getEmail());
		eventMap.put(CommonConstants.FIRST_NAME, user.getUserProfile().getFirstName());
		eventMap.put(CommonConstants.LAST_NAME, user.getUserProfile().getLastName());
		eventMap.put(CommonConstants.VERIFICATION_URL, forgotPasswordUrl);
		eventMap.put(CommonConstants.PLATFORM_EVENT_NAME, eventName);

		return eventMap;
	}
}
