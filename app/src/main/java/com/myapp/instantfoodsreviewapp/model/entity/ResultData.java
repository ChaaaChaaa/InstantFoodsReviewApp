package com.myapp.instantfoodsreviewapp.model.entity;

import com.google.gson.annotations.SerializedName;

public class ResultData {
    @SerializedName("user")
    private String user;

    public String getUser() {
        return user;
    }
}
