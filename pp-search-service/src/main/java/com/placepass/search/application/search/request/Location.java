package com.placepass.search.application.search.request;

public class Location {

    private String webId;
    
    private String geoLocationId;

    private Float latitude;

    private Float longitude;

    private String formattedAddress;

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getGeoLocationId() {
        return geoLocationId;
    }

    public void setGeoLocationId(String geoLocationId) {
        this.geoLocationId = geoLocationId;
    }

}
