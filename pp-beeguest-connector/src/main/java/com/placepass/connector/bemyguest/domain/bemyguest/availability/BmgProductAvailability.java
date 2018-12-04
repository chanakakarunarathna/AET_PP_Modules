package com.placepass.connector.bemyguest.domain.bemyguest.availability;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.placepass.connector.bemyguest.domain.bemyguest.common.BmgResultType;

@Document(collection = "ProductAvailability")
public class BmgProductAvailability {

    @Id
    private String id;

    private List<BmgAvailability> availability;

    private BmgResultType resultType;

    private String productID;

    @Field(value = "UpdateOn")
    private String updateOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BmgAvailability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<BmgAvailability> availability) {
        this.availability = availability;
    }

    public BmgResultType getResultType() {
        return resultType;
    }

    public void setResultType(BmgResultType resultType) {
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
