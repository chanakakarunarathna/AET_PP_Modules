package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityCategories {

    @Field(value = "ActivityCategory")
    private ActivityCategory activityCategory;

    public ActivityCategory getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(ActivityCategory activityCategory) {
        this.activityCategory = activityCategory;
    }

}
