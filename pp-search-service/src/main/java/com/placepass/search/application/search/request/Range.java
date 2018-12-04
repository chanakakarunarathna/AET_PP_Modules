package com.placepass.search.application.search.request;

public class Range {
	private Double min;
	private Double max;
		
	
	public Range() {
        
    }
	public Range(Double min, Double max) {
		this.min = min;
		this.max = max;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}	
}
