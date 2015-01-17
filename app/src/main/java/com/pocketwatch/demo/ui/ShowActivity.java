package com.pocketwatch.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.adapters.EpisodeAdapter;
import com.pocketwatch.demo.models.Episode;
import com.pocketwatch.demo.models.Show;
import com.pocketwatch.demo.utils.Calendar;
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
    //private ScrollView mScrollView;
    private LinearLayout mLinearLayout;
    private FrameLayout mFrameLayout;
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
        mLinearLayout = (LinearLayout) findViewById(R.id.show_placeholder);

        mShowBanner = (ImageView) findViewById(R.id.show_banner);
        mShowTitle = (TextView) findViewById(R.id.show_title);
        mShowDescription = (TextView) findViewById(R.id.show_description);
        //mFrameLayout = (FrameLayout) findViewById(R.id.episode_placeholder);

        //mScrollView = (ScrollView) findViewById(R.id.show_scroll);
/*
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
*/
        //mAllView = (ListView) findViewById(R.id.all_episodes_list);
/*
        mAllView.setOnTouchListener(new View.OnTouchListener() {
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

        //mFirstUnwatchedAdapter = new EpisodeAdapter(this, R.id.episode, mFirstUnwatchedList);
        //mUnwatchedAdapter = new EpisodeAdapter(this, R.id.episode, mUnwatchedList);
        mAllAdapter = new EpisodeAdapter(this, R.id.episode, mAllList);

        //mFirstUnwatchedView.setAdapter(mFirstUnwatchedAdapter);
        //mUnwatchedView.setAdapter(mUnwatchedAdapter);
        mAllView.setAdapter(mAllAdapter);
*/
        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mShow = Show.getSingleShow(json);

                ArrayList<String> imgUrls = new ArrayList<String>();
                //imgUrls.add(mShow.getBannerUrl());
                for (Show.ShowThumbnail thumbnail : mShow.getBannerList())
                    imgUrls.add(Utils.getThumbnail(thumbnail.getThumbnailUrl()));
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

                LayoutInflater inflater = LayoutInflater.from(ShowActivity.this);

                for (Episode episode : mAllList) {
                    ArrayList<String> imgUrls = new ArrayList<String>();

                    View view = inflater.inflate(R.layout.episodes_item, null, false);
                    ImageView image = (ImageView) view.findViewById(R.id.episode_image);
                    TextView title = (TextView) view.findViewById(R.id.episode_title);
                    TextView dateDuration = (TextView) view.findViewById(R.id.episode_date_duration);
                    TextView description = (TextView) view.findViewById(R.id.episode_description);

                    View divider = inflater.inflate(R.layout.divider, null, false);

                    title.setText(episode.getTitle());
                    Calendar calendar = new Calendar(episode.getCreationTime());
                    dateDuration.setText(calendar.getDateString() + " • " + Utils.getFormatDuration(episode.getDuration()));
                    //Log.d(TAG, "Raw Duration: " + episode.getDuration());
                    //Log.d(TAG, "Duration: " + Utils.getFormatDuration(episode.getDuration()));
                    description.setText(episode.getDescription());
                    //imgUrls.add(episode.getTileImageUrl());
                    for (Episode.EpisodeThumbnail thumbnail : episode.getThumbnailList())
                        imgUrls.add(Utils.getThumbnail(thumbnail.getThumbnailUrl()));
                    ImageLoader.loadImage(image, imgUrls, ImageLoader.getCache(), R.drawable.thumb_placeholder);

                    //view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

                    view.setOnClickListener(new SetOnClickListener(view, episode));

                    mLinearLayout.addView(view);
                    mLinearLayout.addView(divider);
                }

                View copyright = inflater.inflate(R.layout.copyright, null, false);
                TextView copyrightText = (TextView) copyright.findViewById(R.id.copyright);
                copyrightText.setText(mShow.getCopyright());

                mLinearLayout.addView(copyright);

/*
                ((ViewGroup)mFrameLayout).addView(layout);

                for (Episode episode : mAllList)
                    mAllAdapter.add(episode);
                mAllAdapter.notifyDataSetChanged();

                setListViewHeightBasedOnChildren(mAllView);
*/
            }
        }).execute(Utils.getShowEpisodes(mUuid));
    }

    private class SetOnClickListener implements View.OnClickListener {
        private View mView;
        private Episode mEpisode;

        public SetOnClickListener(View view, Episode episode) {
            mView = view;
            mEpisode = episode;
        }

        @Override
        public void onClick(View view) {
            final String func = "All Episodes: onItemClick()";

            Intent intent = new Intent(ShowActivity.this, EpisodeActivity.class);

            ArrayList<String> tabArray = mEpisode.getTabArray();

            Utils.Debug(TAG, func, "Tab Array Size: " + tabArray.size());

            intent.putExtra(EpisodeActivity.EPISODE_UUID, mEpisode.getUuid());
            intent.putStringArrayListExtra(EpisodeActivity.EPISODE_TABS, tabArray);
            startActivity(intent);
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        final String func = "setListViewBasedOnChildren()";
        ListAdapter listAdapter = listView.getAdapter();

        Utils.Entry(TAG, func);

        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            Utils.Debug(TAG, func, "Total Height: " + totalHeight);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
/*
        ViewGroup.LayoutParams scrollParams = mScrollView.getLayoutParams();
        scrollParams.height += params.height;
        mScrollView.setLayoutParams(scrollParams);
*/

        Utils.Exit(TAG, func);
    }
}
