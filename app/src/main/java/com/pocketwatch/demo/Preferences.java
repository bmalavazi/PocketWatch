package com.pocketwatch.demo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bmalavazi on 1/18/15.
 */
public class Preferences {
    private static final String TAG = "Preferences";

    public void saveRegistrationId(String id) {
        SharedPreferences preferences = Application.getInstance().getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.GCM_REGISTRATION_ID, id);
        editor.commit();
    }

    public String getRegistrationId() {
        SharedPreferences preferences = Application.getInstance().getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id = preferences.getString(Constants.GCM_REGISTRATION_ID, null);

        return id;
    }
}
