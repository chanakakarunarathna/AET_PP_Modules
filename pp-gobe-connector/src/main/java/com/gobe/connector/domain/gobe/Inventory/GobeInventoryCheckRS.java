package com.gobe.connector.domain.gobe.Inventory;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created on 8/7/2017.
 */
@Document(collection = "GobeInventoryCheck")
public class GobeInventoryCheckRS {

    private String componentKey;

    private String status;

    private String timeStamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getComponentKey() {
        return componentKey;
    }

    public void setComponentKey(String componentKey) {
        this.componentKey = componentKey;
    }
}
