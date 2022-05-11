package com.example.ahmad.hakimi.basicClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



public class AttolSharedPreference {

    private Activity activity;
    private FcmMessage activity1;

    public AttolSharedPreference(Activity context) {
        this.activity = context;
    }
    public AttolSharedPreference(FcmMessage context) {
        this.activity1 = context;
    }

    public void setKey(String key, String value) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public void setKey(String key, boolean value) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getKey(String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);

        return sharedPref.getString(key, null);

    }
    public String getKey1(String key) {
        SharedPreferences sharedPref = activity1.getSharedPreferences("attolPref", Context.MODE_PRIVATE);

        return sharedPref.getString(key, null);

    }
    public boolean getBooleanKey(String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences("attolPref", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, true);
    }


}
