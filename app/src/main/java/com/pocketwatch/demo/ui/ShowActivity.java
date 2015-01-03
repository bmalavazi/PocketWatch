package com.pocketwatch.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pocketwatch.demo.adapters.EpisodeAdapter;
import com.pocketwatch.demo.models.Episode;
import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.models.Show;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/25/14.
 */
public class ShowActivity extends Activity {
    private static final String TAG = "ShowActivity";
    public static final String SHOW_UUID = "SHOW_UUID";
    private String mUuid;
    private Show mShow;
    private ImageView mShowBanner;
    private TextView mShowTitle;
    private TextView mShowDescription;
    private ListView mFirstUnwatchedView;
    private ListView mUnwatchedView;
    private ListView mAllView;
    private List<Episode> mFirstUnwatchedList = new ArrayList<Episode>();
    private List<Episode> mUnwatchedList = new ArrayList<Episode>();
    private List<Episode> mAllList = new ArrayList<Episode>();
    private EpisodeAdapter mFirstUnwatchedAdapter;
    private EpisodeAdapter mUnwatchedAdapter;
    private EpisodeAdapter mAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_activity);

        mUuid = getIntent().getExtras().getString(SHOW_UUID);

        mShowBanner = (ImageView) findViewById(R.id.show_banner);
        mShowTitle = (TextView) findViewById(R.id.show_title);
        mShowDescription = (TextView) findViewById(R.id.show_description);

        mFirstUnwatchedView = (ListView) findViewById(R.id.unwatched_episode_list);

        mFirstUnwatchedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String func = "First Unwatched Episode: onItemClick()";
                Utils.Entry(TAG, func, "Clicked position: " + position);

                Intent intent = new Intent(ShowActivity.this, EpisodeActivity.class);

                EpisodeAdapter episodeAdapter = (EpisodeAdapter) adapterView.getAdapter();
                Episode episode = (Episode) episodeAdapter.getItem(position);
                ArrayList<String> tabArray = episode.getTabArray();

                intent.putExtra(EpisodeActivity.EPISODE_UUID, episode.getUuid());
                intent.putStringArrayListExtra(EpisodeActivity.EPISODE_TABS, tabArray);
                startActivity(intent);
            }
        });

        mUnwatchedView = (ListView) findViewById(R.id.unwatched_episodes_list);

        mUnwatchedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String func = "Unwatched Episodes: onItemClick()";
                Utils.Entry(TAG, func, "Clicked position: " + position);

                Intent intent = new Intent(ShowActivity.this, EpisodeActivity.class);

                EpisodeAdapter episodeAdapter = (EpisodeAdapter) adapterView.getAdapter();
                Episode episode = (Episode) episodeAdapter.getItem(position);
                ArrayList<String> tabArray = episode.getTabArray();

                intent.putExtra(EpisodeActivity.EPISODE_UUID, episode.getUuid());
                intent.putStringArrayListExtra(EpisodeActivity.EPISODE_TABS, tabArray);
                startActivity(intent);
            }
        });

        mAllView = (ListView) findViewById(R.id.all_episodes_list);
        mAllView.setOnTouchListener(new OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mAllView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String func = "All Episodes: onItemClick()";
                Utils.Entry(TAG, func, "Clicked position: " + position);

                Intent intent = new Intent(ShowActivity.this, EpisodeActivity.class);

                EpisodeAdapter episodeAdapter = (EpisodeAdapter) adapterView.getAdapter();
                Episode episode = (Episode) episodeAdapter.getItem(position);
                ArrayList<String> tabArray = episode.getTabArray();

                Utils.Debug(TAG, func, "Tab Array Size: " + tabArray.size());

                intent.putExtra(EpisodeActivity.EPISODE_UUID, episode.getUuid());
                intent.putStringArrayListExtra(EpisodeActivity.EPISODE_TABS, tabArray);
                startActivity(intent);
            }
        });

        mFirstUnwatchedAdapter = new EpisodeAdapter(this, R.id.episode, mFirstUnwatchedList);
        mUnwatchedAdapter = new EpisodeAdapter(this, R.id.episode, mUnwatchedList);
        mAllAdapter = new EpisodeAdapter(this, R.id.episode, mAllList);

        mFirstUnwatchedView.setAdapter(mFirstUnwatchedAdapter);
        mUnwatchedView.setAdapter(mUnwatchedAdapter);
        mAllView.setAdapter(mAllAdapter);

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mShow = Show.getSingleShow(json);

                ArrayList<String> imgUrls = new ArrayList<String>();
                imgUrls.add(mShow.getBannerUrl());
                ImageLoader.loadImage(mShowBanner, imgUrls, ImageLoader.getCache(), R.drawable.tile_placeholder);
                mShowTitle.setText(mShow.getTitle());
                mShowDescription.setText(mShow.getDescription());
            }
        }).execute(Utils.getShow(mUuid));

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mAllList = Episode.getEpisodes(json);
                Log.d(TAG, "Num Episodes: " + mAllList.size());

                for (Episode episode : mAllList)
                    mAllAdapter.add(episode);
                mAllAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getShowEpisodes(mUuid));
    }
}
