package com.placepass.userservice.domain.user.strategy;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.placepass.userservice.domain.partner.strategy.PartnerSpecificationRetrievalStrategy;
import com.placepass.userservice.domain.user.AdditionalInformation;
import com.placepass.userservice.domain.user.ArchivedPassword;
import com.placepass.userservice.domain.user.ArchivedPasswordService;
import com.placepass.userservice.domain.user.User;
import com.placepass.userservice.domain.user.UserManagementService;
import com.placepass.userservice.domain.user.UserSecurityProfile;
import com.placepass.userservice.domain.user.UserSecurityProfileService;
import com.placepass.userservice.domain.user.UserType;
import com.placepass.userservice.platform.common.CommonConstants;

/**
 * The Class UserPersistanceAndRetrievalStrategyImpl.
 * 
 * @author shanakak
 */
@Service
public class UserPersistanceAndRetrievalStrategyImpl implements UserPersistanceAndRetrievalStrategy {

	/** The logger. */
	private org.slf4j.Logger logger = LoggerFactory.getLogger(UserPersistanceAndRetrievalStrategyImpl.class);
	
	/** The password generation strategy. */
	@Autowired
	private PasswordGenerationStrategy passwordGenerationStrategy;
	
	/** The cryptography strategy. */
	@Autowired
	private CryptographyStrategy cryptographyStrategy;

	/** The user validation strategy. */
	@Autowired
	private UserValidationStrategy userValidationStrategy;

	/** The user management service. */
	@Autowired
	private UserManagementService userManagementService;
	
	/** The user security profile service. */
	@Autowired
	private UserSecurityProfileService userSecurityProfileService;
	
	@Autowired
	private ArchivedPasswordService archivedPasswordService;
	
	/** The verification strategy. */
	@Autowired
	private VerificationStrategy verificationStrategy;
	
	/** The partner specification retrieval strategy. */
	@Autowired
	private PartnerSpecificationRetrievalStrategy partnerSpecificationRetrievalStrategy;
	
	@Value("${placepass.previous.password.checks.count:3}")
	private int previousPasswordCount;
	
	@Value("${placepass.password.history.checks.allowed:true}")
	private boolean passwordHistoryChecksAllowed;
	
	@Value("${placepass.user.created.event.name}")
	private String userCreatedEventName;
	
	@Value("${placepass.user.email.updated.event.name}")
	private String userEmailUpdatedEventName;

	@Override
	public User createUser(User user, UserSecurityProfile userSecurityProfile) {
		userValidationStrategy.validateEmail(user.getEmail(), user.getPartnerId());
		String password = userSecurityProfile.getPassword();

		if (user.isGuestUser()) {
			password = passwordGenerationStrategy.generatePassword();
			Assert.hasText(password, "Error while creating user.");
			userSecurityProfile.setSystemGeneratedPassword(true);
		}

		String hashedPassword = cryptographyStrategy.hash(password);
		userSecurityProfile.setPassword(hashedPassword);

		boolean verificationEnabled = partnerSpecificationRetrievalStrategy.checkVerificationEnabled(user.getPartnerId());

		if (!verificationEnabled) {
			user.markAsVerified();
		}

		user = userManagementService.createUser(user);
		userSecurityProfile.setUserId(user.getId());
		userSecurityProfileService.createUserSecurityProfile(userSecurityProfile);

		ArchivedPassword passwordHistory = new ArchivedPassword(user.getPartnerId(), user.getId(), hashedPassword);
		archivedPasswordService.createArchivedPassword(passwordHistory);

		if (verificationEnabled) {
			verificationStrategy.createVerificationCode(user.getPartnerId(), user.getUserType(), user, userCreatedEventName);
		}

		return user;
	}
	
	@Override
	public User retrieveUser(String partnerId, String userId, boolean enableThrowException) {
		User existingUser = userManagementService.retrieveUser(partnerId, userId);
				
		if (enableThrowException && existingUser == null) {
			throw new IllegalArgumentException("User does not exist.");
		}
		
		return existingUser;
	}
	
	@Override
	public void updateUser(String partnerId, String previousEmail, User user) {

        boolean emailChanged = false;

        if (StringUtils.hasText(user.getEmail())) {
            User userWithEmail = userManagementService.retrieveUserByEmail(partnerId, user.getEmail());
            if (userWithEmail != null) {
                Assert.isTrue(user.getId().equalsIgnoreCase(userWithEmail.getId()), "Email already in use.");
            }

            if (StringUtils.hasText(previousEmail)) {
                emailChanged = !previousEmail.equalsIgnoreCase(user.getEmail());
            } else {
                emailChanged = true;
            }
        }

        boolean verificationEnabled = partnerSpecificationRetrievalStrategy
                .checkVerificationEnabled(user.getPartnerId());

        if (verificationEnabled && emailChanged) {
			user.markAsPendingVerification();
			logger.info("Email change occured for user id : " + user.getId());
		}

		userManagementService.updateUser(user);

		if (verificationEnabled && emailChanged) {
			verificationStrategy.createVerificationCode(partnerId, user.getUserType(), user, userEmailUpdatedEventName);
		}
	}
	
