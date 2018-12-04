package com.placepass.connector.bemyguest.domain.bemyguest.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BmgPhotoPaths {
	
	@JsonProperty("original")
	private String original;
	
	@JsonProperty("75x50")
	private String resolution75x50;
	
	@JsonProperty("175x112")
	private String resolution175x112;
	
	@JsonProperty("680x325")
	private String resolution680x325;
	
	@JsonProperty("1280x720")
	private String resolution1280x720;
	
	@JsonProperty("1920x1080")
	private String resolution1920x1080;
	
	@JsonProperty("2048x1536")
	private String resolution2048x1536;

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getResolution75x50() {
		return resolution75x50;
	}

	public void setResolution75x50(String resolution75x50) {
		this.resolution75x50 = resolution75x50;
	}

	public String getResolution175x112() {
		return resolution175x112;
	}

	public void setResolution175x112(String resolution175x112) {
		this.resolution175x112 = resolution175x112;
	}

	public String getResolution680x325() {
		return resolution680x325;
	}

	public void setResolution680x325(String resolution680x325) {
		this.resolution680x325 = resolution680x325;
	}

	public String getResolution1280x720() {
		return resolution1280x720;
	}

	public void setResolution1280x720(String resolution1280x720) {
		this.resolution1280x720 = resolution1280x720;
	}

	public String getResolution1920x1080() {
		return resolution1920x1080;
	}

	public void setResolution1920x1080(String resolution1920x1080) {
		this.resolution1920x1080 = resolution1920x1080;
	}

	public String getResolution2048x1536() {
		return resolution2048x1536;
	}

	public void setResolution2048x1536(String resolution2048x1536) {
		this.resolution2048x1536 = resolution2048x1536;
	}
	
}
