package com.myapp.instantfoodsreviewapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("uid")
    @Expose
    private Integer uid;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("profilepath")
    @Expose
    private String profilepath;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("registed_dt")
    @Expose
    private String registedDt;
    @SerializedName("updated_dt")
    @Expose
    private String updatedDt;
    @SerializedName("secession")
    @Expose
    private String secession;
    @SerializedName("noti_state")
    @Expose
    private String notiState;

    public Integer getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfilepath() {
        return profilepath;
    }

    public String getPassword() {
        return password;
    }

    public String getRegistedDt() {
        return registedDt;
    }

    public String getUpdatedDt() {
        return updatedDt;
    }

    public String getSecession() {
        return secession;
    }

    public String getNotiState() {
        return notiState;
    }
}
