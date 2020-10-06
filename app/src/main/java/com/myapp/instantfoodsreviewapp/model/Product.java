package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("pr_id")
    @Expose
    private Integer prId;
    @SerializedName("pr_title")
    @Expose
    private String prTitle;
    @SerializedName("pr_score")
    @Expose
    private Integer prScore;
    @SerializedName("pr_registed_time")
    @Expose
    private String prRegistedTime;
    @SerializedName("pr_image")
    @Expose
    private String prImage;
    @SerializedName("pr_category")
    @Expose
    private Integer prCategory;
    @SerializedName("pr_review_count")
    @Expose
    private Integer prReviewCount;

    public Product(String productPicture, Integer productCategory, String title, int reviewCount, int productScore) {
        prImage = productPicture;
        prCategory = productCategory;
        prTitle = title;
        prReviewCount = checkReviewCount(reviewCount);
        prScore = productScore;
    }

    private Integer checkReviewCount(Integer reviewCount) {
        if (reviewCount == null) {
            reviewCount = 0;
        }
        return reviewCount;
    }


    public Integer getPrId() {
        return prId;
    }

    public String getPrTitle() {
        return prTitle;
    }

    public Integer getPrScore() {
        return prScore;
    }


    public String getPrImage() {
        return prImage;
    }

    public void setPrImage(String prImage) {
        this.prImage = prImage;
    }

    public Integer getPrCategory() {
        return prCategory;
    }

    public void setPrCategory(Integer prCategory) {
        this.prCategory = prCategory;
    }

    public Integer getPrReviewCount() {
        return prReviewCount;
    }

    public void setPrReviewCount(Integer prReviewCount) {
        this.prReviewCount = prReviewCount;
    }

}

