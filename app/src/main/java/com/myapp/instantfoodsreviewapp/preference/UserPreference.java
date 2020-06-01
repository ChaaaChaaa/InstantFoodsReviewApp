package com.myapp.instantfoodsreviewapp.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private static final String USER_PREFERENCES = "USER_PREFERENCES";
    private static final String LOGGED_IN_PREF = "logged_in_status";
    private static SharedPreferences userSharedPreferences;
    private static SharedPreferences.Editor sharedPreferencesEditor;


    private static UserPreference ownPreference = new UserPreference();

    public static UserPreference getInstance(){
        return ownPreference;
    }

    public void setContext(Context context){
        userSharedPreferences = getPreference(context);
        sharedPreferencesEditor = getPreferenceEditor(context);
    }


    private static SharedPreferences getPreference(Context context){
       return context.getSharedPreferences(USER_PREFERENCES, Activity.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getPreferenceEditor(Context context){
        return userSharedPreferences.edit();
    }

    public static boolean getLoggedStatus(Context context){
        return getPreference(context).getBoolean(LOGGED_IN_PREF,false);
    }

    public static void clearUserLogin(Context context){
       // sharedPreferencesEditor = getPreferenceEditor(context);
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }

    public void putString(String key, String value) {
        sharedPreferencesEditor.putString(key, value);
        sharedPreferencesEditor.commit();
    }

    public String getString(String key) {
        return userSharedPreferences.getString(key, "");
    }
}
