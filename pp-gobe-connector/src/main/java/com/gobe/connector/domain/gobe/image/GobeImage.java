package com.gobe.connector.domain.gobe.image;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created on 8/7/2017.
 */
@Document(collection = "images")
public class GobeImage {

    private String id;

    private GobeGenericImage horizontal;

    private GobeGenericImage vertical;

    private List<GobeGenericImage> largeHeroImages;

    private List<GobeGenericImage> mediumHeroImages;

    private List<GobeGenericImage> smallHeroImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GobeGenericImage getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(GobeGenericImage horizontal) {
        this.horizontal = horizontal;
    }

    public GobeGenericImage getVertical() {
        return vertical;
    }

    public void setVertical(GobeGenericImage vertical) {
        this.vertical = vertical;
    }

    public List<GobeGenericImage> getLargeHeroImages() {
        return largeHeroImages;
    }

    public void setLargeHeroImages(List<GobeGenericImage> largeHeroImages) {
        this.largeHeroImages = largeHeroImages;
    }

    public List<GobeGenericImage> getMediumHeroImages() {
        return mediumHeroImages;
    }

    public void setMediumHeroImages(List<GobeGenericImage> mediumHeroImages) {
        this.mediumHeroImages = mediumHeroImages;
    }

    public List<GobeGenericImage> getSmallHeroImages() {
        return smallHeroImages;
    }

    public void setSmallHeroImages(List<GobeGenericImage> smallHeroImages) {
        this.smallHeroImages = smallHeroImages;
    }
}
