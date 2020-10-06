package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostsResponse {
    @SerializedName("resultCode")
    @Expose
    private Integer resultCode;
    @SerializedName("resultData")
    @Expose
    private List<Posts> resultData = null;


    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public List<Posts> getResultData() {
        return resultData;
    }

    public void setResultData(List<Posts> resultData) {
        this.resultData = resultData;
    }

}
