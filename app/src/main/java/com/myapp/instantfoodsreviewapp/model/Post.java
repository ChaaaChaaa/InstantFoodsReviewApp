package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("request")
    @Expose
    private PostRequest postRequest;
    @SerializedName("stored_paths")
    @Expose
    private String storedPaths;

    public PostRequest getPostRequest() {
        return postRequest;
    }

    public void setPostRequest(PostRequest postRequest) {
        this.postRequest = postRequest;
    }

    public String getStoredPaths() {
        return storedPaths;
    }

    public void setStoredPaths(String storedPaths) {
        this.storedPaths = storedPaths;
    }
}
