package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgProductDetailsResInfo {

    // Response for .../v1/products/{uuid}

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("title")
    private String title;

    @JsonProperty("titleTranslated")
    private String titleTranslated;

    @JsonProperty("description")
    private String description;

    @JsonProperty("descriptionTranslated")
    private String descriptionTranslated;

    @JsonProperty("highlights")
    private String highlights;

    @JsonProperty("highlightsTranslated")
    private String highlightsTranslated;

    @JsonProperty("additionalInfo")
    private String additionalInfo;

    @JsonProperty("additionalInfoTranslated")
    private String additionalInfoTranslated;

    @JsonProperty("priceIncludes")
    private String priceIncludes;

    @JsonProperty("priceIncludesTranslated")
    private String priceIncludesTranslated;

    @JsonProperty("priceExcludes")
    private String priceExcludes;

    @JsonProperty("priceExcludesTranslated")
    private String priceExcludesTranslated;

    @JsonProperty("validFrom")
    private String validFrom;

    @JsonProperty("validThrough")
    private String validThrough;

    @JsonProperty("itinerary")
    private String itinerary;

    @JsonProperty("itineraryTranslated")
    private String itineraryTranslated;

    @JsonProperty("warnings")
    private String warnings;

    @JsonProperty("warningsTranslated")
    private String warningsTranslated;

    @JsonProperty("safety")
    private String safety;

    @JsonProperty("safetyTranslated")
    private String safetyTranslated;

    @JsonProperty("latitude")
    private float latitude;

    @JsonProperty("longitude")
    private float longitude;

    @JsonProperty("w3wAddress")
    private String w3wAddress;

    @JsonProperty("minPax")
    private int minPax;

    @JsonProperty("maxPax")
    private int maxPax;

    @JsonProperty("basePrice")
    private double basePrice;

    @JsonProperty("currency")
    private BmgCurrency currency;

    @JsonProperty("isFlatPaxPrice")
    private boolean isFlatPaxPrice;

    @JsonProperty("reviewCount")
    private int reviewCount;

    @JsonProperty("reviewAverageScore")
    private double reviewAverageScore;

    @JsonProperty("typeName")
    private String typeName;

    @JsonProperty("typeUuid")
    private UUID typeUuid;

    @JsonProperty("photosUrl")
    private String photosUrl;

    @JsonProperty("businessHoursFrom")
    private String businessHoursFrom;

    @JsonProperty("businessHoursTo")
    private String businessHoursTo;

    @JsonProperty("meetingTime")
    private String meetingTime;

    @JsonProperty("averageDelivery")
    private int averageDelivery;

    @JsonProperty("hotelPickup")
    private boolean hotelPickup;

    @JsonProperty("airportPickup")
    private boolean airportPickup;

    @JsonProperty("meetingLocation")
    private String meetingLocation;

    @JsonProperty("meetingLocationTranslated")
    private String meetingLocationTranslated;

    @JsonProperty("photos")
    private List<BmgPhoto> photos;

    @JsonProperty("categories")
    private List<BmgBasicField> categories;

    @JsonProperty("productTypes")
    private List<BmgProductTypes> productTypes;

    @JsonProperty("locations")
    private List<BmgLocations> locations;

    @JsonProperty("url")
    private String url;

    @JsonProperty("staticUrl")
    private String staticUrl;

    @JsonProperty("guideLanguages")
    private List<BmgBasicField> guideLanguages;

    @JsonProperty("audioHeadsetLanguages")
    private List<BmgBasicField> audioHeadsetLanguages;

    @JsonProperty("writtenLanguages")
    private List<BmgBasicField> writtenLanguages;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleTranslated() {
        return titleTranslated;
    }

    public void setTitleTranslated(String titleTranslated) {
        this.titleTranslated = titleTranslated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionTranslated() {
        return descriptionTranslated;
    }

    public void setDescriptionTranslated(String descriptionTranslated) {
        this.descriptionTranslated = descriptionTranslated;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getHighlightsTranslated() {
        return highlightsTranslated;
    }

    public void setHighlightsTranslated(String highlightsTranslated) {
        this.highlightsTranslated = highlightsTranslated;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfoTranslated() {
        return additionalInfoTranslated;
    }

    public void setAdditionalInfoTranslated(String additionalInfoTranslated) {
        this.additionalInfoTranslated = additionalInfoTranslated;
    }

    public String getPriceIncludes() {
        return priceIncludes;
    }

    public void setPriceIncludes(String priceIncludes) {
        this.priceIncludes = priceIncludes;
    }

    public String getPriceIncludesTranslated() {
        return priceIncludesTranslated;
    }

    public void setPriceIncludesTranslated(String priceIncludesTranslated) {
        this.priceIncludesTranslated = priceIncludesTranslated;
    }

    public String getPriceExcludes() {
        return priceExcludes;
    }

    public void setPriceExcludes(String priceExcludes) {
        this.priceExcludes = priceExcludes;
    }

    public String getPriceExcludesTranslated() {
        return priceExcludesTranslated;
    }

    public void setPriceExcludesTranslated(String priceExcludesTranslated) {
        this.priceExcludesTranslated = priceExcludesTranslated;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidThrough() {
        return validThrough;
    }

    public void setValidThrough(String validThrough) {
        this.validThrough = validThrough;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getItineraryTranslated() {
        return itineraryTranslated;
    }

    public void setItineraryTranslated(String itineraryTranslated) {
        this.itineraryTranslated = itineraryTranslated;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getWarningsTranslated() {
        return warningsTranslated;
    }

    public void setWarningsTranslated(String warningsTranslated) {
        this.warningsTranslated = warningsTranslated;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getSafetyTranslated() {
        return safetyTranslated;
    }

    public void setSafetyTranslated(String safetyTranslated) {
        this.safetyTranslated = safetyTranslated;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getW3wAddress() {
        return w3wAddress;
    }

    public void setW3wAddress(String w3wAddress) {
        this.w3wAddress = w3wAddress;
    }

    public int getMinPax() {
        return minPax;
    }

    public void setMinPax(int minPax) {
        this.minPax = minPax;
    }

    public int getMaxPax() {
        return maxPax;
    }

    public void setMaxPax(int maxPax) {
        this.maxPax = maxPax;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public BmgCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BmgCurrency currency) {
        this.currency = currency;
    }

    public boolean getIsFlatPaxPrice() {
        return isFlatPaxPrice;
    }

    public void setFlatPaxPrice(boolean isFlatPaxPrice) {
        this.isFlatPaxPrice = isFlatPaxPrice;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getReviewAverageScore() {
        return reviewAverageScore;
    }

    public void setReviewAverageScore(double reviewAverageScore) {
        this.reviewAverageScore = reviewAverageScore;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public UUID getTypeUuid() {
        return typeUuid;
    }

    public void setTypeUuid(UUID typeUuid) {
        this.typeUuid = typeUuid;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getBusinessHoursFrom() {
        return businessHoursFrom;
    }

    public void setBusinessHoursFrom(String businessHoursFrom) {
        this.businessHoursFrom = businessHoursFrom;
    }

    public String getBusinessHoursTo() {
        return businessHoursTo;
    }

    public void setBusinessHoursTo(String businessHoursTo) {
        this.businessHoursTo = businessHoursTo;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public int getAverageDelivery() {
        return averageDelivery;
    }

    public void setAverageDelivery(int averageDelivery) {
        this.averageDelivery = averageDelivery;
    }

    public boolean isHotelPickup() {
        return hotelPickup;
    }

    public void setHotelPickup(boolean hotelPickup) {
        this.hotelPickup = hotelPickup;
    }

    public boolean isAirportPickup() {
        return airportPickup;
    }

    public void setAirportPickup(boolean airportPickup) {
        this.airportPickup = airportPickup;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public String getMeetingLocationTranslated() {
        return meetingLocationTranslated;
    }

    public void setMeetingLocationTranslated(String meetingLocationTranslated) {
        this.meetingLocationTranslated = meetingLocationTranslated;
    }

    public List<BmgPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<BmgPhoto> photos) {
        this.photos = photos;
    }

    public List<BmgBasicField> getCategories() {
        return categories;
    }

    public void setCategories(List<BmgBasicField> categories) {
        this.categories = categories;
    }

    public List<BmgProductTypes> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<BmgProductTypes> productTypes) {
        this.productTypes = productTypes;
    }

    public List<BmgLocations> getLocations() {
        return locations;
    }

    public void setLocations(List<BmgLocations> locations) {
        this.locations = locations;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
    }

    public List<BmgBasicField> getGuideLanguages() {
        return guideLanguages;
    }

    public void setGuideLanguages(List<BmgBasicField> guideLanguages) {
        this.guideLanguages = guideLanguages;
    }

    public List<BmgBasicField> getAudioHeadsetLanguages() {
        return audioHeadsetLanguages;
    }

    public void setAudioHeadsetLanguages(List<BmgBasicField> audioHeadsetLanguages) {
        this.audioHeadsetLanguages = audioHeadsetLanguages;
    }

    public List<BmgBasicField> getWrittenLanguages() {
        return writtenLanguages;
    }

    public void setWrittenLanguages(List<BmgBasicField> writtenLanguages) {
        this.writtenLanguages = writtenLanguages;
    }

}
