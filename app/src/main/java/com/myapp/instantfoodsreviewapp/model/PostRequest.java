package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostRequest {
    @SerializedName("path")
    @Expose
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
