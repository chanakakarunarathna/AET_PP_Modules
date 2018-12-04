package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityCancellation {

    @Field(value = "Day")
    private Integer day;

    @Field(value = "Percentage")
    private Double percentage;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

}
