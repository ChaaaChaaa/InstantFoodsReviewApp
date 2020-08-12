package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post extends Product {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("uid")
    @Expose
    private Integer uid;
    @SerializedName("registed_time")
    @Expose
    private String registedTime;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;
    @SerializedName("stored_path")
    @Expose
    private String storedPath;
    @SerializedName("good_contents")
    @Expose
    private String goodContents;
    @SerializedName("bad_contents")
    @Expose
    private String badContents;
    @SerializedName("pr_id")
    @Expose
    private Integer prId;

    public Post(String productPicture, Integer productCategory, String title, int reviewCount, int productScore) {
        super(productPicture, productCategory, title, reviewCount, productScore);
    }

//    public Post(String postTitle, String goodPostPoint, String badPostPoint, String postPicture){
//        this.title = postTitle;
//        this.goodContents = goodPostPoint;
//        this.badContents = badPostPoint;
//        this.storedPath = postPicture;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRegistedTime() {
        return registedTime;
    }

    public void setRegistedTime(String registedTime) {
        this.registedTime = registedTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getStoredPath() {
        return storedPath;
    }

    public void setStoredPath(String storedPath) {
        this.storedPath = storedPath;
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

    public Integer getPrId() {
        return prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }
}
