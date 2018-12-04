package com.placepass.connector.common.product;

import com.placepass.connector.common.common.MessageRequest;

public class GetAvailabilityRQ extends MessageRequest {

    private String productId;

    private String month;

    private String year;

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productid the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetAvailabilityRQ [" + (productId != null ? "productId=" + productId + ", " : "")
                + (month != null ? "month=" + month + ", " : "") + (year != null ? "year=" + year : "") + "]";
    }

}
