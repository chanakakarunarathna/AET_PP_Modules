package com.placepass.connector.citydiscovery.domain.placepass.algolia.product;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Supplier")
public class AlgoliaSupplier {

    private String code;
    
	private String title;

	private String description;

	private String email;

	private AlgoliaLocation locationDTO;

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

	public AlgoliaLocation getLocation() {
		return locationDTO;
	}

	public void setLocation(AlgoliaLocation locationDTO) {
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
