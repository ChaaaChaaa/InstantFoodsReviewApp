package com.myapp.instantfoodsreviewapp.model;

import java.util.UUID;

public class ReviewData {
    private String goodPointReview;
    private String badPointReview;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ReviewData() {
        id = UUID.randomUUID();
    }

    public String getGoodPointReview() {
        return goodPointReview;
    }

    public void setGoodPointReview(String goodPointReview) {
        this.goodPointReview = goodPointReview;
    }

    public String getBadPointReview() {
        return badPointReview;
    }

    public void setBadPointReview(String badPointReview) {
        this.badPointReview = badPointReview;
    }
}
