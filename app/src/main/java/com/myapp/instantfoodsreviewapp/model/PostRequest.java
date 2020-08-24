package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostRequest {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("good_contents")
    @Expose
    private String goodContents;
    @SerializedName("bad_contents")
    @Expose
    private String badContents;

    @SerializedName("score")
    @Expose
    private float score;

    @SerializedName("pr_id")
    @Expose
    private Integer prId;
    @SerializedName("path")
    @Expose
    private String path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoodContents() {
        return goodContents;
    }

    public void setGoodContents(String goodContents) {
        this.goodContents = goodContents;
    }

    public String getBadContents() {
        return badContents;
    }

    public void setBadContents(String badContents) {
        this.badContents = badContents;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Integer getPrId() {
        return prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
