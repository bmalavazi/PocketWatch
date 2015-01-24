package com.pocketwatch.demo;

import android.content.Context;
import android.content.SharedPreferences;

import com.pocketwatch.demo.utils.Utils;

import java.util.Set;
import java.util.TreeSet;

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

    public static Set<String> getSubscriptions(Context context) {
        final String func = "getSubscriptions()";

        Utils.Entry(TAG, func);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> uuids = preferences.getStringSet(Constants.APP_SHOW_SUBSCRIPTIONS, null);

        Utils.Exit(TAG, func);

        return uuids;
    }

    public static void setSubscriptions(Context context, String uuid) {
        final String func = "setSubscriptions()";

        Utils.Entry(TAG, func, "UUID: " + uuid);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> uuids = preferences.getStringSet(Constants.APP_SHOW_SUBSCRIPTIONS, null);

        if (null == uuids) {
            uuids = new TreeSet<String>();
        }

        if (!uuids.contains(uuid)) {
            uuids.add(uuid);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(Constants.APP_SHOW_SUBSCRIPTIONS, uuids);
            editor.commit();
        }

        Utils.Exit(TAG, func);
    }

    public static void removeSubscriptions(Context context, String uuid) {
        final String func = "setSubscriptions()";

        Utils.Entry(TAG, func, "UUID: " + uuid);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> uuids = preferences.getStringSet(Constants.APP_SHOW_SUBSCRIPTIONS, null);

        if (null == uuids) {
            Utils.Exit(TAG, func, "Uuids is null");
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(Constants.APP_SHOW_SUBSCRIPTIONS, uuids);
        editor.commit();

        Utils.Exit(TAG, func);
    }

    public static void setSubscriptionsPush(Context context, String uuid) {
        final String func = "setSubscriptionsPush()";

        Utils.Entry(TAG, func, "UUID: " + uuid);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> uuids = preferences.getStringSet(Constants.APP_SHOW_SUBSCRIPTIONS_PUSH, null);

        if (null == uuids) {
            uuids = new TreeSet<String>();
        }

        if (!uuids.contains(uuid)) {
            uuids.add(uuid);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(Constants.APP_SHOW_SUBSCRIPTIONS_PUSH, uuids);
            editor.commit();
        }

        Utils.Exit(TAG, func);
    }

    public static void removeSubscriptionsPush(Context context, String uuid) {
        final String func = "removeSubscriptionsPush()";

        Utils.Entry(TAG, func, "UUID: " + uuid);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> uuids = preferences.getStringSet(Constants.APP_SHOW_SUBSCRIPTIONS_PUSH, null);

        if (null == uuids) {
            Utils.Exit(TAG, func, "Uuids is null");
            return;
        }

        uuids.remove(uuid);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(Constants.APP_SHOW_SUBSCRIPTIONS_PUSH, uuids);
        editor.commit();

        Utils.Exit(TAG, func);
    }

    public static boolean isSubscriptionsPush(Context context, String uuid) {
        final String func = "isSubscriptionsPush()";

        Utils.Entry(TAG, func, "UUID: " + uuid);

        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> uuids = preferences.getStringSet(Constants.APP_SHOW_SUBSCRIPTIONS_PUSH, null);

        if (null != uuids && uuids.contains(uuid)) {
            Utils.Exit(TAG, func, "Found UUID: " + uuid);
            return true;
        }

        Utils.Exit(TAG, func, "UUID: " + uuid + " not found");

        return false;
    }
}
