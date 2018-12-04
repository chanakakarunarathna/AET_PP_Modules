package com.placepass.userservice.domain.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class User.
 */
@Document(collection = "user")
public class User {

	/** The id. */
	@Id
	private String id;

	/** The partner id. */
	private String partnerId;

	/** The email. */
	@Indexed
	private String email;

	/** The user profile. */
	private UserProfile userProfile;

	/** The created date. */
	private Date createdDate;

	/** The modified date. */
	private Date modifiedDate;

	/** The active. */
	private boolean active;

	/** The deleted. */
	private boolean deleted;

	/** The user status. */
	private UserStatus userStatus;

	/** The user type. */
	private UserType userType;

		/** The user subsciption. */
	private UserSubsciption userSubsciption;

	/** The Additional information. */
	private List<AdditionalInformation> additionalInformation = new ArrayList<AdditionalInformation>();
	
	/** The external user id. */
	@Indexed
	private String externalUserId;
	
	/** The version. */
	private String version = "1.0.0";

	
	/**
	 * Instantiates a new user.
	 */
	public User() {

	}
		
	/**
	 * Instantiates a new user.
	 *
	 * @param partnerId the partner id
	 * @param email the email
	 * @param userType the user type
	 * @param userProfile the user profile
	 * @param userSubsciption the user subsciption
	 * @param additionalInformation the additional information
	 * @param externalUserId the external user id
	 */
	public User(String partnerId, String email, UserType userType, UserProfile userProfile,
			UserSubsciption userSubsciption, List<AdditionalInformation> additionalInformation, String externalUserId) {
		super();
		this.partnerId = partnerId;
		this.email = email;
		this.userType = userType;
		this.userStatus = UserStatus.PENDING_VERIFICATION;
		this.userProfile = userProfile;
		this.userSubsciption = userSubsciption;
		this.externalUserId = externalUserId;
		if (additionalInformation != null) {
			this.getAdditionalInformation().addAll(additionalInformation);
		}
	}
	
	/**
	 * Instantiates a new user for authentication by provider, where only the external user id is provided.
	 *
	 * @param partnerId the partner id
	 * @param externalUserId the external user id
	 * @param userType the user type
	 * @param additionalInformation the additional information
	 */
	public User(String partnerId, String externalUserId, UserType userType, List<AdditionalInformation> additionalInformation) {
		super();
		this.partnerId = partnerId;
		this.userType = userType;
		this.userStatus = UserStatus.VERIFIED;
		this.externalUserId = externalUserId;
		if (additionalInformation != null) {
            this.getAdditionalInformation().addAll(additionalInformation);
        }
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the partner id.
	 *
	 * @return the partner id
	 */
	public String getPartnerId() {
		return partnerId;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets the user profile.
	 *
	 * @return the user profile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Gets the modified date.
	 *
	 * @return the modified date
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Checks if is deleted.
	 *
	 * @return true, if is deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Gets the user status.
	 *
	 * @return the user status
	 */
	public UserStatus getUserStatus() {
		return userStatus;
	}

	/**
	 * Sets the active.
	 *
	 * @param active
	 *            the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Sets the deleted.
	 *
	 * @param deleted
	 *            the new deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate
	 *            the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Sets the modified date.
	 *
	 * @param modifiedDate
	 *            the new modified date
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * Gets the user type.
	 *
	 * @return the user type
	 */
	public UserType getUserType() {
		return userType;
	}
	
	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the user type.
	 *
	 * @param userType the new user type
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	/**
	 * Gets the user subsciption.
	 *
	 * @return the user subsciption
	 */
	public UserSubsciption getUserSubsciption() {
		return userSubsciption;
	}

	/**
	 * Gets the additional information.
	 *
	 * @return the additional information
	 */
	public List<AdditionalInformation> getAdditionalInformation() {
		return additionalInformation;
	}
	
	/**
	 * Gets the external user id.
	 *
	 * @return the external user id
	 */
	public String getExternalUserId() {
        return externalUserId;
    }

    /**
	 * Sets the user profile.
	 *
	 * @param userProfile the new user profile
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	/**
	 * Sets the user subsciption.
	 *
	 * @param userSubsciption the new user subsciption
	 */
	public void setUserSubsciption(UserSubsciption userSubsciption) {
		this.userSubsciption = userSubsciption;
	}

	/**
	 * Mark as pending verification.
	 */
	public void markAsPendingVerification() {
		userStatus = UserStatus.PENDING_VERIFICATION;
	}

	/**
	 * Mark as verified.
	 */
	public void markAsVerified() {
		userStatus = UserStatus.VERIFIED;
	}

	/**
	 * Checks if is guest user.
	 *
	 * @return true, if is guest user
	 */
	public boolean isGuestUser() {
		return UserType.GUEST.equals(userType);
	}
	
	/**
	 * Checks if is registered user.
	 *
	 * @return true, if is registered user
	 */
	public boolean isEnrolledUser() {
		return UserType.ENROLLED.equals(userType);
	}

    /**
     * Checks if is additional information exists.
     *
     * @param key the key
     * @return true, if is additional information exists
     */
    public boolean isAdditionalInformationExists(String key) {

        for (AdditionalInformation additionalInformationItem : additionalInformation) {
            if (additionalInformationItem.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Update additional information.
     *
     * @param key the key
     * @param value the value
     */
    public void updateAdditionalInformation(String key, String value) {
        for (AdditionalInformation additionalInformationItem : additionalInformation) {
            if (additionalInformationItem.getKey().equals(key)) {
                additionalInformationItem.setValue(value);
                break;
            }
        }
    }

    /**
     * Delete additional information.
     *
     * @param key the key
     */
    public void deleteAdditionalInformation(String key) {
        AdditionalInformation additionalInformationToDelete = null;
        for (AdditionalInformation additionalInformationItem : additionalInformation) {
            if (additionalInformationItem.getKey().equals(key)) {
                additionalInformationToDelete = additionalInformationItem;
            }
        }

        if (additionalInformationToDelete != null) {
            additionalInformation.remove(additionalInformationToDelete);
        }
    }
}
