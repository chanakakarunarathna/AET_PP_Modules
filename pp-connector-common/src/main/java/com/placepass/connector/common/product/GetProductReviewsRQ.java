package com.placepass.connector.common.product;

import com.placepass.connector.common.common.MessageRequest;

public class GetProductReviewsRQ extends MessageRequest {

    private String productId;

    private int hitsPerPage;

    private int pageNumber;

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
     * @return the hitsPerPage
     */
    public int getHitsPerPage() {
        return hitsPerPage;
    }

    /**
     * @param hitsPerPage the hitsPerPage to set
     */
    public void setHitsPerPage(int hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetProductReviewsRQ [" + (productId != null ? "productId=" + productId + ", " : "") + "hitsPerPage="
                + hitsPerPage + ", pageNumber=" + pageNumber + "]";
    }

}
