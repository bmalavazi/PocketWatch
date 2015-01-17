package com.pocketwatch.demo.ui;

import android.os.Bundle;

import com.pocketwatch.demo.models.Channel;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/22/14.
 */
public class HomeActivity extends BaseTabActivity {
    private static final String TAG = "HomeActivity";
    private List<Channel> mChannelList = new ArrayList<Channel>();
    private List<String> mChannelUuidList = new ArrayList<String>();

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
}
