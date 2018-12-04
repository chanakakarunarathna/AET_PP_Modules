package com.placepass.userservice.infrastructure.gigya;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class GigyaSAMLData.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GigyaSAMLData {
    
    /** The rewards id. */
    @JsonProperty("RewardsID")
    private String rewardsID;

    /**
     * Gets the rewards id.
     *
     * @return the rewards id
     */
    public String getRewardsID() {
        return rewardsID;
    }

    /**
     * Sets the rewards id.
     *
     * @param rewardsID the new rewards id
     */
    public void setRewardsID(String rewardsID) {
        this.rewardsID = rewardsID;
    }
    

}
