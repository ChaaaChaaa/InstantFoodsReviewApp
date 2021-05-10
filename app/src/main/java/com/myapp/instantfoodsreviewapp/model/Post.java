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
}
