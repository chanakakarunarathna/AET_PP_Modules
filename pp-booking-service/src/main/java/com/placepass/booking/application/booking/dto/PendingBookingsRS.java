package com.placepass.booking.application.booking.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "PendingBookingsResponse")
public class PendingBookingsRS {

	private List<PendingBookingDTO> pendingBookings;

	public List<PendingBookingDTO> getPendingBookings() {
		return pendingBookings;
	}

	public void setPendingBookings(List<PendingBookingDTO> pendingBookings) {
		this.pendingBookings = pendingBookings;
	}

}
