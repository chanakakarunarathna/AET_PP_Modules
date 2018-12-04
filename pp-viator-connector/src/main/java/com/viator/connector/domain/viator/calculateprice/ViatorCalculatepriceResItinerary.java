package com.viator.connector.domain.viator.calculateprice;

import java.util.List;

public class ViatorCalculatepriceResItinerary {
    private Integer sortOrder;

    private Object rulesApplied;

    private Object omniPreRuleList;

    private ViatorCalculatepriceResBookingStatus bookingStatus;

    private List<ViatorCalculatepriceResItemSummary> itemSummaries = null;

    private Object voucherURL;

    private Object paypalRedirectURL;

    private String currencyCode;

    private Integer exchangeRate;

    private Object userId;

    private Integer itineraryId;

    private String voucherKey;

    private String bookingDate;

    private Object bookerEmail;

    private Object distributorRef;

    private Double totalPrice;

    private String totalPriceFormatted;

    private Double totalPriceUSD;

    private Boolean hasVoucher;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Object getRulesApplied() {
        return rulesApplied;
    }

    public void setRulesApplied(Object rulesApplied) {
        this.rulesApplied = rulesApplied;
    }

    public Object getOmniPreRuleList() {
        return omniPreRuleList;
    }

    public void setOmniPreRuleList(Object omniPreRuleList) {
        this.omniPreRuleList = omniPreRuleList;
    }

    public ViatorCalculatepriceResBookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(ViatorCalculatepriceResBookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public List<ViatorCalculatepriceResItemSummary> getItemSummaries() {
        return itemSummaries;
    }

    public void setItemSummaries(List<ViatorCalculatepriceResItemSummary> itemSummaries) {
        this.itemSummaries = itemSummaries;
    }

    public Object getVoucherURL() {
        return voucherURL;
    }

    public void setVoucherURL(Object voucherURL) {
        this.voucherURL = voucherURL;
    }

    public Object getPaypalRedirectURL() {
        return paypalRedirectURL;
    }

    public void setPaypalRedirectURL(Object paypalRedirectURL) {
        this.paypalRedirectURL = paypalRedirectURL;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getVoucherKey() {
        return voucherKey;
    }

    public void setVoucherKey(String voucherKey) {
        this.voucherKey = voucherKey;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Object getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(Object bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public Object getDistributorRef() {
        return distributorRef;
    }

    public void setDistributorRef(Object distributorRef) {
        this.distributorRef = distributorRef;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPriceFormatted() {
        return totalPriceFormatted;
    }

    public void setTotalPriceFormatted(String totalPriceFormatted) {
        this.totalPriceFormatted = totalPriceFormatted;
    }

    public Double getTotalPriceUSD() {
        return totalPriceUSD;
    }

    public void setTotalPriceUSD(Double totalPriceUSD) {
        this.totalPriceUSD = totalPriceUSD;
    }

    public Boolean getHasVoucher() {
        return hasVoucher;
    }

    public void setHasVoucher(Boolean hasVoucher) {
        this.hasVoucher = hasVoucher;
    }

}
