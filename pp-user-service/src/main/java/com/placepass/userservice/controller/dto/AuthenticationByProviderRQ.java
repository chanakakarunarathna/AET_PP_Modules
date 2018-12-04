package com.placepass.userservice.controller.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The Class AuthenticationByProviderRQ.
 * 
 */
@ApiModel(value = "AuthenticationByProviderRQ")
public class AuthenticationByProviderRQ {

    /** The provider. */
    @ApiModelProperty(value = "The identity provider name e.g. 'GIGYA'.")
    private String provider;

    /** The external user id. */
    @ApiModelProperty(value = "The external user Id.")
    private String externalUserId;

    /** The provider token. */
    @ApiModelProperty(value = "The identity provider token.")
    private String providerToken;
    
    /** The additional information. */
    @ApiModelProperty(value = "The additional information key/value pair.")
    private List<AdditionalInformation> additionalInformation;

    /**
     * Gets the external user id.
     *
     * @return the external user id
     */
    public String getExternalUserId() {
        return externalUserId;
    }

    /**
     * Gets the provider.
     *
     * @return the provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Gets the provider token.
     *
     * @return the provider token
     */
    public String getProviderToken() {
        return providerToken;
    }
    
    /**
     * Gets the additional information.
     *
     * @return the additional information
     */
    public List<AdditionalInformation> getAdditionalInformation() {
        if (additionalInformation == null) {
            additionalInformation = new ArrayList<AdditionalInformation>();
        }

        return additionalInformation;
    }

}
