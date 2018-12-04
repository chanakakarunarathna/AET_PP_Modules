package com.gobe.connector.domain.gobe.book;

/**
 * Created on 8/8/2017.
 */
public class OrderStat {

    private String requestedProduct;

    private String ttaOrderId;

    private String supplierComment;

    private String printTicketURL;

    private String mobileTicketURL;

    private String confirmationNumber;

    private String timestamp;

    private String groupId;

    private String partnerId;

    public String getRequestedProduct() {
        return requestedProduct;
    }

    public void setRequestedProduct(String requestedProduct) {
        this.requestedProduct = requestedProduct;
    }

    public String getTtaOrderId() {
        return ttaOrderId;
    }

    public void setTtaOrderId(String ttaOrderId) {
        this.ttaOrderId = ttaOrderId;
    }

    public String getSupplierComment() {
        return supplierComment;
    }

    public void setSupplierComment(String supplierComment) {
        this.supplierComment = supplierComment;
    }

    public String getPrintTicketURL() {
        return printTicketURL;
    }

    public void setPrintTicketURL(String printTicketURL) {
        this.printTicketURL = printTicketURL;
    }

    public String getMobileTicketURL() {
        return mobileTicketURL;
    }

    public void setMobileTicketURL(String mobileTicketURL) {
        this.mobileTicketURL = mobileTicketURL;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
