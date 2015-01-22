package com.pocketwatch.demo.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.services.MessageReceivingService;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/22/14.
 */
public class HomeActivity extends BaseTabActivity {
    private static final String TAG = "HomeActivity";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String func = "onCreate()";

        Utils.Entry(TAG, func);

        if (checkPlayServices()) {
            Utils.Debug(TAG, func, "Google Play Services available");
            startService(new Intent(this, MessageReceivingService.class));
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

    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String func = "onResume()";

        Utils.Entry(TAG, func);

        String message = "";
        Intent intent = getIntent();
        if(intent!=null){
            Bundle extras = intent.getExtras();
            if(extras!=null){
                for(String key: extras.keySet()){
                    message+= key + "=" + extras.getString(key) + "\n";
                }
                Utils.Debug(TAG, func, message);
            }
        }

        Utils.Exit(TAG, func);
    }

    private boolean checkPlayServices() {
        final String func = "checkPlayServices()";

        Utils.Entry(TAG, func);

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Utils.Debug(TAG, func, "This device is not supported.");
                finish();
            }

            Utils.Exit(TAG, func);
            return false;
        }

        Utils.Exit(TAG, func);
        return true;
    }
}
