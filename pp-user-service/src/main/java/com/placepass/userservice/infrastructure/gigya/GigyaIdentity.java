package com.placepass.userservice.infrastructure.gigya;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class GigyaIdentity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GigyaIdentity {
    
    /** The gigya saml data. */
    @JsonProperty("samlData")
    private GigyaSAMLData gigyaSAMLData;

    /**
     * Gets the gigya saml data.
     *
     * @return the gigya saml data
     */
    public GigyaSAMLData getGigyaSAMLData() {
        return gigyaSAMLData;
    }

    /**
     * Sets the gigya saml data.
     *
     * @param gigyaSAMLData the new gigya saml data
     */
    public void setGigyaSAMLData(GigyaSAMLData gigyaSAMLData) {
        this.gigyaSAMLData = gigyaSAMLData;
    }
    
    
}
