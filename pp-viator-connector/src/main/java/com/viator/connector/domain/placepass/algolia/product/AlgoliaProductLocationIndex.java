package com.viator.connector.domain.placepass.algolia.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaProductLocationIndex {
    @JsonProperty("objectID")
    private String placeId;

    @JsonProperty("WebId")
    private String webId;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("RegionWebId")
    private String regionWebId;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("CountryWebId")
    private String countryWebId;

    @JsonProperty("PlacePassRegion")
    private String placePassRegion;

    @JsonProperty("PlacePassRegionWebId")
    private String placePassRegionWebId;

    @JsonProperty("AddressType")
    private String addressType;

    private AlgoliaProductGeolocation _geoloc;

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

    public AlgoliaProductGeolocation get_geoloc() {
        return _geoloc;
    }

    public void set_geoloc(AlgoliaProductGeolocation _geoloc) {
        this._geoloc = _geoloc;
    }
    
}
