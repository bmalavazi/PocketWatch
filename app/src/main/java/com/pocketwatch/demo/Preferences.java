package com.pocketwatch.demo;

import android.content.Context;
import android.content.SharedPreferences;

import com.pocketwatch.demo.utils.Utils;

/**
 * Created by bmalavazi on 1/18/15.
 */
public class Preferences {
    private static final String TAG = "Preferences";

    public static void saveRegistrationId(Context context, String id) {
        final String func = "saveRegistrationId()";

        Utils.Entry(TAG, func, "Registration ID: " + id);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.GCM_REGISTRATION_ID, id);
        editor.commit();

        Utils.Exit(TAG, func);
    }

    public static String getRegistrationId(Context context) {
        final String func = "getRegistrationId()";

        Utils.Entry(TAG, func);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id = preferences.getString(Constants.GCM_REGISTRATION_ID, null);

        Utils.Exit(TAG, func, "Registration ID: " + id);

        return id;
    }
}
