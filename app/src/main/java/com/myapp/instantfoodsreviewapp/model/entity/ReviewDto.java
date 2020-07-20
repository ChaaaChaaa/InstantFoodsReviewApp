package com.myapp.instantfoodsreviewapp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.myapp.instantfoodsreviewapp.model.WriteReviewData;

import java.util.List;

public class ReviewDto {
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultData")
    private ResultData resultData;
    @SerializedName("message")
    private String message;

    public int getResultCode(){
        return resultCode;
    }

    public void setResultCode(int resultCode){
        this.resultCode = resultCode;
    }

    public ResultData getResultData(){
        return resultData;
    }

    public void setResultData(ResultData resultData){
        this.resultData = resultData;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public static class ResultData {
        @SerializedName("request")
        @Expose
        private Review review;
        @SerializedName("stored_paths")
        @Expose
        private List<String> storedPaths = null;

        public Review getReview() {
            return review;
        }

        public void setReview(Review request) {
            this.review = request;
        }

        public List<String> getStoredPaths() {
            return storedPaths;
        }

        public void setStoredPaths(List<String> storedPaths) {
            this.storedPaths = storedPaths;
        }

    }

    public static class Review {
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("good_point")
        @Expose
        private String goodPoint;
        @SerializedName("bad_point")
        @Expose
        private String badPoint;
        @SerializedName("estimate_score")
        @Expose
        private String estimateScore;
        @SerializedName("contents")
        @Expose
        private String contents;
        @SerializedName("pr_id")
        @Expose
        private String prId;
        @SerializedName("path")
        @Expose
        private String path;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGoodPoint() {
            return goodPoint;
        }

        public void setGoodPoint(String goodPoint) {
            this.goodPoint = goodPoint;
        }

        public String getBadPoint() {
            return badPoint;
        }

        public void setBadPoint(String badPoint) {
            this.badPoint = badPoint;
        }

        public String getEstimateScore() {
            return estimateScore;
        }

        public void setEstimateScore(String estimateScore) {
            this.estimateScore = estimateScore;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getPrId() {
            return prId;
        }

        public void setPrId(String prId) {
            this.prId = prId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
