package com.placepass.userservice.controller.dto;

/**
 * The Class AdditionalInformation.
 * 
 * @author shanakak
 */
public class AdditionalInformation {

	
	/** The key. */
	private String key;
	
	/** The value. */
	private String value;

	/**
	 * Instantiates a new additional information.
	 */
	public AdditionalInformation(){
		
	}
	
	/**
	 * Instantiates a new additional information.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public AdditionalInformation(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	
	
	
}
