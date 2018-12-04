package com.placepass.search.application.search.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationData {
    private List<LocationIndex> placeList;

    public List<LocationIndex> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<LocationIndex> placeList) {
        this.placeList = placeList;
    }

}
