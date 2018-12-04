package com.viator.connector.domain.viator.product;

import java.util.List;

import com.viator.connector.domain.viator.reviews.ViatorProductReviewsResInfo;

public class ViatorProductResInfo {
    private String supplierName;

    private String currencyCode;

    private List<Integer> catIds;

    private List<Integer> subCatIds;

    private Object webURL;

    private Object specialReservationDetails;

    private Integer panoramaCount;

    private Boolean merchantCancellable;

    private String bookingEngineId;

    private Object onRequestPeriod;

    private Object primaryGroupId;

    private Object pas;

    private Boolean available;

    private String voucherRequirements;

    private Boolean tourGradesAvailable;

    private Boolean hotelPickup;

    private List<ViatorProductResUserphoto> userPhotos;

    private List<ViatorProductReviewsResInfo> reviews;

    private Object videos;

    private List<ViatorProductResTourgrade> tourGrades;

    private List<ViatorProductResAgeband> ageBands;

    private List<ViatorProductResBookingQuestion> bookingQuestions;

    private Object passengerAttributes;

    private List<String> highlights;

    private List<String> salesPoints;

    private ViatorProductResRatingcounts ratingCounts;

    private Object termsAndConditions;

    private Integer maxTravellerCount;

    private String itinerary;

    private Integer destinationId;

    private List<String> additionalInfo;

    private List<String> inclusions;

    private String voucherOption;

    private Boolean applePassSupported;

    private List<ViatorProductResPhoto> productPhotos;

    private String departureTime;

    private String departurePoint;

    private String departureTimeComments;

    private String city;

    private String returnDetails;

    private String specialOffer;

    private String operates;

    private Object mapURL;

    private Boolean allTravellerNamesRequired;

    private List<String> exclusions;

    private String description;

    private String location;

    private String country;

    private String region;

    private String title;

    private String shortDescription;

    private Float price;

    private String supplierCode;

    private Integer translationLevel;

    private Integer primaryDestinationId;

    private String primaryDestinationName;

    private String thumbnailURL;

    private String priceFormatted;

    private Float rrp;

    private String rrpformatted;

    private Boolean onSale;

    private Integer videoCount;

    private Float rating;

    private String thumbnailHiResURL;

    private Integer photoCount;

    private Integer reviewCount;

    private Boolean specialReservation;

    private String shortTitle;

    private Float merchantNetPriceFrom;

    private Float savingAmount;

    private String merchantNetPriceFromFormatted;

    private String savingAmountFormated;

    private Boolean specialOfferAvailable;

    private Object essential;

    private Object admission;

    private String duration;

    private String code;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<Integer> getCatIds() {
        return catIds;
    }

    public void setCatIds(List<Integer> catIds) {
        this.catIds = catIds;
    }

    public List<Integer> getSubCatIds() {
        return subCatIds;
    }

    public void setSubCatIds(List<Integer> subCatIds) {
        this.subCatIds = subCatIds;
    }

    public Object getWebURL() {
        return webURL;
    }

    public void setWebURL(Object webURL) {
        this.webURL = webURL;
    }

    public Object getSpecialReservationDetails() {
        return specialReservationDetails;
    }

    public void setSpecialReservationDetails(Object specialReservationDetails) {
        this.specialReservationDetails = specialReservationDetails;
    }

    public Integer getPanoramaCount() {
        return panoramaCount;
    }

    public void setPanoramaCount(Integer panoramaCount) {
        this.panoramaCount = panoramaCount;
    }

    public Boolean getMerchantCancellable() {
        return merchantCancellable;
    }

    public void setMerchantCancellable(Boolean merchantCancellable) {
        this.merchantCancellable = merchantCancellable;
    }

    public String getBookingEngineId() {
        return bookingEngineId;
    }

    public void setBookingEngineId(String bookingEngineId) {
        this.bookingEngineId = bookingEngineId;
    }

