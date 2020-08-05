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
    private Object prReviewCount;

    public Integer getPrId() {
        return prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    public String getPrTitle() {
        return prTitle;
    }

    public void setPrTitle(String prTitle) {
        this.prTitle = prTitle;
    }

    public Integer getPrScore() {
        return prScore;
    }

    public void setPrScore(Integer prScore) {
        this.prScore = prScore;
    }

    public String getPrRegistedTime() {
        return prRegistedTime;
    }

    public void setPrRegistedTime(String prRegistedTime) {
        this.prRegistedTime = prRegistedTime;
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

    public Object getPrReviewCount() {
        return prReviewCount;
    }

    public void setPrReviewCount(Object prReviewCount) {
        this.prReviewCount = prReviewCount;
    }

}

