package com.placepass.connector.bemyguest.domain.placepass.algolia.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoliaProductCatalog {
    private String objectID;

    @JsonProperty("ProductId")
    private String productId;

    @JsonProperty("Vendor")
    private String vendor;

    @JsonProperty("ProductName")
    private String productName;

    @JsonProperty("ProductText")
    private String productText;

    @JsonProperty("Duration")
    private String duration;

    @JsonProperty("Special")
    private Integer special;

    @JsonProperty("ProductImage")
    private String productImage;

    @JsonProperty("Continent")
    private String continent;

    @JsonProperty("RewardPoints")    
    private Integer rewardPoints;
    
    @JsonProperty("Country")
    private String country;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Airport")
    private String airport;

    @JsonProperty("Group")
    private List<String> group;

    @JsonProperty("SubGroup")
    private List<String> subGroup;

    @JsonProperty("Category")
    private List<String> category;

    @JsonProperty("ProductUrl")
    private String productUrl;

    @JsonProperty("Price")
    private Double price;

    @JsonProperty("AvgRating")
    private Double avgRating;

    @JsonProperty("UpdateOn")
    private String updateOn;

    @JsonProperty("_tags")
    private List<String> tags;

    @JsonProperty("Reviews")
    private Double reviews;

    @JsonProperty("DestinationId")
    private String destinationId;

    @JsonProperty("FormattedAddress")
    private List<String> formattedAddress;

    @JsonProperty("Rank")
    private Integer rank;

    @JsonProperty("GeoLocationIds")
    private List<String> geoLocationIds;

    @JsonProperty("PlacePassRegion")
    private String placePassRegion;

    @JsonProperty("DurationInMinutes")
    private Double durationInMinutes;

    @JsonProperty("TriedAndTrueGuarantee")
    private Boolean triedAndTrueGuarantee;

    @JsonProperty("Marriott")
    private AlgoliaPartnerProperties marriott;

    @JsonProperty("PlacePass")
    private AlgoliaPartnerProperties placepass;

    @JsonProperty("_geoloc;")
    private AlgoliaProductLocation geoloc;

    private AlgoliaProductDetail details;

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public List<String> getGroup() {
        return group;
    }

    public void setGroup(List<String> group) {
        this.group = group;
    }

    public List<String> getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(List<String> subGroup) {
        this.subGroup = subGroup;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public String getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = updateOn;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Double getReviews() {
        return reviews;
    }

    public void setReviews(Double reviews) {
        this.reviews = reviews;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public List<String> getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(List<String> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public List<String> getGeoLocationIds() {
        return geoLocationIds;
    }

    public void setGeoLocationIds(List<String> geoLocationIds) {
        this.geoLocationIds = geoLocationIds;
    }

    public String getPlacePassRegion() {
        return placePassRegion;
    }

    public void setPlacePassRegion(String placePassRegion) {
        this.placePassRegion = placePassRegion;
    }

    public Double getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Double durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Boolean getTriedAndTrueGuarantee() {
        return triedAndTrueGuarantee;
    }

    public void setTriedAndTrueGuarantee(Boolean triedAndTrueGuarantee) {
        this.triedAndTrueGuarantee = triedAndTrueGuarantee;
    }

    public AlgoliaPartnerProperties getMarriott() {
        return marriott;
    }

    public void setMarriott(AlgoliaPartnerProperties marriott) {
        this.marriott = marriott;
    }

    public AlgoliaPartnerProperties getPlacepass() {
        return placepass;
    }

    public void setPlacepass(AlgoliaPartnerProperties placepass) {
        this.placepass = placepass;
    }

    public AlgoliaProductLocation getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(AlgoliaProductLocation geoloc) {
        this.geoloc = geoloc;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public AlgoliaProductDetail getDetails() {
        return details;
    }

    public void setDetails(AlgoliaProductDetail details) {
        this.details = details;
    }

}
