package com.viator.connector.domain.viator.pricingmatrix;

import java.util.List;

public class ViatorPricingMatrixResAgeBandPrice {

	private Integer bandId;

	private Integer sortOrder;

	private Integer maximumCountRequired;

	private Integer minimumCountRequired;

	private List<ViatorPricingMatrixResPrice> prices;

	public Integer getBandId() {
		return bandId;
	}

	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getMaximumCountRequired() {
		return maximumCountRequired;
	}

	public void setMaximumCountRequired(Integer maximumCountRequired) {
		this.maximumCountRequired = maximumCountRequired;
	}

	public Integer getMinimumCountRequired() {
		return minimumCountRequired;
	}

	public void setMinimumCountRequired(Integer minimumCountRequired) {
		this.minimumCountRequired = minimumCountRequired;
	}

	public List<ViatorPricingMatrixResPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<ViatorPricingMatrixResPrice> prices) {
		this.prices = prices;
	}
}
