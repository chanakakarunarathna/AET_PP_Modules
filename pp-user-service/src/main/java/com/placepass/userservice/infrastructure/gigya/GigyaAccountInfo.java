package com.placepass.userservice.infrastructure.gigya;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GigyaAccountInfo {

    @JsonProperty("UID")
    private String uid;

    @JsonProperty("profile")
    private GigyaProfile profile;
    
    @JsonProperty("identities")
    private List<GigyaIdentity> GigyaIdentities;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public GigyaProfile getProfile() {
        return profile;
    }

    public void setProfile(GigyaProfile profile) {
        this.profile = profile;
    }

    public List<GigyaIdentity> getGigyaIdentities() {
        return GigyaIdentities;
    }
    
    

}
