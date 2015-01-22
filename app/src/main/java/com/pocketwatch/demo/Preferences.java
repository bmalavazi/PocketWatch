package com.pocketwatch.demo;

import android.content.Context;
import android.content.SharedPreferences;

import com.pocketwatch.demo.utils.Utils;

/**
 * Created by bmalavazi on 1/18/15.
 */
public class Preferences {
    private static final String TAG = "Preferences";

    public static void setRegistrationId(Context context, String id) {
        final String func = "setRegistrationId()";

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

    public static void setNotificationId(Context context) {
        final String func = "saveRegistrationId()";
        int id;

        id = getNotificationId(context);

        Utils.Entry(TAG, func, "Notification ID: " + id);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.GCM_NOTIFICATION_ID, ++id);
        editor.commit();

        Utils.Exit(TAG, func);
    }

    public static int getNotificationId(Context context) {
        final String func = "getNotificationId()";

        Utils.Entry(TAG, func);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        int id = preferences.getInt(Constants.GCM_NOTIFICATION_ID, 0);

        Utils.Exit(TAG, func, "Registration ID: " + id);

        return id;
    }
}
