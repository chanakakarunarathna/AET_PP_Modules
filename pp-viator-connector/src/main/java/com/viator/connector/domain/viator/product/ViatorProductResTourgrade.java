package com.viator.connector.domain.viator.product;

public class ViatorProductResTourgrade {
    private Integer sortOrder;

    private String currencyCode;

    private ViatorProductResTourGradeLangservices langServices;

    private String priceFromFormatted;

    private String gradeCode;

    private Float priceFrom;

    private Float merchantNetPriceFrom;

    private String merchantNetPriceFromFormatted;

    private String gradeTitle;

    private String gradeDepartureTime;

    private String gradeDescription;

    private String defaultLanguageCode;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ViatorProductResTourGradeLangservices getLangServices() {
        return langServices;
    }

    public void setLangServices(ViatorProductResTourGradeLangservices langServices) {
        this.langServices = langServices;
    }

    public String getPriceFromFormatted() {
        return priceFromFormatted;
    }

    public void setPriceFromFormatted(String priceFromFormatted) {
        this.priceFromFormatted = priceFromFormatted;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Float getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Float priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Float getMerchantNetPriceFrom() {
        return merchantNetPriceFrom;
    }

    public void setMerchantNetPriceFrom(Float merchantNetPriceFrom) {
        this.merchantNetPriceFrom = merchantNetPriceFrom;
    }

    public String getMerchantNetPriceFromFormatted() {
        return merchantNetPriceFromFormatted;
    }

    public void setMerchantNetPriceFromFormatted(String merchantNetPriceFromFormatted) {
        this.merchantNetPriceFromFormatted = merchantNetPriceFromFormatted;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public String getGradeDepartureTime() {
        return gradeDepartureTime;
    }

    public void setGradeDepartureTime(String gradeDepartureTime) {
        this.gradeDepartureTime = gradeDepartureTime;
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

}
