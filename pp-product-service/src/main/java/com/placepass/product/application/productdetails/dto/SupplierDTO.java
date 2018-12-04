package com.placepass.product.application.productdetails.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Supplier")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDTO {

    private String code;
    
	private String title;

	private String description;

	private String email;

	private LocationDTO locationDTO;

	private String phoneNumber;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocationDTO getLocation() {
		return locationDTO;
	}

	public void setLocation(LocationDTO locationDTO) {
		this.locationDTO = locationDTO;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
