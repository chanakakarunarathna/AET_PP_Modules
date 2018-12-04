package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgPhoto {

    private String caption;

    private UUID uuid;

    @JsonProperty("paths")
    private BmgPhotoPaths paths;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BmgPhotoPaths getPaths() {
        return paths;
    }

    public void setPaths(BmgPhotoPaths paths) {
        this.paths = paths;
    }

}
