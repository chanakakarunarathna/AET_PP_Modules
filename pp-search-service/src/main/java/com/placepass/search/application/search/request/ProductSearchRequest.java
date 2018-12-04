package com.placepass.search.application.search.request;

import java.util.List;

import javax.validation.constraints.Min;

public class ProductSearchRequest {

    private String query;

    private SortBy sortBy;

    private Location location;

    private List<Integer> ratings;

    private Range reviews;

    private Range durationInMinutes;

    private Range price;

    private List<String> categories;
    
    private List<String> group;
    
    private List<String> subGroup;
    
    private List<String> classifications;
    
    private List<String> productIds;
    
    private DateRange availableDates;

    private Boolean triedAndTrue;

    private Integer pageNumber;

    @Min(1)
    private Integer hitsPerPage;
    
    private Boolean isBookable;

    private String tags;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public Range getReviews() {
        return reviews;
    }

    public void setReviews(Range reviews) {
        this.reviews = reviews;
    }

    public Range getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Range durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Range getPrice() {
        return price;
    }

    public void setPrice(Range price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Boolean getTriedAndTrue() {
        return triedAndTrue;
    }

    public void setTriedAndTrue(Boolean triedAndTrue) {
        this.triedAndTrue = triedAndTrue;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getHitsPerPage() {
        return hitsPerPage;
    }

    public void setHitsPerPage(Integer hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getIsBookable() {
        return isBookable;
    }

    public void setIsBookable(Boolean isBookable) {
        this.isBookable = isBookable;
    }

	public List<String> getGroup() {
		return group;
	}

	public void setGroup(List<String> group) {
		this.group = group;
	}

	public List<String> getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(List<String> subGroup) {
		this.subGroup = subGroup;
	}

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<String> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<String> classifications) {
        this.classifications = classifications;
    }

    public DateRange getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(DateRange availableDates) {
        this.availableDates = availableDates;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
