package com.gobe.connector.domain.gobe.book;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created on 8/8/2017.
 */
@Document(collection = "GobeOrderRS")
public class GobeOrderRS {

    private String ttOrderNumber;

    private String ttOrderStatus;

    public String getTtOrderNumber() {
        return ttOrderNumber;
    }

    public void setTtOrderNumber(String ttOrderNumber) {
        this.ttOrderNumber = ttOrderNumber;
    }

    public String getTtOrderStatus() {
        return ttOrderStatus;
    }

    public void setTtOrderStatus(String ttOrderStatus) {
        this.ttOrderStatus = ttOrderStatus;
    }
}
