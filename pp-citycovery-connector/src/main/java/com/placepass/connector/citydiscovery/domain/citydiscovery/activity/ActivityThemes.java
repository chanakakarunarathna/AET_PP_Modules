package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityThemes {

    @Field(value = "ActivityTheme")
    private ActivityTheme activityTheme;

    public ActivityTheme getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(ActivityTheme activityTheme) {
        this.activityTheme = activityTheme;
    }

}
