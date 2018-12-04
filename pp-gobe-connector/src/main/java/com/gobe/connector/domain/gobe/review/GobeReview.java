package com.gobe.connector.domain.gobe.review;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created on 8/7/2017.
 */
@Document(collection = "GobeReviews")
public class GobeReview {

    private String comment;

    private String date;

    private String headline;
    @Id
    private String id;

    private String rating;

    private GobePrincipal principal;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public GobePrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(GobePrincipal principal) {
        this.principal = principal;
    }
}
