package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgPromotionPrice {

    @JsonProperty("type")
    private String type;

    @JsonProperty("daysInAdvance")
    private Integer daysInAdvance;

    @JsonProperty("booking_dates")
    private String booking_dates;

    @JsonProperty("hoursInAdvance")
    private Integer hoursInAdvance;

    @JsonProperty("name")
    private String name;

    @JsonProperty("translate_name")
    private String translate_name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("translate_description")
    private String translate_description;

    @JsonProperty("adult")
    private HashMap<Integer, Double> adult;

    @JsonProperty("child")
    private double child;

    @JsonProperty("children")
    private HashMap<Integer, Double> children;

    @JsonProperty("discountPercent")
    private int discountPercent;

    @JsonProperty("CancellationPolicy")
    private List<BmgCancellationPolicies> cancellationPolicy;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDaysInAdvance() {
        return daysInAdvance;
    }

    public void setDaysInAdvance(Integer daysInAdvance) {
        this.daysInAdvance = daysInAdvance;
    }

    public String getBooking_dates() {
        return booking_dates;
    }

    public void setBooking_dates(String booking_dates) {
        this.booking_dates = booking_dates;
    }

    public Integer getHoursInAdvance() {
        return hoursInAdvance;
    }

    public void setHoursInAdvance(Integer hoursInAdvance) {
        this.hoursInAdvance = hoursInAdvance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslate_name() {
        return translate_name;
    }

    public void setTranslate_name(String translate_name) {
        this.translate_name = translate_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTranslate_description() {
        return translate_description;
    }

    public void setTranslate_description(String translate_description) {
        this.translate_description = translate_description;
    }

    public HashMap<Integer, Double> getAdult() {
        return adult;
    }

    public void setAdult(HashMap<Integer, Double> adult) {
        this.adult = adult;
    }

    public double getChild() {
        return child;
    }

    public void setChild(double child) {
        this.child = child;
    }

    public HashMap<Integer, Double> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Integer, Double> children) {
        this.children = children;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public List<BmgCancellationPolicies> getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(List<BmgCancellationPolicies> cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

}
