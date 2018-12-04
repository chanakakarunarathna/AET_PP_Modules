package com.gobe.connector.domain.gobe.book;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created on 8/8/2017.
 */
@Document(collection = "GobeCancelOrderRS")
public class GobeCancelOrderRS {

    private String cancellationId;

    private Float refundedAmout;

    private String vendorOrderId;

    public String getCancellationId() {
        return cancellationId;
    }

    public void setCancellationId(String cancellationId) {
        this.cancellationId = cancellationId;
    }

    public Float getRefundedAmout() {
        return refundedAmout;
    }

    public void setRefundedAmount(Float refundedAmout) {
        this.refundedAmout = refundedAmout;
    }

    public String getVendorOrderId() {
        return vendorOrderId;
    }

    public void setVendorOrderId(String vendorOrderId) {
        this.vendorOrderId = vendorOrderId;
    }
}
