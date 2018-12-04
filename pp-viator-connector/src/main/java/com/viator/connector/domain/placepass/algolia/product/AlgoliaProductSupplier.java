package com.viator.connector.domain.placepass.algolia.product;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Supplier")
public class AlgoliaProductSupplier {

    private String code;
    
	private String title;

	private String description;

	private String email;

	private AlgoliaProductLocationDTO locationDTO;

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

	public AlgoliaProductLocationDTO getLocation() {
		return locationDTO;
	}

	public void setLocation(AlgoliaProductLocationDTO locationDTO) {
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
