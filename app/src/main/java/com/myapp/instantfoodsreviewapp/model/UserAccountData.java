package com.myapp.instantfoodsreviewapp.model;

public class UserAccountData {
    private String email;
    private String nickname;

    UserAccountData(String email, String nickname){
        this.email = email;
        this.nickname = nickname;
    }

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
}
