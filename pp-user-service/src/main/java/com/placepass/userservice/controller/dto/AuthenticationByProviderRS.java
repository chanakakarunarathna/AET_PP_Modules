package com.placepass.userservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The Class AuthenticationByProviderRS.
 * 
 */
@ApiModel(value = "AuthenticationByProviderRS")
public class AuthenticationByProviderRS {

    /** The user id. */
    @JsonIgnore
    private String userId;

    /** The timeout. */
    private long timeout;

    /** The token. */
    private String token;

    /**
     * Instantiates a new authentication by provider rs.
     */
    public AuthenticationByProviderRS() {
        super();
    }

    /**
     * Instantiates a new authentication by provider rs.
     *
     * @param token the token
     * @param userId the user id
     * @param timeout the timeout
     */
    public AuthenticationByProviderRS(String token, String userId, long timeout) {
        super();
        this.token = token;
        this.userId = userId;
        this.timeout = timeout;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    @JsonIgnore
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the timeout.
     *
     * @return the timeout
     */
    @ApiModelProperty(value = "The timeout period for the user service authorization token.")
    public long getTimeout() {
        return timeout;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    @ApiModelProperty(value = "The authorization token which should be sent in Authorization header for authenticated requests.")
    public String getToken() {
        return token;
    }

}
