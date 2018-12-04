package com.placepass.booking.domain.product;

import java.util.List;

public class ProductDetails {

    private String productId;

    private String languageCode;

    private String title;

    private String description;

    private WebLocation webLocation;

    private String vendor;

    private String cancellationPolicy;

    private String itinerary;

    private List<String> images;

    private List<String> videos;

    private List<String> category;

    private List<String> inclusions;

    private List<String> exclusions;

    private List<String> highlights;

    private Supplier supplier;

    private List<String> activitySnapshots;

    private List<Location> meetingPoint;

    private List<Location> dropOffPoint;

    private List<String> voucherTypes;

    private List<Integer> durationsInMinutes;

    private Integer rewardPoints;

    private List<Price> prices;

    private int reviewCount;

    private Boolean triedAndTrueGuarantee;

    private float avgRating;

    private String voucherRequirements;

    private List<String> additionalInfo;

    private List<AgeBand> eligibleAgeBands;

    private Boolean isHotelPickUpEligible;

    private CancellationRules cancellationRules;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WebLocation getWebLocation() {
        return webLocation;
    }

    public void setWebLocation(WebLocation webLocation) {
        this.webLocation = webLocation;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getInclusions() {
        return inclusions;
    }

    public void setInclusions(List<String> inclusions) {
        this.inclusions = inclusions;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<String> exclusions) {
        this.exclusions = exclusions;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<String> getActivitySnapshots() {
        return activitySnapshots;
    }

    public void setActivitySnapshots(List<String> activitySnapshots) {
        this.activitySnapshots = activitySnapshots;
    }

    public List<Location> getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(List<Location> meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public List<Location> getDropOffPoint() {
        return dropOffPoint;
    }

    public void setDropOffPoint(List<Location> dropOffPoint) {
        this.dropOffPoint = dropOffPoint;
    }

    public List<String> getVoucherTypes() {
        return voucherTypes;
    }

    public void setVoucherTypes(List<String> voucherTypes) {
        this.voucherTypes = voucherTypes;
    }

    public List<Integer> getDurationsInMinutes() {
        return durationsInMinutes;
    }

    public void setDurationsInMinutes(List<Integer> durationsInMinutes) {
        this.durationsInMinutes = durationsInMinutes;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Boolean getTriedAndTrueGuarantee() {
        return triedAndTrueGuarantee;
    }

    public void setTriedAndTrueGuarantee(Boolean triedAndTrueGuarantee) {
        this.triedAndTrueGuarantee = triedAndTrueGuarantee;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public String getVoucherRequirements() {
        return voucherRequirements;
    }

    public void setVoucherRequirements(String voucherRequirements) {
        this.voucherRequirements = voucherRequirements;
    }

    public List<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<AgeBand> getEligibleAgeBands() {
        return eligibleAgeBands;
    }

    public void setEligibleAgeBands(List<AgeBand> eligibleAgeBands) {
        this.eligibleAgeBands = eligibleAgeBands;
    }

    public Boolean getIsHotelPickUpEligible() {
        return isHotelPickUpEligible;
    }

    public void setIsHotelPickUpEligible(Boolean isHotelPickUpEligible) {
        this.isHotelPickUpEligible = isHotelPickUpEligible;
    }

    public CancellationRules getCancellationRules() {
        return cancellationRules;
    }

    public void setCancellationRules(CancellationRules cancellationRules) {
        this.cancellationRules = cancellationRules;
    }

}
