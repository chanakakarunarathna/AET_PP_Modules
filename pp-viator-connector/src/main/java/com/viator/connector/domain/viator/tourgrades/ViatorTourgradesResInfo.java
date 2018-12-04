package com.viator.connector.domain.viator.tourgrades;

import java.util.List;

public class ViatorTourgradesResInfo {
	
    private Integer sortOrder;

    private String bookingDate;

    private List<ViatorTourgradesResAgeBandDetail> ageBands;

    private List<List<ViatorTourgradesResAgeBandsRequired>> ageBandsRequired = null;

    private Object langServices; //TODO

    private String unavailableReason;

    private String gradeCode;

    private String gradeTitle;

    private String gradeDescription;

    private String defaultLanguageCode;

    private String gradeDepartureTime;

    private Boolean available;

    private String currencyCode;

    private Integer retailPrice;

    private String retailPriceFormatted;

    private Integer merchantNetPrice;

    private String merchantNetPriceFormatted;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    
    public List<List<ViatorTourgradesResAgeBandsRequired>> getAgeBandsRequired() {
        return ageBandsRequired;
    }

    public void setAgeBandsRequired(List<List<ViatorTourgradesResAgeBandsRequired>> ageBandsRequired) {
        this.ageBandsRequired = ageBandsRequired;
    }

    public Object getLangServices() {
        return langServices;
    }

    public void setLangServices(Object langServices) {
        this.langServices = langServices;
    }

    public String getUnavailableReason() {
        return unavailableReason;
    }

    public void setUnavailableReason(String unavailableReason) {
        this.unavailableReason = unavailableReason;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    public String getDefaultLanguageCode() {
        return defaultLanguageCode;
    }

    public void setDefaultLanguageCode(String defaultLanguageCode) {
        this.defaultLanguageCode = defaultLanguageCode;
    }

    public String getGradeDepartureTime() {
        return gradeDepartureTime;
    }

    public void setGradeDepartureTime(String gradeDepartureTime) {
        this.gradeDepartureTime = gradeDepartureTime;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getRetailPriceFormatted() {
        return retailPriceFormatted;
    }

    public void setRetailPriceFormatted(String retailPriceFormatted) {
        this.retailPriceFormatted = retailPriceFormatted;
    }

    public Integer getMerchantNetPrice() {
        return merchantNetPrice;
    }

    public void setMerchantNetPrice(Integer merchantNetPrice) {
        this.merchantNetPrice = merchantNetPrice;
    }

    public String getMerchantNetPriceFormatted() {
        return merchantNetPriceFormatted;
    }

    public void setMerchantNetPriceFormatted(String merchantNetPriceFormatted) {
        this.merchantNetPriceFormatted = merchantNetPriceFormatted;
    }

    public List<ViatorTourgradesResAgeBandDetail> getAgeBands() {
        return ageBands;
    }

    public void setAgeBands(List<ViatorTourgradesResAgeBandDetail> ageBands) {
        this.ageBands = ageBands;
    }
}
