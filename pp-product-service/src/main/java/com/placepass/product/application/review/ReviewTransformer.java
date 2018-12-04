package com.placepass.product.application.review;

import java.util.ArrayList;
import java.util.List;

import com.placepass.connector.common.product.GetProductReviewsRS;
import com.placepass.connector.common.product.Review;
import com.placepass.product.application.review.dto.GetReviewsRS;
import com.placepass.product.application.review.dto.ReviewDTO;

public class ReviewTransformer {

    public static GetReviewsRS toGetReviewsRS(GetProductReviewsRS getProductReviewsCRS, int hitsPerPage) {
        GetReviewsRS getReviewsRS = new GetReviewsRS();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();

        if (getProductReviewsCRS.getResultType().getCode() == 0) {
            for (Review review : getProductReviewsCRS.getReviews()) {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setReviewId(review.getReviewId());
                reviewDTO.setOwnerAvatarURL(review.getOwnerAvatarURL());
                reviewDTO.setOwnerCountry(review.getOwnerCountry());
                reviewDTO.setOwnerId(review.getOwnerId());
                reviewDTO.setOwnerName(review.getOwnerName());
                reviewDTO.setProductCode(review.getProductCode());
                reviewDTO.setProductTitle(review.getProductTitle());
                reviewDTO.setProductUrlName(review.getProductUrlName());
                reviewDTO.setPublishedDate(review.getPublishedDate());
                reviewDTO.setRating(review.getRating());
                reviewDTO.setReview(review.getReview());
                reviewDTO.setSubmissionDate(review.getSubmissionDate());
                reviewDTO.setVendorFeedback(review.getVendorFeedback());
                reviewDTO.setVendorNotes(review.getVendorNotes());

                reviewDTOList.add(reviewDTO);
            }

            getReviewsRS.setReviews(reviewDTOList);
            getReviewsRS.setTotalRecords(getProductReviewsCRS.getTotalReviewCount());
            int totalPages = getProductReviewsCRS.getTotalReviewCount() / hitsPerPage
                    + (getProductReviewsCRS.getTotalReviewCount() % hitsPerPage == 0 ? 0 : 1);
            getReviewsRS.setTotalPages(totalPages);
        }

        return getReviewsRS;
    }

}
