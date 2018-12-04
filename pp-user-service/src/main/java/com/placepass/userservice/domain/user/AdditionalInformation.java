package com.placepass.userservice.domain.user;

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
		

	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdditionalInformation other = (AdditionalInformation) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
}
