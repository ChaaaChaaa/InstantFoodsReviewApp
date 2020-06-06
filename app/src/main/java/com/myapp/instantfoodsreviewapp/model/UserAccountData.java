package com.myapp.instantfoodsreviewapp.model;

public class UserAccountData {
    private String email;
    private String nickname;
    private int uid;

//    UserAccountData(String email, String nickname, int uid) {
//        this.email = email;
//        this.nickname = nickname;
//        this.uid = uid;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
