package com.viator.connector.domain.viator.mybookings;

import com.viator.connector.domain.viator.book.ViatorBookResBookingStatus;

public class ViatorMybookingsResDetails {

    private Integer sortOrder;

    private String rulesApplied;

    private String omniPreRuleList;
    
    private ViatorBookResBookingStatus bookingStatus;

    private String voucherURL;

    private String paypalRedirectURL;

    private String userId;

    private String bookingDate;

    private String distributorRef;

    private String voucherKey;

    private String bookerEmail;

    private boolean hasVoucher;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRulesApplied() {
        return rulesApplied;
    }

    public void setRulesApplied(String rulesApplied) {
        this.rulesApplied = rulesApplied;
    }

    public String getOmniPreRuleList() {
        return omniPreRuleList;
    }

    public void setOmniPreRuleList(String omniPreRuleList) {
        this.omniPreRuleList = omniPreRuleList;
    }

    public String getVoucherURL() {
        return voucherURL;
    }

    public void setVoucherURL(String voucherURL) {
        this.voucherURL = voucherURL;
    }

    public String getPaypalRedirectURL() {
        return paypalRedirectURL;
    }

    public void setPaypalRedirectURL(String paypalRedirectURL) {
        this.paypalRedirectURL = paypalRedirectURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDistributorRef() {
        return distributorRef;
    }

    public void setDistributorRef(String distributorRef) {
        this.distributorRef = distributorRef;
    }

    public String getVoucherKey() {
        return voucherKey;
    }

    public void setVoucherKey(String voucherKey) {
        this.voucherKey = voucherKey;
    }

    public String getBookerEmail() {
        return bookerEmail;
    }

    public void setBookerEmail(String bookerEmail) {
        this.bookerEmail = bookerEmail;
    }

    public boolean isHasVoucher() {
        return hasVoucher;
    }

    public void setHasVoucher(boolean hasVoucher) {
        this.hasVoucher = hasVoucher;
    }

	public ViatorBookResBookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(ViatorBookResBookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

}
