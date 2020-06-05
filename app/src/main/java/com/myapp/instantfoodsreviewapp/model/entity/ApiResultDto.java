package com.myapp.instantfoodsreviewapp.model.entity;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ApiResultDto {
<<<<<<< HEAD

=======
>>>>>>> [UPDATE] to use token in main activity
    @SerializedName("resultCode")
    private int resultCode;
    @SerializedName("resultData")
    private JsonObject resultData;
    @SerializedName("message")
    private String message;

<<<<<<< HEAD

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public JsonObject getResultData() {
        return resultData;
    }

    public void setResultData(JsonObject resultData) {
        this.resultData = resultData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
=======
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
>>>>>>> [UPDATE] to use token in main activity
        this.message = message;
    }
}
