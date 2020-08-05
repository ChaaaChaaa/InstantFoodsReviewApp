package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.myapp.instantfoodsreviewapp.model.entity.ProductListDto;

import java.util.List;

public class ProductResponse {
    @SerializedName("resultCode")
    @Expose
    private Integer resultCode;
    @SerializedName("resultData")
    @Expose
    private List<Product> resultData = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public List<Product> getResultData() {
        return resultData;
    }

    public void setResultData(List<Product> resultData) {
        this.resultData = resultData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
