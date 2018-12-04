package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgBasicField {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("uuid")
	private UUID uuid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
