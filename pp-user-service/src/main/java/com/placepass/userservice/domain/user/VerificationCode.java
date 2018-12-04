package com.placepass.userservice.domain.user;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class VerificationCode.
 */
@Document(collection = "verification_code")
public class VerificationCode {
	
	/** The id. */
	@Id
	private String id;
	
	/** The user id. */
	private String userId;
	
	/** The partner id. */
	private String partnerId;
	
	/** The code. */
	@Indexed
	private String code;
	
	/** The verification type. */
	private VerificationType verificationType;
	
	/** The verification channel type. */
	private VerificationChannelType verificationChannelType;
	
	/** The verification status. */
	private VerificationStatus verificationStatus;
	
	/** The created date. */
	private Date createdDate;
	
	/** The modified date. */
	private Date modifiedDate;
	
	/** The version. */
	private String version = "1.0.0";
	
	/**
	 * Instantiates a new verification code.
	 *
	 * @param userId the user id
	 * @param partnerId the partner id
	 * @param code the code
	 * @param verificationType the verification type
	 * @param verificationChannelType the verification channel type
	 */
	public VerificationCode(String userId, String partnerId, String code,
			VerificationType verificationType, VerificationChannelType verificationChannelType) {
		super();
		this.userId = userId;
		this.partnerId = partnerId;
		this.code = code;
		this.verificationType = verificationType;
		this.verificationChannelType = verificationChannelType;
		this.verificationStatus = VerificationStatus.PENDING;
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
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
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
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
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
	 * Gets the verification type.
	 *
	 * @return the verification type
	 */
	public VerificationType getVerificationType() {
		return verificationType;
	}

	/**
	 * Gets the verification status.
	 *
	 * @return the verification status
	 */
	public VerificationStatus getVerificationStatus() {
		return verificationStatus;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	 * Sets the modified date.
	 *
	 * @param modifiedDate the new modified date
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
		
	/**
	 * Gets the verification channel type.
	 *
	 * @return the verification channel type
	 */
	public VerificationChannelType getVerificationChannelType() {
		return verificationChannelType;
	}

	/**
	 * Mark as expired.
	 */
	public void markAsExpired(){
		verificationStatus = VerificationStatus.EXPIRED;
	}
	
	
}
