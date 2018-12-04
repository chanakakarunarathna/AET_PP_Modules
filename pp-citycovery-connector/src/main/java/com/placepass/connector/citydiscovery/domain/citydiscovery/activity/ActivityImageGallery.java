package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityImageGallery {

    @Field(value = "ID")
    private int id;

    @Field(value = "ActivityImgType")
    private String activityImgType;

    @Field(value = "ActivityImgThumb")
    private String activityImgThumb;

    @Field(value = "ActivityImgMain")
    private String activityImgMain;

    @Field(value = "ActivityImgDescription")
    private String activityImgDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityImgType() {
        return activityImgType;
    }

    public void setActivityImgType(String activityImgType) {
        this.activityImgType = activityImgType;
    }

    public String getActivityImgThumb() {
        return activityImgThumb;
    }

    public void setActivityImgThumb(String activityImgThumb) {
        this.activityImgThumb = activityImgThumb;
    }

    public String getActivityImgMain() {
        return activityImgMain;
    }

    public void setActivityImgMain(String activityImgMain) {
        this.activityImgMain = activityImgMain;
    }

    public String getActivityImgDescription() {
        return activityImgDescription;
    }

    public void setActivityImgDescription(String activityImgDescription) {
        this.activityImgDescription = activityImgDescription;
    }

}
