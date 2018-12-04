package com.placepass.userservice.domain.partner;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The Class PartnerAuthenticationTokenExpirationSpecification.
 * 
 * 
 */
@Document(collection = "partner_auth_token_specification")
public class PartnerAuthenticationTokenExpirationSpecification {

    /** The id. */
    @Id
    private String id;

    /** The partner id. */
    @Indexed
    private String partnerId;
    
    /** The user authentication token timeout. */
    private long userAuthenticationTokenTimeout;
    
    /** The created date. */
    private Date createdDate;

    /** The modified date. */
    private Date modifiedDate;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
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
     * Sets the partner id.
     *
     * @param partnerId the new partner id
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * Gets the user authentication token timeout.
     *
     * @return the user authentication token timeout
     */
    public long getUserAuthenticationTokenTimeout() {
        return userAuthenticationTokenTimeout;
    }

    /**
     * Sets the user authentication token timeout.
     *
     * @param userAuthenticationTokenTimeout the new user authentication token timeout
     */
    public void setUserAuthenticationTokenTimeout(long userAuthenticationTokenTimeout) {
        this.userAuthenticationTokenTimeout = userAuthenticationTokenTimeout;
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
