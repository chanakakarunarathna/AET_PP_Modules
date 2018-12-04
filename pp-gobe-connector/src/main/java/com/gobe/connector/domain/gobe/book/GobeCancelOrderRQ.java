package com.gobe.connector.domain.gobe.book;

import java.util.List;

/**
 * Created on 8/8/2017.
 */
public class GobeCancelOrderRQ {

    private String orderId;

    private Boolean full;

    private List<CancellationDetail> cancellationDetails;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public List<CancellationDetail> getCancellationDetails() {
        return cancellationDetails;
    }

    public void setCancellationDetails(List<CancellationDetail> cancellationDetails) {
        this.cancellationDetails = cancellationDetails;
    }
}
