package com.placepass.search.application.search.response;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Facets {
    
    private List<FacetItem> formattedAddress;
    private List<FacetItem> vendor;
    private List<FacetItem> subGroup;
    private List<FacetItem> group;
    private List<FacetItem> avgRating;
    private List<FacetItem> reviews;
    private List<FacetItem> classifications;
    private List<FacetItem> availableDays;
    
    public List<FacetItem> getFormattedAddress() {
        return formattedAddress;
    }
    public void setFormattedAddress(List<FacetItem> formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
    public List<FacetItem> getVendor() {
        return vendor;
    }
    public void setVendor(List<FacetItem> vendor) {
        this.vendor = vendor;
    }
    public List<FacetItem> getSubGroup() {
        return subGroup;
    }
    public void setSubGroup(List<FacetItem> subGroup) {
        this.subGroup = subGroup;
    }
    public List<FacetItem> getGroup() {
        return group;
    }
    public void setGroup(List<FacetItem> group) {
        this.group = group;
    }
    public List<FacetItem> getAvgRating() {
        return avgRating;
    }
    public void setAvgRating(List<FacetItem> avgRating) {
        this.avgRating = avgRating;
    }
    public List<FacetItem> getReviews() {
        return reviews;
    }
    public void setReviews(List<FacetItem> reviews) {
        this.reviews = reviews;
    }
    public List<FacetItem> getClassifications() {
        return classifications;
    }
    public void setClassifications(List<FacetItem> classifications) {
        this.classifications = classifications;
    }
    public List<FacetItem> getAvailableDays() {
        return availableDays;
    }
    public void setAvailableDays(List<FacetItem> availableDays) {
        this.availableDays = availableDays;
    }
   
   
}
