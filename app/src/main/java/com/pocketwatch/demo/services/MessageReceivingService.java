package com.pocketwatch.demo.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.Preferences;
import com.pocketwatch.demo.utils.Utils;

import java.io.IOException;

/**
 * Created by bmalavazi on 1/20/15.
 */

public class MessageReceivingService extends Service {
    private static final String TAG = "MessageReceivingService";
    protected String SENDER_ID = Constants.GCM_SENDER_ID;
    private GoogleCloudMessaging mGcm;
    private String mRegistrationId;

    public void onCreate() {
        super.onCreate();
        final String func = "OnCreate()";

        Utils.Entry(TAG, func);

        mGcm = GoogleCloudMessaging.getInstance(getBaseContext());
        Utils.Debug(TAG, func, "Google Play Services available");
        mRegistrationId = Preferences.getRegistrationId(getApplicationContext());

        if (Utils.isEmpty(mRegistrationId)) {
            register();
        }

        Utils.Exit(TAG, func);
    }

    private void register() {
        (new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                final String func = "doInBackground()";

                Utils.Entry(TAG, func);

                try {
                    mRegistrationId = mGcm.register(SENDER_ID);
                    Utils.Debug(TAG, func, "Current Device's Registration ID is: " + mRegistrationId);
                    Preferences.setRegistrationId(getApplicationContext(), mRegistrationId);
                } catch (IOException ex) {
                    Utils.Debug(TAG, func, "Registration ID Error:" + ex.getMessage());
                }

                Utils.Exit(TAG, func);

                return null;
            }
        }).execute(null, null, null);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}