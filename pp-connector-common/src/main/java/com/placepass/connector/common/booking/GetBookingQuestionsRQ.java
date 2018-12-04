package com.placepass.connector.common.booking;

public class GetBookingQuestionsRQ {

    private String partnerId;

    private String productId;

    private String currencyCode;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the partnerId
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * @param partnerId the partnerId to set
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetBookingQuestionsRQ [" + (partnerId != null ? "partnerId=" + partnerId + ", " : "")
                + (productId != null ? "productId=" + productId + ", " : "")
                + (currencyCode != null ? "currencyCode=" + currencyCode : "") + "]";
    }

}
