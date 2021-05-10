package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ApiResultDto {
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultData")
    private JsonObject resultData;
    @SerializedName("message")
    private String message;

    public int getResultCode() {
        return resultCode;
    }

    public JsonObject getResultData() {
        return resultData;
    }

    public void setResultData(JsonObject resultData) {
        this.resultData = resultData;
    }
}
