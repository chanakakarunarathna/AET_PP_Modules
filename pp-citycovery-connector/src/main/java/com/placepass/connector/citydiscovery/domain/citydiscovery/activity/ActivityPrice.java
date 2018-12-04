package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityPrice {

    @Field(value = "ID")
    private int id;

    @Field(value = "ActivityPriceDays")
    private ActivityPriceDays activityPriceDays;

    @Field(value = "ActivityPriceOption")
    private String activityPriceOption;

    @Field(value = "ActivityPriceOptionDepartureTime")
    private String activityPriceOptionDepartureTime;

    @Field(value = "ActivityPriceDateBegins")
    private Date activityPriceDateBegins;

    @Field(value = "ActivityPriceDateEnds")
    private Date activityPriceDateEnds;

    @Field(value = "ActivityPriceGroup")
    private String activityPriceGroup;

    @Field(value = "ActivityPriceGroupMinPax")
    private Integer activityPriceGroupMinPax;

    @Field(value = "ActivityPriceGroupMaxPax")
    private Integer activityPriceGroupMaxPax;

    @Field(value = "ActivityPriceAdult")
    private Double activityPriceAdult;

    @Field(value = "ActivityPriceAdultUSD")
    private Double activityPriceAdultUSD;

    @Field(value = "ActivityPriceAdultNet")
    private Double activityPriceAdultNet;

    @Field(value = "ActivityPriceAdultNetUSD")
    private Double activityPriceAdultNetUSD;

    @Field(value = "ActivityPriceAdultBeforePromoCode")
    private Double activityPriceAdultBeforePromoCode;

    @Field(value = "ActivityPriceAdultBeforePromoCodeUSD")
    private Double activityPriceAdultBeforePromoCodeUSD;

    @Field(value = "ActivityPriceChild")
    private Double activityPriceChild;

    @Field(value = "ActivityPriceChildUSD")
    private Double activityPriceChildUSD;

    @Field(value = "ActivityPriceChildNet")
    private Double activityPriceChildNet;

    @Field(value = "ActivityPriceChildNetUSD")
    private Double activityPriceChildNetUSD;

    @Field(value = "ActivityPriceChildBeforePromoCode")
    private Double activityPriceChildBeforePromoCode;

    @Field(value = "ActivityPriceChildBeforePromoCodeUSD")
    private Double activityPriceChildBeforePromoCodeUSD;

    @Field(value = "ActivityPriceCurrency")
    private String activityPriceCurrency;

    @Field(value = "ActivityChildAllowed")
    private String activityChildAllowed;

    @Field(value = "ActivityChildMaxAge")
    private Integer activityChildMaxAge;

    @Field(value = "ActivityInfantAllowed")
    private String activityInfantAllowed;

    @Field(value = "ActivityInfantMaxAge")
    private Integer activityInfantMaxAge;

    @Field(value = "ActivityPriceBeforeDiscount")
    private Double activityPriceBeforeDiscount;

    @Field(value = "ActivityPriceDiscountMessage")
    private String activityPriceDiscountMessage;

    @Field(value = "ActivityPromoCodeMessage")
    private String activityPromoCodeMessage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActivityPriceDays getActivityPriceDays() {
        return activityPriceDays;
    }

    public void setActivityPriceDays(ActivityPriceDays activityPriceDays) {
        this.activityPriceDays = activityPriceDays;
    }

    public String getActivityPriceOption() {
        return activityPriceOption;
    }

    public void setActivityPriceOption(String activityPriceOption) {
        this.activityPriceOption = activityPriceOption;
    }

    public String getActivityPriceOptionDepartureTime() {
        return activityPriceOptionDepartureTime;
    }

    public void setActivityPriceOptionDepartureTime(String activityPriceOptionDepartureTime) {
        this.activityPriceOptionDepartureTime = activityPriceOptionDepartureTime;
    }

    public Date getActivityPriceDateBegins() {
        return activityPriceDateBegins;
    }

    public void setActivityPriceDateBegins(Date activityPriceDateBegins) {
        this.activityPriceDateBegins = activityPriceDateBegins;
    }

    public Date getActivityPriceDateEnds() {
        return activityPriceDateEnds;
    }

    public void setActivityPriceDateEnds(Date activityPriceDateEnds) {
        this.activityPriceDateEnds = activityPriceDateEnds;
    }

    public String getActivityPriceGroup() {
        return activityPriceGroup;
    }

    public void setActivityPriceGroup(String activityPriceGroup) {
        this.activityPriceGroup = activityPriceGroup;
    }

    public Integer getActivityPriceGroupMinPax() {
        return activityPriceGroupMinPax;
    }

    public void setActivityPriceGroupMinPax(Integer activityPriceGroupMinPax) {
        this.activityPriceGroupMinPax = activityPriceGroupMinPax;
    }

    public Integer getActivityPriceGroupMaxPax() {
        return activityPriceGroupMaxPax;
    }

    public void setActivityPriceGroupMaxPax(Integer activityPriceGroupMaxPax) {
        this.activityPriceGroupMaxPax = activityPriceGroupMaxPax;
    }

    public Double getActivityPriceAdult() {
        return activityPriceAdult;
    }

    public void setActivityPriceAdult(Double activityPriceAdult) {
        this.activityPriceAdult = activityPriceAdult;
    }

    public Double getActivityPriceAdultUSD() {
        return activityPriceAdultUSD;
    }

    public void setActivityPriceAdultUSD(Double activityPriceAdultUSD) {
        this.activityPriceAdultUSD = activityPriceAdultUSD;
    }

    public Double getActivityPriceAdultNet() {
        return activityPriceAdultNet;
    }

    public void setActivityPriceAdultNet(Double activityPriceAdultNet) {
        this.activityPriceAdultNet = activityPriceAdultNet;
    }

    public Double getActivityPriceAdultNetUSD() {
        return activityPriceAdultNetUSD;
    }

    public void setActivityPriceAdultNetUSD(Double activityPriceAdultNetUSD) {
        this.activityPriceAdultNetUSD = activityPriceAdultNetUSD;
    }

    public Double getActivityPriceAdultBeforePromoCode() {
        return activityPriceAdultBeforePromoCode;
    }

    public void setActivityPriceAdultBeforePromoCode(Double activityPriceAdultBeforePromoCode) {
        this.activityPriceAdultBeforePromoCode = activityPriceAdultBeforePromoCode;
    }

    public Double getActivityPriceAdultBeforePromoCodeUSD() {
        return activityPriceAdultBeforePromoCodeUSD;
    }

    public void setActivityPriceAdultBeforePromoCodeUSD(Double activityPriceAdultBeforePromoCodeUSD) {
        this.activityPriceAdultBeforePromoCodeUSD = activityPriceAdultBeforePromoCodeUSD;
    }

    public Double getActivityPriceChild() {
        return activityPriceChild;
    }

    public void setActivityPriceChild(Double activityPriceChild) {
        this.activityPriceChild = activityPriceChild;
    }

    public Double getActivityPriceChildUSD() {
        return activityPriceChildUSD;
    }

    public void setActivityPriceChildUSD(Double activityPriceChildUSD) {
        this.activityPriceChildUSD = activityPriceChildUSD;
    }

    public Double getActivityPriceChildNet() {
        return activityPriceChildNet;
    }

    public void setActivityPriceChildNet(Double activityPriceChildNet) {
        this.activityPriceChildNet = activityPriceChildNet;
    }

    public Double getActivityPriceChildNetUSD() {
        return activityPriceChildNetUSD;
    }

    public void setActivityPriceChildNetUSD(Double activityPriceChildNetUSD) {
        this.activityPriceChildNetUSD = activityPriceChildNetUSD;
    }

    public Double getActivityPriceChildBeforePromoCode() {
        return activityPriceChildBeforePromoCode;
    }

    public void setActivityPriceChildBeforePromoCode(Double activityPriceChildBeforePromoCode) {
        this.activityPriceChildBeforePromoCode = activityPriceChildBeforePromoCode;
    }

    public Double getActivityPriceChildBeforePromoCodeUSD() {
        return activityPriceChildBeforePromoCodeUSD;
    }

    public void setActivityPriceChildBeforePromoCodeUSD(Double activityPriceChildBeforePromoCodeUSD) {
        this.activityPriceChildBeforePromoCodeUSD = activityPriceChildBeforePromoCodeUSD;
    }

    public String getActivityPriceCurrency() {
        return activityPriceCurrency;
    }

    public void setActivityPriceCurrency(String activityPriceCurrency) {
        this.activityPriceCurrency = activityPriceCurrency;
    }

    public String getActivityChildAllowed() {
        return activityChildAllowed;
    }

    public void setActivityChildAllowed(String activityChildAllowed) {
        this.activityChildAllowed = activityChildAllowed;
    }

    public Integer getActivityChildMaxAge() {
        return activityChildMaxAge;
    }

    public void setActivityChildMaxAge(Integer activityChildMaxAge) {
        this.activityChildMaxAge = activityChildMaxAge;
    }

    public String getActivityInfantAllowed() {
        return activityInfantAllowed;
    }

    public void setActivityInfantAllowed(String activityInfantAllowed) {
        this.activityInfantAllowed = activityInfantAllowed;
    }

    public Integer getActivityInfantMaxAge() {
        return activityInfantMaxAge;
    }

    public void setActivityInfantMaxAge(Integer activityInfantMaxAge) {
        this.activityInfantMaxAge = activityInfantMaxAge;
    }

    public Double getActivityPriceBeforeDiscount() {
        return activityPriceBeforeDiscount;
    }

    public void setActivityPriceBeforeDiscount(Double activityPriceBeforeDiscount) {
        this.activityPriceBeforeDiscount = activityPriceBeforeDiscount;
    }

    public String getActivityPriceDiscountMessage() {
        return activityPriceDiscountMessage;
    }

    public void setActivityPriceDiscountMessage(String activityPriceDiscountMessage) {
        this.activityPriceDiscountMessage = activityPriceDiscountMessage;
    }

    public String getActivityPromoCodeMessage() {
        return activityPromoCodeMessage;
    }

    public void setActivityPromoCodeMessage(String activityPromoCodeMessage) {
        this.activityPromoCodeMessage = activityPromoCodeMessage;
    }

}