	@Override
	public UserSecurityProfile retrieveUserSecurityProfile(String partnerId, String userId,
			boolean enableThrowException) {
		UserSecurityProfile existingUserSecurityProfile = userSecurityProfileService
				.retrieveUserSecurityProfile(partnerId, userId);

		if (enableThrowException && existingUserSecurityProfile == null) {

			logger.info("User security profile does not exist for userId : " + userId);
			throw new IllegalArgumentException("User does not exist.");
		}

		return existingUserSecurityProfile;
	}

	@Override
	public void updateUserSecurityProfile(String partnerId, UserSecurityProfile userSecurityProfile) {
				
		userSecurityProfileService.updateUserSecurityProfile(userSecurityProfile);
		
	}
	
	@Override
	public void updateUserPassword(String partnerId, UserSecurityProfile userSecurityProfile, String previousHashedPassword, String previousPassword) {
		
		boolean matchesPreviousPassword = cryptographyStrategy.verifyHash(previousPassword, previousHashedPassword);
		Assert.isTrue(matchesPreviousPassword, "Previous password is not valid.");
		
		boolean matchesNewPassword = cryptographyStrategy.verifyHash(userSecurityProfile.getPassword(), previousHashedPassword);
		Assert.isTrue(!matchesNewPassword, "New password should be different to the previous password.");
		
		validateArchivedPassword(partnerId, userSecurityProfile);

		String hashedPassword = cryptographyStrategy.hash(userSecurityProfile.getPassword());
		userSecurityProfile.setPassword(hashedPassword);
		userSecurityProfile.setSystemGeneratedPassword(false);
		
		userSecurityProfileService.updateUserSecurityProfile(userSecurityProfile);
		ArchivedPassword archivedPassword = new ArchivedPassword(partnerId, userSecurityProfile.getUserId(), previousHashedPassword);
		archivedPasswordService.createArchivedPassword(archivedPassword);
		
	}
	
	@Override
	public User retrieveUserByEmail(String partnerId, String email) {
		User user = userManagementService.retrieveUserByEmail(partnerId, email);
		Assert.notNull(user, "User does not exist.");
		return user;
	}

	@Override
	public void resetPassword(UserSecurityProfile userSecurityProfile) {
		validateArchivedPassword(userSecurityProfile.getPartnerId(), userSecurityProfile);
		
		String hashedPassword = cryptographyStrategy.hash(userSecurityProfile.getPassword());
		userSecurityProfile.setPassword(hashedPassword);
		//TODO : Should we disallow for guest
		userSecurityProfile.setSystemGeneratedPassword(true);
		
		userSecurityProfileService.updateUserSecurityProfile(userSecurityProfile);
	}
	
	private void validateArchivedPassword(String partnerId, UserSecurityProfile userSecurityProfile) {
		if (passwordHistoryChecksAllowed) {
			List<ArchivedPassword> archivedPasswords = archivedPasswordService.retrieveArchivedPassword(partnerId,
					userSecurityProfile.getUserId(), new PageRequest(0, previousPasswordCount, Direction.DESC, CommonConstants.CREATED_DATE));

			if (archivedPasswords != null && !archivedPasswords.isEmpty()) {
				for (ArchivedPassword archivedPassword : archivedPasswords) {
					boolean matchesPasswordHistory = cryptographyStrategy.verifyHash(userSecurityProfile.getPassword(),
							archivedPassword.getPassword());
					Assert.isTrue(!matchesPasswordHistory,
							"New password should be different to the previous passwords.");
				}
			}
		}
	}

	@Override
	public User createUserWithExternalUserId(String partnerId, String externalUserId, List<AdditionalInformation> additionalInformationList) { 
		User user = userManagementService.retrieveUserByExternalUserId(partnerId, externalUserId);

		if (user == null) {
			user = new User(partnerId, externalUserId, UserType.EXTERNAL_GIGYA, additionalInformationList);
			user = userManagementService.createUser(user);
		} else {
            if (UserType.EXTERNAL_GIGYA.equals(user.getUserType())) {
                boolean hasRewardsId = false;

                for (AdditionalInformation additionalInformation : user.getAdditionalInformation()) {
                    if (CommonConstants.REWARDS_ID.equals(additionalInformation.getKey())) {
                        hasRewardsId = true;
                        break;
                    }
                }

                if (!hasRewardsId) {
                    user.getAdditionalInformation().addAll(additionalInformationList);
                    userManagementService.updateUser(user);
                }

            }
        }

		return user;
	}

}
