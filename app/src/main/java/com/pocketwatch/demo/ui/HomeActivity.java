package com.pocketwatch.demo.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pocketwatch.demo.models.Channel;
import com.pocketwatch.demo.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    protected String SENDER_ID = "751802367359";
    private GoogleCloudMessaging mGcm =null;
    private String mRegistrationId;

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
        if (ConnectionResult.SUCCESS == GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext())) {
            Utils.Debug(TAG, func, "Google Play Services available");
            registerInBackground();
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
                } catch (IOException ex) {
                    Utils.Debug(TAG, func, "Registration ID Error:" + ex.getMessage());
                }

                Utils.Exit(TAG, func);

                return null;
            }
        }).execute(null, null, null);
    }
}
