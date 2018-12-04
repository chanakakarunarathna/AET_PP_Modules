package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityStartingPlace {

    @Field(value = "Lat")
    private String latitude;

    @Field(value = "Long")
    private String longtitude;

    @Field(value = "Value")
    private String value;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
