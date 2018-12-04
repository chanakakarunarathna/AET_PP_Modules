package com.viator.connector.domain.viator.reviews;

import java.util.List;

import com.viator.connector.domain.viator.common.ViatorGenericResponse;

public class ViatorProductReviewsResponse extends ViatorGenericResponse {

    private List<ViatorProductReviewsResInfo> data;

    private Integer totalCount;

    public List<ViatorProductReviewsResInfo> getData() {
        return data;
    }

    public void setData(List<ViatorProductReviewsResInfo> data) {
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }


}
