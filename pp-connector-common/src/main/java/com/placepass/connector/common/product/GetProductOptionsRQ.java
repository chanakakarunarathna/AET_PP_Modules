package com.placepass.connector.common.product;

import com.placepass.connector.common.common.MessageRequest;

public class GetProductOptionsRQ extends MessageRequest {

    private String productId;

    private String bookingDate;

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
     * @return the bookingDate
     */
    public String getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetProductOptionsCRQ [" + (productId != null ? "productId=" + productId + ", " : "")
                + (bookingDate != null ? "bookingDate=" + bookingDate : "") + "]";
    }

}
