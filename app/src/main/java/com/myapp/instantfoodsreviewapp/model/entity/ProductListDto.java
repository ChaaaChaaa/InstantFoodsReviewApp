package com.myapp.instantfoodsreviewapp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductListDto {
    @SerializedName("resultCode")
    @Expose
    private Integer resultCode;
    @SerializedName("resultData")
    @Expose
    private List<ProductList> resultData = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public List<ProductList> getResultData() {
        return resultData;
    }

    public void setResultData(List<ProductList> resultData) {
        this.resultData = resultData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ProductList {

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

        public Integer getPrReviewCount() {
            return prReviewCount;
        }

        public void setPrReviewCount(Integer prReviewCount) {
            this.prReviewCount = prReviewCount;
        }

    }
}