package com.placepass.product.application.availability.dto;

import java.util.List;

public class GetAvailabilityRS {
	List<AvailabilityDTO> availabilityList;

	public List<AvailabilityDTO> getAvailabilityList() {
		return availabilityList;
	}

	public void setAvailabilityList(List<AvailabilityDTO> availabilityList) {
		this.availabilityList = availabilityList;
	}

	
}
