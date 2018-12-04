package com.placepass.product.application.productdetails.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

/**
 * @author dilani.j
 *
 */
@ApiModel(value="LocationInfo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {

	private String title;

	private String description;

	private String street1;

	private String street2;

	private String city;

	private String postalCode;

	private String state;

	private String country;

	private String webSite;

	private Float latitude;

	private Float longitude;
	
	private String time;
	
	private String timeComments;

	private Map<String, String> extendedAttributes;

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

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}


	public Map<String, String> getExtendedAttributes() {
		return extendedAttributes;
	}

	public void setExtendedAttributes(Map<String, String> extendedAttributes) {
		this.extendedAttributes = extendedAttributes;
	}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeComments() {
        return timeComments;
    }

    public void setTimeComments(String timeComments) {
        this.timeComments = timeComments;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }



}
