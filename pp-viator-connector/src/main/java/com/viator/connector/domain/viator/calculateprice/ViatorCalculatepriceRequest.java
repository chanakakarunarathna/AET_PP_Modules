package com.viator.connector.domain.viator.calculateprice;

import java.util.List;

public class ViatorCalculatepriceRequest {

    private String promoCode;
    private Object partnerDetail;
    private String currencyCode;
    private List<ViatorCalculatepriceReqItem> items = null;

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Object getPartnerDetail() {
        return partnerDetail;
    }

    public void setPartnerDetail(Object partnerDetail) {
        this.partnerDetail = partnerDetail;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<ViatorCalculatepriceReqItem> getItems() {
        return items;
    }

    public void setItems(List<ViatorCalculatepriceReqItem> items) {
        this.items = items;
    }
}
