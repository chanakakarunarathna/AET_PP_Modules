package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "details")
public class ActivityDisplay {

    @Id
    private String id;

    @Field(value = "ActivityID")
    private int activityID;

    @Field(value = "ActivitySupplier")
    private String activitySupplier;

    @Field(value = "ActivityCountry")
    private String activityCountry;

    @Field(value = "ActivityCity")
    private String activityCity;

    @Field(value = "ActivityRegion")
    private String activityRegion;

    @Field(value = "ActivityReviewRating")
    private Integer activityReviewRating;

    @Field(value = "ActivityHighlights")
    private String activityHighlights;

    @Field(value = "ActivityName")
    private String activityName;

    @Field(value = "ActivityAvailabilityType")
    private String activityAvailabilityType;

    @Field(value = "ActivityReleaseDate")
    private Integer activityReleaseDate;

    @Field(value = "ActivityImageGallery")
    private List<ActivityImageGallery> activityImageGallery;

    @Field(value = "ActivityStartingPlace")
    private ActivityStartingPlace activityStartingPlace;

    @Field(value = "ActivityDescription")
    private String activityDescription;

    @Field(value = "ActivityLanguages")
    private String activityLanguages;

    @Field(value = "ActivityContentLanguages")
    private String activityContentLanguages;

    @Field(value = "ActivityThemes")
    private ActivityThemes activityThemes;

    @Field(value = "ActivityCategories")
    private ActivityCategories activityCategories;

    @Field(value = "ActivityDateAdded")
    private Date activityDateAdded;

    @Field(value = "ActivityDateModified")
    private Date activityDateModified;

    @Field(value = "ActivityDuration")
    private Integer activityDuration;

    @Field(value = "ActivityDurationText")
    private String activityDurationText;

    @Field(value = "ActivityBlockOutdates")
    private String activityBlockOutdates;

    @Field(value = "ActivityCancellation")
    private List<ActivityCancellation> activityCancellation;

    @Field(value = "ActivityTermsConditions")
    private String activityTermsConditions;

    @Field(value = "ActivityPrices")
    private List<ActivityPrice> activityPrices;

    @Field(value = "UpdateOn")
    private String updateOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public String getActivitySupplier() {
        return activitySupplier;
    }

    public void setActivitySupplier(String activitySupplier) {
        this.activitySupplier = activitySupplier;
    }

    public String getActivityCountry() {
        return activityCountry;
    }

    public void setActivityCountry(String activityCountry) {
        this.activityCountry = activityCountry;
    }

    public String getActivityCity() {
        return activityCity;
    }

    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
    }

    public String getActivityRegion() {
        return activityRegion;
    }

    public void setActivityRegion(String activityRegion) {
        this.activityRegion = activityRegion;
    }

    public Integer getActivityReviewRating() {
        return activityReviewRating;
    }

    public void setActivityReviewRating(Integer activityReviewRating) {
        this.activityReviewRating = activityReviewRating;
    }

    public String getActivityHighlights() {
        return activityHighlights;
    }

    public void setActivityHighlights(String activityHighlights) {
        this.activityHighlights = activityHighlights;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityAvailabilityType() {
        return activityAvailabilityType;
    }

    public void setActivityAvailabilityType(String activityAvailabilityType) {
        this.activityAvailabilityType = activityAvailabilityType;
    }

    public Integer getActivityReleaseDate() {
        return activityReleaseDate;
    }

    public void setActivityReleaseDate(Integer activityReleaseDate) {
        this.activityReleaseDate = activityReleaseDate;
    }

    public List<ActivityImageGallery> getActivityImageGallery() {
        return activityImageGallery;
    }

    public void setActivityImageGallery(List<ActivityImageGallery> activityImageGallery) {
        this.activityImageGallery = activityImageGallery;
    }

    public ActivityStartingPlace getActivityStartingPlace() {
        return activityStartingPlace;
    }

    public void setActivityStartingPlace(ActivityStartingPlace activityStartingPlace) {
        this.activityStartingPlace = activityStartingPlace;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityLanguages() {
        return activityLanguages;
    }

    public void setActivityLanguages(String activityLanguages) {
        this.activityLanguages = activityLanguages;
    }

    public String getActivityContentLanguages() {
        return activityContentLanguages;
    }

    public void setActivityContentLanguages(String activityContentLanguages) {
        this.activityContentLanguages = activityContentLanguages;
    }

    public ActivityThemes getActivityThemes() {
        return activityThemes;
    }

    public void setActivityThemes(ActivityThemes activityThemes) {
        this.activityThemes = activityThemes;
    }

    public ActivityCategories getActivityCategories() {
        return activityCategories;
    }

    public void setActivityCategories(ActivityCategories activityCategories) {
        this.activityCategories = activityCategories;
    }

    public Date getActivityDateAdded() {
        return activityDateAdded;
    }

    public void setActivityDateAdded(Date activityDateAdded) {
        this.activityDateAdded = activityDateAdded;
    }

    public Date getActivityDateModified() {
        return activityDateModified;
    }

    public void setActivityDateModified(Date activityDateModified) {
        this.activityDateModified = activityDateModified;
    }

    public Integer getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
    }

    public String getActivityDurationText() {
        return activityDurationText;
    }

    public void setActivityDurationText(String activityDurationText) {
        this.activityDurationText = activityDurationText;
    }

    public String getActivityBlockOutdates() {
        return activityBlockOutdates;
    }

    public void setActivityBlockOutdates(String activityBlockOutdates) {
        this.activityBlockOutdates = activityBlockOutdates;
    }

    public List<ActivityCancellation> getActivityCancellation() {
        return activityCancellation;
    }

    public void setActivityCancellation(List<ActivityCancellation> activityCancellation) {
        this.activityCancellation = activityCancellation;
    }

    public List<ActivityPrice> getActivityPrices() {
        return activityPrices;
    }

    public void setActivityPrices(List<ActivityPrice> activityPrices) {
        this.activityPrices = activityPrices;
    }

    public String getUpdateOn() {
        return updateOn;
    }

    public String getActivityTermsConditions() {
        return activityTermsConditions;
    }

    public void setActivityTermsConditions(String activityTermsConditions) {
        this.activityTermsConditions = activityTermsConditions;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = updateOn;
    }

}
