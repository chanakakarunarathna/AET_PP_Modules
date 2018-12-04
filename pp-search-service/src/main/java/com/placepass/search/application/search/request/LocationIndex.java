package com.placepass.search.application.search.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationIndex {
    @JsonProperty("id")
    private String placeId;

    private String webId;

    private String location;

    private String city;

    private String region;

    private String regionWebId;

    private String country;

    private String countryWebId;

    private String placePassRegion;

    private String placePassRegionWebId;

    private String addressType;

    private Geolocation _geoloc;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionWebId() {
        return regionWebId;
    }

    public void setRegionWebId(String regionWebId) {
        this.regionWebId = regionWebId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryWebId() {
        return countryWebId;
    }

    public void setCountryWebId(String countryWebId) {
        this.countryWebId = countryWebId;
    }

    public String getPlacePassRegion() {
        return placePassRegion;
    }

    public void setPlacePassRegion(String placePassRegion) {
        this.placePassRegion = placePassRegion;
    }

    public String getPlacePassRegionWebId() {
        return placePassRegionWebId;
    }

    public void setPlacePassRegionWebId(String placePassRegionWebId) {
        this.placePassRegionWebId = placePassRegionWebId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Geolocation get_geoloc() {
        return _geoloc;
    }

    public void set_geoloc(Geolocation _geoloc) {
        this._geoloc = _geoloc;
    }
    
}
