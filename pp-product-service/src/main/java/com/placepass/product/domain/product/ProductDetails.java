package com.placepass.product.domain.product;

import java.util.List;

public class ProductDetails {

	private String languageCode;

	private String title;

	private String description;

	private List<String> images;

	private List<String> vidoes;

	private List<String> inclusions;

	private List<String> exclusions;

	private List<String> higlights;

	private Supplier supplier;

	private List<String> activitySnapshots;

	private List<Location> meetingPoint;

	private List<Location> dropOffPoint;

	private List<String> voucherTypes;

	private List<String> duration;

	private List<Price> prices;

	// TODO: Some vendors might return this. For others it will be a separeate
	// call.
	private int reviewCount;

	// TODO: We will need a rate normalizer transformation.
	// TODO: Some vendors might return this. For others it will be a separeate
	// call.
	private float averageRating;

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getVidoes() {
		return vidoes;
	}

	public void setVidoes(List<String> vidoes) {
		this.vidoes = vidoes;
	}

	public List<String> getInclusions() {
		return inclusions;
	}

	public void setInclusions(List<String> inclusions) {
		this.inclusions = inclusions;
	}

	public List<String> getExclusions() {
		return exclusions;
	}

	public void setExclusions(List<String> exclusions) {
		this.exclusions = exclusions;
	}

	public List<String> getHiglights() {
		return higlights;
	}

	public void setHiglights(List<String> higlights) {
		this.higlights = higlights;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<String> getActivitySnapshots() {
		return activitySnapshots;
	}

	public void setActivitySnapshots(List<String> activitySnapshots) {
		this.activitySnapshots = activitySnapshots;
	}

	public List<Location> getMeetingPoint() {
		return meetingPoint;
	}

	public void setMeetingPoint(List<Location> meetingPoint) {
		this.meetingPoint = meetingPoint;
	}

	public List<Location> getDropOffPoint() {
		return dropOffPoint;
	}

	public void setDropOffPoint(List<Location> dropOffPoint) {
		this.dropOffPoint = dropOffPoint;
	}

	public List<String> getVoucherTypes() {
		return voucherTypes;
	}

	public void setVoucherTypes(List<String> voucherTypes) {
		this.voucherTypes = voucherTypes;
	}

	public List<String> getDuration() {
		return duration;
	}

	public void setDuration(List<String> duration) {
		this.duration = duration;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}

	// TODO: Are booking questions part of the product?

}
