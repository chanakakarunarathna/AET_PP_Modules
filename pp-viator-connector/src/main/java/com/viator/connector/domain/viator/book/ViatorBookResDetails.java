package com.viator.connector.domain.viator.book;

import java.util.ArrayList;
import java.util.List;

public class ViatorBookResDetails {
    private Integer sortOrder;

    private Object rulesApplied;

    private Object omniPreRuleList;

    private ViatorBookResBookingStatus bookingStatus;

    private List<ViatorBookResItemSummary> itemSummaries = null;

    private String voucherURL;

    private Object paypalRedirectURL;

    private String totalPriceFormatted;

    private Double totalPriceUSD;

    private Boolean hasVoucher;

    private Integer itineraryId;

    private Integer exchangeRate;

    private String bookingDate;

    private String bookerEmail;

    private String voucherKey;

    private String distributorRef;

    private Double totalPrice;

    private Object userId;

    private String currencyCode;

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

    public ViatorBookResBookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(ViatorBookResBookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public List<ViatorBookResItemSummary> getItemSummaries() {
        if (itemSummaries == null)
            itemSummaries = new ArrayList<ViatorBookResItemSummary>();
        return itemSummaries;
    }

    public void setItemSummaries(List<ViatorBookResItemSummary> itemSummaries) {
        this.itemSummaries = itemSummaries;
    }

    public String getVoucherURL() {
        return voucherURL;
    }

    public void setVoucherURL(String voucherURL) {
        this.voucherURL = voucherURL;
    }

    public Object getPaypalRedirectURL() {
        return paypalRedirectURL;
    }

    public void setPaypalRedirectURL(Object paypalRedirectURL) {
        this.paypalRedirectURL = paypalRedirectURL;
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

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public Integer getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public String getVoucherKey() {
        return voucherKey;
    }

    public void setVoucherKey(String voucherKey) {
        this.voucherKey = voucherKey;
    }

    public String getDistributorRef() {
        return distributorRef;
    }

    public void setDistributorRef(String distributorRef) {
        this.distributorRef = distributorRef;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
