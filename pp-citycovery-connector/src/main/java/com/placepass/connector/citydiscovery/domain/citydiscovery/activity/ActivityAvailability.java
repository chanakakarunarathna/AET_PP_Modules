package com.placepass.connector.citydiscovery.domain.citydiscovery.activity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "productavailability")
public class ActivityAvailability {

    @Id
    private String id;

    private List<ActivitySingleAvailability> availability;

    private ActivityAvailabilityResultType resultType;

    private String productID;

    @Field(value = "UpdateOn")
    private String updateOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ActivitySingleAvailability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<ActivitySingleAvailability> availability) {
        this.availability = availability;
    }

    public ActivityAvailabilityResultType getResultType() {
        return resultType;
    }

    public void setResultType(ActivityAvailabilityResultType resultType) {
        this.resultType = resultType;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(String updateOn) {
        this.updateOn = updateOn;
    }

}
