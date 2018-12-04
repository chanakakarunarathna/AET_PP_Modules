package com.placepass.connector.bemyguest.domain.bemyguest.bookingcheck;

import java.util.List;

public class BeMyGuestCheckBookingRQ {

    private String productTypeUuid;

    private int pax;

    private int children;

    private String timeSlotUuid;

    private List<BeMyGuestAddOn> addons;

    private String currencyCode;

    private String arrivalDate;

    private boolean usePromotion;

    public String getProductTypeUuid() {
        return productTypeUuid;
    }

    public void setProductTypeUuid(String productTypeUuid) {
        this.productTypeUuid = productTypeUuid;
    }

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getTimeSlotUuid() {
        return timeSlotUuid;
    }

    public void setTimeSlotUuid(String timeSlotUuid) {
        this.timeSlotUuid = timeSlotUuid;
    }

    public List<BeMyGuestAddOn> getAddons() {
        return addons;
    }

    public void setAddons(List<BeMyGuestAddOn> addons) {
        this.addons = addons;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public boolean getUsePromotion() {
        return usePromotion;
    }

    public void setUsePromotion(boolean usePromotion) {
        this.usePromotion = usePromotion;
    }
}
