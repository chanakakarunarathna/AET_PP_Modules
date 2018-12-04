package com.placepass.connector.common.product;

import java.util.List;

import com.placepass.connector.common.common.BaseRS;

public class GetProductReviewsRS extends BaseRS {

    private List<Review> reviews;

    private Integer totalReviewCount;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Integer getTotalReviewCount() {
        return totalReviewCount;
    }

    public void setTotalReviewCount(Integer totalReviewCount) {
        this.totalReviewCount = totalReviewCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GetProductReviewsRS [" + (reviews != null ? "reviews=" + reviews + ", " : "")
                + (totalReviewCount != null ? "totalReviewCount=" + totalReviewCount : "") + "]";
    }

}
