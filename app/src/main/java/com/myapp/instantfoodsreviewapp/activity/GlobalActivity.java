package com.myapp.instantfoodsreviewapp.activity;

import android.app.Application;

import com.myapp.instantfoodsreviewapp.preference.UserPreference;

public class GlobalActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserPreference.getInstance().setContext(this);
    }
}
