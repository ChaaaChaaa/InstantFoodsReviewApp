package com.myapp.instantfoodsreviewapp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.myapp.instantfoodsreviewapp.model.Post;

import java.util.List;

public class PostsDto {
    @SerializedName("resultCode")
    @Expose
    private Integer resultCode;
    @SerializedName("resultData")
    @Expose
    private List<Post> resultData = null;


    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public List<Post> getResultData() {
        return resultData;
    }

    public void setResultData(List<Post> resultData) {
        this.resultData = resultData;
    }

}
