package com.placepass.userservice.domain.partner;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class PartnerVerificationSpecification.
 * 
 * 
 */
@Document(collection = "partner_verification_specification")
public class PartnerVerificationSpecification {

	/** The id. */
	@Id
	private String id;

	/** The partner id. */
	@Indexed
	private String partnerId;
	
	/** The verification enabled. */
	private boolean verificationEnabled;
	
	/** The created date. */
	private Date createdDate;

	/** The modified date. */
	private Date modifiedDate;

	/**
	 * Instantiates a new partner verification specification.
	 *
	 * @param partnerId the partner id
	 * @param verificationEnabled the verification enabled
	 */
	public PartnerVerificationSpecification(String partnerId, boolean verificationEnabled) {
		super();
		this.partnerId = partnerId;
		this.verificationEnabled = verificationEnabled;
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
	 * Checks if is verification enabled.
	 *
	 * @return true, if is verification enabled
	 */
	public boolean isVerificationEnabled() {
		return verificationEnabled;
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
	
	

}