    public Object getOnRequestPeriod() {
        return onRequestPeriod;
    }

    public void setOnRequestPeriod(Object onRequestPeriod) {
        this.onRequestPeriod = onRequestPeriod;
    }

    public Object getPrimaryGroupId() {
        return primaryGroupId;
    }

    public void setPrimaryGroupId(Object primaryGroupId) {
        this.primaryGroupId = primaryGroupId;
    }

    public Object getPas() {
        return pas;
    }

    public void setPas(Object pas) {
        this.pas = pas;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getVoucherRequirements() {
        return voucherRequirements;
    }

    public void setVoucherRequirements(String voucherRequirements) {
        this.voucherRequirements = voucherRequirements;
    }

    public Boolean getTourGradesAvailable() {
        return tourGradesAvailable;
    }

    public void setTourGradesAvailable(Boolean tourGradesAvailable) {
        this.tourGradesAvailable = tourGradesAvailable;
    }

    public Boolean getHotelPickup() {
        return hotelPickup;
    }

    public void setHotelPickup(Boolean hotelPickup) {
        this.hotelPickup = hotelPickup;
    }

    public List<ViatorProductResUserphoto> getUserPhotos() {
        return userPhotos;
    }

    public void setUserPhotos(List<ViatorProductResUserphoto> userPhotos) {
        this.userPhotos = userPhotos;
    }

    public List<ViatorProductReviewsResInfo> getReviews() {
        return reviews;
    }

    public void setReviews(List<ViatorProductReviewsResInfo> reviews) {
        this.reviews = reviews;
    }

    public Object getVideos() {
        return videos;
    }

    public void setVideos(Object videos) {
        this.videos = videos;
    }

    public List<ViatorProductResTourgrade> getTourGrades() {
        return tourGrades;
    }

    public void setTourGrades(List<ViatorProductResTourgrade> tourGrades) {
        this.tourGrades = tourGrades;
    }

    public List<ViatorProductResAgeband> getAgeBands() {
        return ageBands;
    }

    public void setAgeBands(List<ViatorProductResAgeband> ageBands) {
        this.ageBands = ageBands;
    }

    public List<ViatorProductResBookingQuestion> getBookingQuestions() {
        return bookingQuestions;
    }

    public void setBookingQuestions(List<ViatorProductResBookingQuestion> bookingQuestions) {
        this.bookingQuestions = bookingQuestions;
    }

    public Object getPassengerAttributes() {
        return passengerAttributes;
    }

    public void setPassengerAttributes(Object passengerAttributes) {
        this.passengerAttributes = passengerAttributes;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public List<String> getSalesPoints() {
        return salesPoints;
    }

    public void setSalesPoints(List<String> salesPoints) {
        this.salesPoints = salesPoints;
    }

    public ViatorProductResRatingcounts getRatingCounts() {
        return ratingCounts;
    }

    public void setRatingCounts(ViatorProductResRatingcounts ratingCounts) {
        this.ratingCounts = ratingCounts;
    }

    public Object getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(Object termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public Integer getMaxTravellerCount() {
        return maxTravellerCount;
    }

    public void setMaxTravellerCount(Integer maxTravellerCount) {
        this.maxTravellerCount = maxTravellerCount;
    }

    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public List<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<String> getInclusions() {
        return inclusions;
    }

    public void setInclusions(List<String> inclusions) {
        this.inclusions = inclusions;
    }

    public String getVoucherOption() {
        return voucherOption;
    }

    public void setVoucherOption(String voucherOption) {
        this.voucherOption = voucherOption;
    }

    public Boolean getApplePassSupported() {
        return applePassSupported;
    }

    public void setApplePassSupported(Boolean applePassSupported) {
        this.applePassSupported = applePassSupported;
    }

    public List<ViatorProductResPhoto> getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(List<ViatorProductResPhoto> productPhotos) {
        this.productPhotos = productPhotos;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDepartureTimeComments() {
        return departureTimeComments;
    }

    public void setDepartureTimeComments(String departureTimeComments) {
        this.departureTimeComments = departureTimeComments;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReturnDetails() {
        return returnDetails;
    }

    public void setReturnDetails(String returnDetails) {
        this.returnDetails = returnDetails;
    }

    public String getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(String specialOffer) {
        this.specialOffer = specialOffer;
    }

    public String getOperates() {
        return operates;
    }

    public void setOperates(String operates) {
        this.operates = operates;
    }

    public Object getMapURL() {
        return mapURL;
    }

    public void setMapURL(Object mapURL) {
        this.mapURL = mapURL;
    }

    public Boolean getAllTravellerNamesRequired() {
        return allTravellerNamesRequired;
    }

    public void setAllTravellerNamesRequired(Boolean allTravellerNamesRequired) {
        this.allTravellerNamesRequired = allTravellerNamesRequired;
    }

    public List<String> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<String> exclusions) {
        this.exclusions = exclusions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getTranslationLevel() {
        return translationLevel;
    }

    public void setTranslationLevel(Integer translationLevel) {
        this.translationLevel = translationLevel;
    }

    public Integer getPrimaryDestinationId() {
        return primaryDestinationId;
    }

    public void setPrimaryDestinationId(Integer primaryDestinationId) {
        this.primaryDestinationId = primaryDestinationId;
    }

    public String getPrimaryDestinationName() {
        return primaryDestinationName;
    }

    public void setPrimaryDestinationName(String primaryDestinationName) {
        this.primaryDestinationName = primaryDestinationName;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getPriceFormatted() {
        return priceFormatted;
    }

    public void setPriceFormatted(String priceFormatted) {
        this.priceFormatted = priceFormatted;
    }

    public Float getRrp() {
        return rrp;
    }

    public void setRrp(Float rrp) {
        this.rrp = rrp;
    }

    public String getRrpformatted() {
        return rrpformatted;
    }

    public void setRrpformatted(String rrpformatted) {
        this.rrpformatted = rrpformatted;
    }

    public Boolean getOnSale() {
        return onSale;
    }

    public void setOnSale(Boolean onSale) {
        this.onSale = onSale;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getThumbnailHiResURL() {
        return thumbnailHiResURL;
    }

    public void setThumbnailHiResURL(String thumbnailHiResURL) {
        this.thumbnailHiResURL = thumbnailHiResURL;
    }

    public Integer getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Boolean getSpecialReservation() {
        return specialReservation;
    }

    public void setSpecialReservation(Boolean specialReservation) {
        this.specialReservation = specialReservation;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public Float getMerchantNetPriceFrom() {
        return merchantNetPriceFrom;
    }

    public void setMerchantNetPriceFrom(Float merchantNetPriceFrom) {
        this.merchantNetPriceFrom = merchantNetPriceFrom;
    }

    public Float getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(Float savingAmount) {
        this.savingAmount = savingAmount;
    }

    public String getMerchantNetPriceFromFormatted() {
        return merchantNetPriceFromFormatted;
    }

    public void setMerchantNetPriceFromFormatted(String merchantNetPriceFromFormatted) {
        this.merchantNetPriceFromFormatted = merchantNetPriceFromFormatted;
    }

    public String getSavingAmountFormated() {
        return savingAmountFormated;
    }

    public void setSavingAmountFormated(String savingAmountFormated) {
        this.savingAmountFormated = savingAmountFormated;
    }

    public Boolean getSpecialOfferAvailable() {
        return specialOfferAvailable;
    }

    public void setSpecialOfferAvailable(Boolean specialOfferAvailable) {
        this.specialOfferAvailable = specialOfferAvailable;
    }

    public Object getEssential() {
        return essential;
    }

    public void setEssential(Object essential) {
        this.essential = essential;
    }

    public Object getAdmission() {
        return admission;
    }

    public void setAdmission(Object admission) {
        this.admission = admission;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
