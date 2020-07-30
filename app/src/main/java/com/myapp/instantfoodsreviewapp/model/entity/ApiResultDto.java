package com.myapp.instantfoodsreviewapp.model.entity;

import com.google.gson.JsonObject;
<<<<<<< HEAD
import com.google.gson.annotations.SerializedName;
=======
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.myapp.instantfoodsreviewapp.model.User;
>>>>>>> feature/11

public class ApiResultDto {
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultData")
<<<<<<< HEAD
    private JsonObject resultData; //json으로 받기에
=======
    private JsonObject resultData;
>>>>>>> feature/11
    @SerializedName("message")
    private String message;

    public int getResultCode(){
        return resultCode;
    }

    public void setResultCode(int resultCode){
        this.resultCode = resultCode;
    }

    public JsonObject getResultData(){
        return resultData;
    }

    public void setResultData(JsonObject resultData){
        this.resultData = resultData;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
<<<<<<< HEAD
=======


>>>>>>> feature/11
}
