package com.pocketwatch.demo.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.widget.VideoView;

import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.model.Episode;
import com.pocketwatch.demo.model.JsonCallback;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bmalavazi on 12/25/14.
 */
public class EpisodeActivity extends FragmentActivity implements FragmentTabHost.OnTabChangeListener {
    private static final String TAG = "EpisodeActivity";
    public static final String EPISODE_UUID = "EPISODE_UUID";
    public static final String EPISODE_TABS = "EPISODE_TABS";
    private VideoView mVideoView;
    private String mUuid;
    private Episode mEpisode;
    private FragmentTabHost mTabHost;
    private HashMap<String, TabSpec> mFragmentMap = new HashMap<String, TabSpec>();

    ActionBar.TabListener mActionBarTabListener = new ActionBar.TabListener() {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
