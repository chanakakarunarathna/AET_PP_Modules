package com.placepass.connector.bemyguest.domain.bemyguest.product;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgCurrency {
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("symbol")
	private String symbol;
	
	@JsonProperty("uuid")
	private UUID uuid;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
