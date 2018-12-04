package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import org.springframework.data.mongodb.core.mapping.Field;

public class ActivityCategory {

    @Field(value = "ID")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
