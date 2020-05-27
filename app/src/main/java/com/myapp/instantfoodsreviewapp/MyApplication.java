package com.myapp.instantfoodsreviewapp;

import android.app.Application;

import com.myapp.instantfoodsreviewapp.preference.UserPreference;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UserPreference.getInstance().setContext(this);
    }
}
