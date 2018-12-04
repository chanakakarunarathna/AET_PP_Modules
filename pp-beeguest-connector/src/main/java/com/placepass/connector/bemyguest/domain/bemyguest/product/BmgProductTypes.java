package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BmgProductTypes {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("titleTranslated")
    private String titleTranslated;

    @JsonProperty("description")
    private String description;

    @JsonProperty("descriptionTranslated")
    private String descriptionTranslated;

    @JsonProperty("durationDays")
    private int durationDays;

    @JsonProperty("durationHours")
    private int durationHours;

    @JsonProperty("durationMinutes")
    private int durationMinutes;

    @JsonProperty("paxMin")
    private int paxMin;

    @JsonProperty("paxMax")
    private int paxMax;

    @JsonProperty("daysInAdvance")
    private int daysInAdvance;

    @JsonProperty("isNonRefundable")
    private boolean isNonRefundable;

    @JsonProperty("hasChildPrice")
    private boolean hasChildPrice;

    @JsonProperty("minAdultAge")
    private int minAdultAge;

    @JsonProperty("maxAdultAge")
    private int maxAdultAge;

    @JsonProperty("allowChildren")
    private boolean allowChildren;

    @JsonProperty("minChildAge")
    private int minChildAge;

    @JsonProperty("maxChildAge")
    private int maxChildAge;

    @JsonProperty("allowInfant")
    private boolean allowInfant;

    @JsonProperty("minInfantAge")
    private int minInfantAge;

    @JsonProperty("maxInfantAge")
    private int maxInfantAge;

    @JsonProperty("instantConfirmation")
    private boolean instantConfirmation;

    @JsonProperty("nonInstantVoucher")
    private boolean nonInstantVoucher;

    @JsonProperty("directAdmission")
    private boolean directAdmission;

    @JsonProperty("voucherUse")
    private String voucherUse;

    @JsonProperty("voucherUseTranslated")
    private String voucherUseTranslated;

    @JsonProperty("voucherRedemptionAddress")
    private String voucherRedemptionAddress;

    @JsonProperty("voucherRedemptionAddressTranslated")
    private String voucherRedemptionAddressTranslated;

    @JsonProperty("voucherRequiresPrinting")
    private boolean voucherRequiresPrinting;

    @JsonProperty("cancellationPolicies")
    private List<BmgCancellationPolicies> cancellationPolicies;

    @JsonProperty("recommendedMarkup")
    private float recommendedMarkup;

    @JsonProperty("adultParityPrice")
    private float adultParityPrice;

    @JsonProperty("childParityPrice")
    private float childParityPrice;

    @JsonProperty("validity")
    private BmgValidity validity;

    @JsonProperty("prices")
    private Map<String, BmgPricesPerDate> prices;

    @JsonProperty("timeSlots")
    private List<BmgTimeSlot> timeSlots;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getPaxMin() {
        return paxMin;
    }

    public void setPaxMin(int paxMin) {
        this.paxMin = paxMin;
    }

    public int getPaxMax() {
        return paxMax;
    }

    public void setPaxMax(int paxMax) {
        this.paxMax = paxMax;
    }

    public int getDaysInAdvance() {
        return daysInAdvance;
    }

    public void setDaysInAdvance(int daysInAdvance) {
        this.daysInAdvance = daysInAdvance;
    }

    public boolean isNonRefundable() {
        return isNonRefundable;
    }

    public void setNonRefundable(boolean isNonRefundable) {
        this.isNonRefundable = isNonRefundable;
    }

    public boolean isHasChildPrice() {
        return hasChildPrice;
    }

    public void setHasChildPrice(boolean hasChildPrice) {
        this.hasChildPrice = hasChildPrice;
    }

    public int getMinAdultAge() {
        return minAdultAge;
    }

    public void setMinAdultAge(int minAdultAge) {
        this.minAdultAge = minAdultAge;
    }

    public int getMaxAdultAge() {
        return maxAdultAge;
    }

    public void setMaxAdultAge(int maxAdultAge) {
        this.maxAdultAge = maxAdultAge;
    }

    public boolean isAllowChildren() {
        return allowChildren;
    }

    public void setAllowChildren(boolean allowChildren) {
        this.allowChildren = allowChildren;
    }

    public int getMinChildAge() {
        return minChildAge;
    }

    public void setMinChildAge(int minChildAge) {
        this.minChildAge = minChildAge;
    }

    public int getMaxChildAge() {
        return maxChildAge;
    }

    public void setMaxChildAge(int maxChildAge) {
        this.maxChildAge = maxChildAge;
    }

    public boolean isAllowInfant() {
        return allowInfant;
    }

    public void setAllowInfant(boolean allowInfant) {
        this.allowInfant = allowInfant;
    }

    public int getMinInfantAge() {
        return minInfantAge;
    }

    public void setMinInfantAge(int minInfantAge) {
        this.minInfantAge = minInfantAge;
    }

    public int getMaxInfantAge() {
        return maxInfantAge;
    }

    public void setMaxInfantAge(int maxInfantAge) {
        this.maxInfantAge = maxInfantAge;
    }

    public boolean isInstantConfirmation() {
        return instantConfirmation;
    }

    public void setInstantConfirmation(boolean instantConfirmation) {
        this.instantConfirmation = instantConfirmation;
    }

    public boolean isNonInstantVoucher() {
        return nonInstantVoucher;
    }

    public void setNonInstantVoucher(boolean nonInstantVoucher) {
        this.nonInstantVoucher = nonInstantVoucher;
    }

    public boolean isDirectAdmission() {
        return directAdmission;
    }

    public void setDirectAdmission(boolean directAdmission) {
        this.directAdmission = directAdmission;
    }

    public String getVoucherUse() {
        return voucherUse;
    }

    public void setVoucherUse(String voucherUse) {
        this.voucherUse = voucherUse;
    }

    public String getVoucherUseTranslated() {
        return voucherUseTranslated;
    }

    public void setVoucherUseTranslated(String voucherUseTranslated) {
        this.voucherUseTranslated = voucherUseTranslated;
    }

    public String getVoucherRedemptionAddress() {
        return voucherRedemptionAddress;
    }

    public void setVoucherRedemptionAddress(String voucherRedemptionAddress) {
        this.voucherRedemptionAddress = voucherRedemptionAddress;
    }

    public String getVoucherRedemptionAddressTranslated() {
        return voucherRedemptionAddressTranslated;
    }

    public void setVoucherRedemptionAddressTranslated(String voucherRedemptionAddressTranslated) {
        this.voucherRedemptionAddressTranslated = voucherRedemptionAddressTranslated;
    }

    public boolean isVoucherRequiresPrinting() {
        return voucherRequiresPrinting;
    }

    public void setVoucherRequiresPrinting(boolean voucherRequiresPrinting) {
        this.voucherRequiresPrinting = voucherRequiresPrinting;
    }

    public List<BmgCancellationPolicies> getCancellationPolicies() {
        return cancellationPolicies;
    }

    public void setCancellationPolicies(List<BmgCancellationPolicies> cancellationPolicies) {
        this.cancellationPolicies = cancellationPolicies;
    }

    public float getRecommendedMarkup() {
        return recommendedMarkup;
    }

    public void setRecommendedMarkup(float recommendedMarkup) {
        this.recommendedMarkup = recommendedMarkup;
    }

    public float getAdultParityPrice() {
        return adultParityPrice;
    }

    public void setAdultParityPrice(float adultParityPrice) {
        this.adultParityPrice = adultParityPrice;
    }

    public float getChildParityPrice() {
        return childParityPrice;
    }

    public void setChildParityPrice(float childParityPrice) {
        this.childParityPrice = childParityPrice;
    }

    public BmgValidity getValidity() {
        return validity;
    }

    public void setValidity(BmgValidity validity) {
        this.validity = validity;
    }

    public Map<String, BmgPricesPerDate> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, BmgPricesPerDate> prices) {
        this.prices = prices;
    }

    public List<BmgTimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<BmgTimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

}
