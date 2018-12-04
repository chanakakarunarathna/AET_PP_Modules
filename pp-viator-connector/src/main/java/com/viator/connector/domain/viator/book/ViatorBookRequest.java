
package com.viator.connector.domain.viator.book;

import java.util.List;

public class ViatorBookRequest {

    private String sessionId;

    private String ipAddress;

    private Object aid;

    private Boolean newsletterSignUp;

    private Boolean demo;

    private Object promoCode;

    private String currencyCode;

    private Object otherDetail;

    private ViatorBookReqPartnerDetail partnerDetail;

    private ViatorBookReqBooker booker;

    private List<ViatorBookReqItem> items = null;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Object getAid() {
        return aid;
    }

    public void setAid(Object aid) {
        this.aid = aid;
    }

    public Boolean getNewsletterSignUp() {
        return newsletterSignUp;
    }

    public void setNewsletterSignUp(Boolean newsletterSignUp) {
        this.newsletterSignUp = newsletterSignUp;
    }

    public Boolean getDemo() {
        return demo;
    }

    public void setDemo(Boolean demo) {
        this.demo = demo;
    }

    public Object getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(Object promoCode) {
        this.promoCode = promoCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Object getOtherDetail() {
        return otherDetail;
    }

    public void setOtherDetail(Object otherDetail) {
        this.otherDetail = otherDetail;
    }

    public ViatorBookReqPartnerDetail getPartnerDetail() {
        return partnerDetail;
    }

    public void setPartnerDetail(ViatorBookReqPartnerDetail partnerDetail) {
        this.partnerDetail = partnerDetail;
    }

    public ViatorBookReqBooker getBooker() {
        return booker;
    }

    public void setBooker(ViatorBookReqBooker booker) {
        this.booker = booker;
    }

    public List<ViatorBookReqItem> getItems() {
        return items;
    }

    public void setItems(List<ViatorBookReqItem> items) {
        this.items = items;
    }

}
