package com.myapp.instantfoodsreviewapp.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private static final String USER_PREFERENCES = "USER_PREFERENCES";
    private static final String LOGGED_IN_PREF = "logged_in_status";
    private static SharedPreferences userSharedPreferences;
    private static SharedPreferences.Editor sharedPreferencesEditor;

    private static SharedPreferences getPreference(Context context){
       return context.getSharedPreferences(USER_PREFERENCES, Activity.MODE_PRIVATE);
       // this.sharedPreferencesEditor = userSharedPreferences.edit();
    }

    private static SharedPreferences.Editor getPreferenceEditor(Context context){
        if(userSharedPreferences == null){
            userSharedPreferences = getPreference(context);
        }
        return userSharedPreferences.edit();
    }

    public static void setLoggedIn(Context context, boolean loggedIn){
        userSharedPreferences = getPreference(context);
        sharedPreferencesEditor = getPreferenceEditor(context);
        sharedPreferencesEditor.putBoolean(LOGGED_IN_PREF,loggedIn);
        sharedPreferencesEditor.apply();
    }

    public static boolean getLoggedStatus(Context context){
        return getPreference(context).getBoolean(LOGGED_IN_PREF,false);
    }

    public static void clearUserLogin(Context context){
        sharedPreferencesEditor = getPreferenceEditor(context);
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }



}
