package com.placepass.product.application.productdetails.dto;

import java.util.List;
import java.util.concurrent.CancellationException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetail {
    private String productId;

    private String languageCode;

    @ApiModelProperty(position = 1, notes = "Title")
    private String title;

    private String vendor;

    private String itinerary;

    private String cancellationPolicy;

    private String description;

    @ApiModelProperty(notes = "List of images")
    private List<String> images;

    private List<String> videos;

    private List<String> category;

    private List<String> inclusions;

    private List<String> exclusions;

    private List<String> highlights;

    private SupplierDTO supplier;

    private List<String> activitySnapshots;

    private List<LocationDTO> meetingPoint;

    private List<LocationDTO> dropOffPoint;

    private List<String> voucherTypes;

    private List<Integer> durationsInMinutes;

    private List<PriceDTO> prices;

    private int reviewCount;

    private float avgRating;

    private String voucherRequirements;

    private List<String> additionalInfo;

    private List<AgeBand> eligibleAgeBands;

    private Boolean isHotelPickUpEligible;

    private List<BookingQuestion> bookingQuestions;
    
    private List<String> partnerIds;
    
    private List<BookingOptionLangSvc> bookingOptionLangSvcs;

    private CancellationRules cancellationRules;

    private Boolean productDetailDeeplink;

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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public List<String> getActivitySnapshots() {
        return activitySnapshots;
    }

    public void setActivitySnapshots(List<String> activitySnapshots) {
        this.activitySnapshots = activitySnapshots;
    }

    public List<LocationDTO> getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(List<LocationDTO> meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public List<LocationDTO> getDropOffPoint() {
        return dropOffPoint;
    }

    public void setDropOffPoint(List<LocationDTO> dropOffPoint) {
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

    public List<PriceDTO> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceDTO> prices) {
        this.prices = prices;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
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

    public List<BookingQuestion> getBookingQuestions() {
        return bookingQuestions;
    }

    public void setBookingQuestions(List<BookingQuestion> bookingQuestions) {
        this.bookingQuestions = bookingQuestions;
    }

    public List<String> getPartnerIds() {
        return partnerIds;
    }

    public void setPartnerIds(List<String> partnerIds) {
        this.partnerIds = partnerIds;
    }

    public List<BookingOptionLangSvc> getBookingOptionLangSvcs() {
        return bookingOptionLangSvcs;
    }

    public void setBookingOptionLangSvcs(List<BookingOptionLangSvc> bookingOptionLangSvcs) {
        this.bookingOptionLangSvcs = bookingOptionLangSvcs;
    }

    public CancellationRules getCancellationRules() {
        return cancellationRules;
    }

    public void setCancellationRules(CancellationRules cancellationRules) {
        this.cancellationRules = cancellationRules;
    }

    public Boolean getProductDetailDeeplink() {
        if (productDetailDeeplink == null) {
            return false;
        }
        return productDetailDeeplink;
    }

    public void setProductDetailDeeplink(Boolean productDetailDeeplink) {
        this.productDetailDeeplink = productDetailDeeplink;
    }
}
