package com.gobe.connector.domain.gobe.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created on 8/7/2017.
 */
@Document(collection = "products")
public class GobeProduct {
    private String id;

    private String code;

    private String description;

    private String name;

    private String summary;

    private String activityType;

    private String ageRestrictions;

    private String bookingFlowRestrictions;

    private String briefActivityLevel;

    private String briefActivityType;

    private String briefDuration;

    private String cancellationPolicy;

    private String descriptionTitle;

    private String emailImportantConsiderations;

    private String excludedAmenities;

    private String generalDisclaimer;

    private String helpfulHints;

    private String hideCheckoutWherePickup;

    private String highlights;

    private String includedAmenities;

    private String itineraryDuration;

    private String languagesOffered;

    private String latitude;

    private String longitude;

    private String mapZoomLevel;

    private String maximumCapacity;

    private String minimumCapacity;

    private String originalVendorTitle;

    private String pickUpLocation;

    private String saleCurrency;

    private String skuUnit;

    private String startingPriceAdult;

    private String startingPriceChild;

    private String startingPriceLabel1;

    private String startingPriceLabel2;

    private String subtitle;

    private String tourDateAvailability;

    private String transportation;

    private String travelerDetailsType;

    private String ttBookingStyle;

    private List<Variant> variants;

    private String vendorTourCode;

    private String whatToBring;

    private String wheelchairAccessible;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getAgeRestrictions() {
        return ageRestrictions;
    }

    public void setAgeRestrictions(String ageRestrictions) {
        this.ageRestrictions = ageRestrictions;
    }

    public String getBookingFlowRestrictions() {
        return bookingFlowRestrictions;
    }

    public void setBookingFlowRestrictions(String bookingFlowRestrictions) {
        this.bookingFlowRestrictions = bookingFlowRestrictions;
    }

    public String getBriefActivityLevel() {
        return briefActivityLevel;
    }

    public void setBriefActivityLevel(String briefActivityLevel) {
        this.briefActivityLevel = briefActivityLevel;
    }

    public String getBriefActivityType() {
        return briefActivityType;
    }

    public void setBriefActivityType(String briefActivityType) {
        this.briefActivityType = briefActivityType;
    }

    public String getBriefDuration() {
        return briefDuration;
    }

    public void setBriefDuration(String briefDuration) {
        this.briefDuration = briefDuration;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

    public void setDescriptionTitle(String descriptionTitle) {
        this.descriptionTitle = descriptionTitle;
    }

    public String getEmailImportantConsiderations() {
        return emailImportantConsiderations;
    }

    public void setEmailImportantConsiderations(String emailImportantConsiderations) {
        this.emailImportantConsiderations = emailImportantConsiderations;
    }

    public String getExcludedAmenities() {
        return excludedAmenities;
    }

    public void setExcludedAmenities(String excludedAmenities) {
        this.excludedAmenities = excludedAmenities;
    }

    public String getGeneralDisclaimer() {
        return generalDisclaimer;
    }

    public void setGeneralDisclaimer(String generalDisclaimer) {
        this.generalDisclaimer = generalDisclaimer;
    }

    public String getHelpfulHints() {
        return helpfulHints;
    }

    public void setHelpfulHints(String helpfulHints) {
        this.helpfulHints = helpfulHints;
    }

    public String getHideCheckoutWherePickup() {
        return hideCheckoutWherePickup;
    }

    public void setHideCheckoutWherePickup(String hideCheckoutWherePickup) {
        this.hideCheckoutWherePickup = hideCheckoutWherePickup;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getIncludedAmenities() {
        return includedAmenities;
    }

    public void setIncludedAmenities(String includedAmenities) {
        this.includedAmenities = includedAmenities;
    }

    public String getItineraryDuration() {
        return itineraryDuration;
    }

    public void setItineraryDuration(String itineraryDuration) {
        this.itineraryDuration = itineraryDuration;
    }

    public String getLanguagesOffered() {
        return languagesOffered;
    }

    public void setLanguagesOffered(String languagesOffered) {
        this.languagesOffered = languagesOffered;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMapZoomLevel() {
        return mapZoomLevel;
    }

    public void setMapZoomLevel(String mapZoomLevel) {
        this.mapZoomLevel = mapZoomLevel;
    }

    public String getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(String maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public String getMinimumCapacity() {
        return minimumCapacity;
    }

    public void setMinimumCapacity(String minimumCapacity) {
        this.minimumCapacity = minimumCapacity;
    }

    public String getOriginalVendorTitle() {
        return originalVendorTitle;
    }

    public void setOriginalVendorTitle(String originalVendorTitle) {
        this.originalVendorTitle = originalVendorTitle;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getSaleCurrency() {
        return saleCurrency;
    }

    public void setSaleCurrency(String saleCurrency) {
        this.saleCurrency = saleCurrency;
    }

    public String getSkuUnit() {
        return skuUnit;
    }

    public void setSkuUnit(String skuUnit) {
        this.skuUnit = skuUnit;
    }

    public String getStartingPriceAdult() {
        return startingPriceAdult;
    }

    public void setStartingPriceAdult(String startingPriceAdult) {
        this.startingPriceAdult = startingPriceAdult;
    }

    public String getStartingPriceChild() {
        return startingPriceChild;
    }

    public void setStartingPriceChild(String startingPriceChild) {
        this.startingPriceChild = startingPriceChild;
    }

    public String getStartingPriceLabel1() {
        return startingPriceLabel1;
    }

    public void setStartingPriceLabel1(String startingPriceLabel1) {
        this.startingPriceLabel1 = startingPriceLabel1;
    }

    public String getStartingPriceLabel2() {
        return startingPriceLabel2;
    }

    public void setStartingPriceLabel2(String startingPriceLabel2) {
        this.startingPriceLabel2 = startingPriceLabel2;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTourDateAvailability() {
        return tourDateAvailability;
    }

    public void setTourDateAvailability(String tourDateAvailability) {
        this.tourDateAvailability = tourDateAvailability;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getTravelerDetailsType() {
        return travelerDetailsType;
    }

    public void setTravelerDetailsType(String travelerDetailsType) {
        this.travelerDetailsType = travelerDetailsType;
    }

    public String getTtBookingStyle() {
        return ttBookingStyle;
    }

    public void setTtBookingStyle(String ttBookingStyle) {
        this.ttBookingStyle = ttBookingStyle;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public String getVendorTourCode() {
        return vendorTourCode;
    }

    public void setVendorTourCode(String vendorTourCode) {
        this.vendorTourCode = vendorTourCode;
    }

    public String getWhatToBring() {
        return whatToBring;
    }

    public void setWhatToBring(String whatToBring) {
        this.whatToBring = whatToBring;
    }

    public String getWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(String wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
