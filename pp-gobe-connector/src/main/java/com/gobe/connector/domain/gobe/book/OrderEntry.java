package com.gobe.connector.domain.gobe.book;

/**
 * Created on 8/7/2017.
 */
public class OrderEntry {

    private String productId;

    private int quantity;

    private Float basePrice;

    private Float totalPrice;

    private String unit;

    private String restrictionsChecked;

    private String restrictionsCheckedTime;

    private String needGuide;

    private String tourSchedule;

    private String startTime;

    private String dropOfTime;

    private String pickupLocation;

    private Passport passport;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRestrictionsChecked() {
        return restrictionsChecked;
    }

    public void setRestrictionsChecked(String restrictionsChecked) {
        this.restrictionsChecked = restrictionsChecked;
    }

    public String getRestrictionsCheckedTime() {
        return restrictionsCheckedTime;
    }

    public void setRestrictionsCheckedTime(String restrictionsCheckedTime) {
        this.restrictionsCheckedTime = restrictionsCheckedTime;
    }

    public String getNeedGuide() {
        return needGuide;
    }

    public void setNeedGuide(String needGuide) {
        this.needGuide = needGuide;
    }

    public String getTourSchedule() {
        return tourSchedule;
    }

    public void setTourSchedule(String tourSchedule) {
        this.tourSchedule = tourSchedule;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDropOfTime() {
        return dropOfTime;
    }

    public void setDropOfTime(String dropOfTime) {
        this.dropOfTime = dropOfTime;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
