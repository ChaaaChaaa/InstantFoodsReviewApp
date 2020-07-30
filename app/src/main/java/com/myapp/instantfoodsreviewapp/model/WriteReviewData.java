package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WriteReviewData {
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
