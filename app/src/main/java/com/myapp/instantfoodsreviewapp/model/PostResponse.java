package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResponse {
    @SerializedName("resultCode")
    @Expose
    private Integer resultCode;
    @SerializedName("resultData")
    @Expose
    private Post post;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public Post getPosts() {
        return post;
    }

    public void setPosts(Post post) {
        this.post = post;
    }
}
