package com.pocketwatch.demo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.Preferences;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.models.Channel;
import com.pocketwatch.demo.receivers.GcmBroadcastReceiver;
import com.pocketwatch.demo.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bmalavazi on 12/22/14.
 */
public class HomeActivity extends BaseTabActivity {
    private static final String TAG = "HomeActivity";
    private List<Channel> mChannelList = new ArrayList<Channel>();
    private List<String> mChannelUuidList = new ArrayList<String>();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    protected String SENDER_ID = Constants.GCM_SENDER_ID;
    private GoogleCloudMessaging mGcm;
    private String mRegistrationId;
    private GcmBroadcastReceiver mGcmBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String func = "onCreate()";

        Utils.Entry(TAG, func);
/*
        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mChannelList = Channel.getChannels(json);

                for (Channel channel : mChannelList) {
                    mChannelUuidList.add(channel.getUuid());
                }
            }
        }).execute(Utils.getChannels());
*/
        //if (checkPlayServices())
        if (checkPlayServices()) {
            Utils.Debug(TAG, func, "Google Play Services available");
            mRegistrationId = Preferences.getRegistrationId(getApplicationContext());

            if (Utils.isEmpty(mRegistrationId)) {
                registerInBackground();
            } else
                sendGcm();
        } else {
            Utils.Debug(TAG, func, "Google Play Services NOT available");
        }

        Utils.Exit(TAG, func);
    }

    @Override
    protected List<TabSpec> getTabSpecs() {
        ArrayList<TabSpec> tabs = new ArrayList<TabSpec>();

        tabs.add(new TabSpec(getResources().getString(R.string.tab_shows),
                             R.string.tab_shows,
                             ShowsFragment.class));

        tabs.add(new TabSpec(getResources().getString(R.string.tab_queue),
                             R.string.tab_queue,
                             QueueFragment.class));

        return tabs;
    }

    @Override
    protected List<String> getTabTags() {
        ArrayList<String> tabs = new ArrayList<String>();
        tabs.add("Subscriptions");
        tabs.add("Shows");

        return tabs;
    }

    private void registerInBackground() {
        final String func = "registerInBackground()";

        (new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                final String func = "registerInBackground()::doInBackground()";

                Utils.Entry(TAG, func);

                try {
                    if (null == mGcm) {
                        mGcm = GoogleCloudMessaging.getInstance(HomeActivity.this);
                    }
                    mRegistrationId = mGcm.register(SENDER_ID);
                    Utils.Debug(TAG, func, "Current Device's Registration ID is: " + mRegistrationId);
                    Preferences.saveRegistrationId(getApplicationContext(), mRegistrationId);
                } catch (IOException ex) {
                    Utils.Debug(TAG, func, "Registration ID Error:" + ex.getMessage());
                }

                sendGcm();
                Utils.Exit(TAG, func);

                return null;
            }
        }).execute(null, null, null);
    }

    private void sendGcm() {
        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                final String func = "doInBackground(): Send";

                Utils.Entry(TAG, func);

                String msg = "";
                AtomicInteger msgId = new AtomicInteger();
                if (null == mGcm) {
                    mGcm = GoogleCloudMessaging.getInstance(HomeActivity.this);
                }
                try {
                    Bundle data = new Bundle();
                    data.putString("my_message", "Hello World");
                    data.putString("my_action",
                            "com.google.android.gcm.demo.app.ECHO_NOW");
                    String id = Integer.toString(msgId.incrementAndGet());
                    mGcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
                    msg = "Sent message";
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }

                Utils.Exit(TAG, func, msg);

                return msg;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
            }
        }.execute(null, null, null);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
