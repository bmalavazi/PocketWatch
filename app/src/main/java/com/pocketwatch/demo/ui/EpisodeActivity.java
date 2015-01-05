package com.pocketwatch.demo.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.widget.VideoView;

import com.pocketwatch.demo.Callbacks.EpisodeCallback;
import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.models.Episode;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bmalavazi on 12/25/14.
 */
public class EpisodeActivity extends FragmentActivity implements FragmentTabHost.OnTabChangeListener, EpisodeCallback {
    private static final String TAG = "EpisodeActivity";
    public static final String EPISODE_UUID = "EPISODE_UUID";
    public static final String EPISODE_TABS = "EPISODE_TABS";
    private VideoView mVideoView;
    private String mUuid;
    private Episode mEpisode;
    private FragmentTabHost mTabHost;
    private HashMap<String, TabSpec> mFragmentMap = new HashMap<String, TabSpec>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String func = "onCreate()";

        Utils.Entry(TAG, func);

        setContentView(R.layout.episode_activity);

        mUuid = getIntent().getExtras().getString(EPISODE_UUID);
        ArrayList<String> tabArray = getIntent().getExtras().getStringArrayList(EPISODE_TABS);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.setOnTabChangedListener(this);

        mFragmentMap.put(Constants.EPISODE_SOCIAL_MEDIA_TAB, SocialFragment.getTabSpec());
        mFragmentMap.put(Constants.EPISODE_CHAT_TAB, ChatFragment.getTabSpec());
        mFragmentMap.put(Constants.EPISODE_SHOPPING_TAB, ShopFragment.getTabSpec());

        for (String tab : tabArray) {
            if (mFragmentMap.containsKey(tab)) {
                Log.d(TAG, "Found TabSpec for: " + tab);
                TabSpec tabSpec = mFragmentMap.get(tab);
                mTabHost.addTab(mTabHost.newTabSpec(tabSpec.getTabTag()).setIndicator(getResources().getString(tabSpec.getTabIndicator())),
                                tabSpec.getTabClass(), null);
            }
        }

        mVideoView = (VideoView) findViewById(R.id.videoView);

        Utils.Exit(TAG, func);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mEpisode = Episode.getSingleEpisode(json);
                Uri uri = Uri.parse(mEpisode.getVideoUrl());

                mVideoView.setVideoURI(uri);
                mVideoView.start();
            }
        }).execute(Utils.getEpisode(mUuid));
    }

    @Override
    public void onTabChanged(String s) {

    }

    @Override
    public String getEpisodeUuid() {
        return mUuid;
    }
}
