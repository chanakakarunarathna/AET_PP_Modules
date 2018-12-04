package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityTheme {

    @Field(value = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
