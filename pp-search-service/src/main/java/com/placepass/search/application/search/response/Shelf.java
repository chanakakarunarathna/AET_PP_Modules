package com.placepass.search.application.search.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shelf {
	private String shelfType;
	
	@JsonProperty("isLastPosition")
	private boolean	lastPosition;
	
	private String carLocationCode;
	
	private String status;
	
    private String title;

    private List<String> productIds;

    private String link;

    private int order;

    public String getTitle() {
        return title;
    }
    
	public String getShelfType() {
		return shelfType;
	}

	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}

	public String getCarLocationCode() {
		return carLocationCode;
	}

	public void setCarLocationCode(String carLocationCode) {
		this.carLocationCode = carLocationCode;
	}

    public void setTitle(String title) {
        this.title = title;
    }   

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(boolean lastPosition) {
		this.lastPosition = lastPosition;
	}

}
