package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgLocations {
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("cityUuid")
	private UUID cityUuid;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("stateUuid")
	private UUID stateUuid;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("countryUuid")
	private UUID countryUuid;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public UUID getCityUuid() {
		return cityUuid;
	}

	public void setCityUuid(UUID cityUuid) {
		this.cityUuid = cityUuid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UUID getStateUuid() {
		return stateUuid;
	}

	public void setStateUuid(UUID stateUuid) {
		this.stateUuid = stateUuid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public UUID getCountryUuid() {
		return countryUuid;
	}

	public void setCountryUuid(UUID countryUuid) {
		this.countryUuid = countryUuid;
	}

}
