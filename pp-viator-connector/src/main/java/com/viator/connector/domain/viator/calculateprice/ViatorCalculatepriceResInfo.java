package com.viator.connector.domain.viator.calculateprice;

public class ViatorCalculatepriceResInfo {

    private Object rulesApplied;

    private Object promoCode;

    private ViatorCalculatepriceResItinerary itinerary;

    private String currencyCode;

    private Boolean promoCodeValid;

    private Boolean promoCodeExpired;

    private Double itineraryFromPrice;

    private String itineraryFromPriceFormatted;

    private Double itineraryNewPrice;

    private String itineraryNewPriceFormatted;

    private Integer itinerarySaving;

    private Boolean hasPromoCode;

    private String itinerarySavingFormatted;

    public Object getRulesApplied() {
        return rulesApplied;
    }

    public void setRulesApplied(Object rulesApplied) {
        this.rulesApplied = rulesApplied;
    }

    public Object getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(Object promoCode) {
        this.promoCode = promoCode;
    }

    public ViatorCalculatepriceResItinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(ViatorCalculatepriceResItinerary itinerary) {
        this.itinerary = itinerary;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean getPromoCodeValid() {
        return promoCodeValid;
    }

    public void setPromoCodeValid(Boolean promoCodeValid) {
        this.promoCodeValid = promoCodeValid;
    }

    public Boolean getPromoCodeExpired() {
        return promoCodeExpired;
    }

    public void setPromoCodeExpired(Boolean promoCodeExpired) {
        this.promoCodeExpired = promoCodeExpired;
    }

    public Double getItineraryFromPrice() {
        return itineraryFromPrice;
    }

    public void setItineraryFromPrice(Double itineraryFromPrice) {
        this.itineraryFromPrice = itineraryFromPrice;
    }

    public String getItineraryFromPriceFormatted() {
        return itineraryFromPriceFormatted;
    }

    public void setItineraryFromPriceFormatted(String itineraryFromPriceFormatted) {
        this.itineraryFromPriceFormatted = itineraryFromPriceFormatted;
    }

    public Double getItineraryNewPrice() {
        return itineraryNewPrice;
    }

    public void setItineraryNewPrice(Double itineraryNewPrice) {
        this.itineraryNewPrice = itineraryNewPrice;
    }

    public String getItineraryNewPriceFormatted() {
        return itineraryNewPriceFormatted;
    }

    public void setItineraryNewPriceFormatted(String itineraryNewPriceFormatted) {
        this.itineraryNewPriceFormatted = itineraryNewPriceFormatted;
    }

    public Integer getItinerarySaving() {
        return itinerarySaving;
    }

    public void setItinerarySaving(Integer itinerarySaving) {
        this.itinerarySaving = itinerarySaving;
    }

    public Boolean getHasPromoCode() {
        return hasPromoCode;
    }

    public void setHasPromoCode(Boolean hasPromoCode) {
        this.hasPromoCode = hasPromoCode;
    }

    public String getItinerarySavingFormatted() {
        return itinerarySavingFormatted;
    }

    public void setItinerarySavingFormatted(String itinerarySavingFormatted) {
        this.itinerarySavingFormatted = itinerarySavingFormatted;
    }
}
