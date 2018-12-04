package com.placepass.connector.common.product;

public class GetProductDetailsRQ {

    private String partnerId;

    private String productId;

    private String currencyCode;

    private boolean excludeTourGrade;

    private boolean showUnavailable;

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
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
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

    /**
     * @return the excludeTourGrade
     */
    public boolean isExcludeTourGrade() {
        return excludeTourGrade;
    }

    /**
     * @param excludeTourGrade the excludeTourGrade to set
     */
    public void setExcludeTourGrade(boolean excludeTourGrade) {
        this.excludeTourGrade = excludeTourGrade;
    }

    /**
     * @return the showUnavailable
     */
    public boolean isShowUnavailable() {
        return showUnavailable;
    }

    /**
     * @param showUnavailable the showUnavailable to set
     */
    public void setShowUnavailable(boolean showUnavailable) {
        this.showUnavailable = showUnavailable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetProductDetailsRQ [" + (partnerId != null ? "partnerId=" + partnerId + ", " : "")
                + (productId != null ? "productId=" + productId + ", " : "")
                + (currencyCode != null ? "currencyCode=" + currencyCode + ", " : "") + "excludeTourGrade="
                + excludeTourGrade + ", showUnavailable=" + showUnavailable + "]";
    }

}
