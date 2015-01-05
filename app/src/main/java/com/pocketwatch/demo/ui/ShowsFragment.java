package com.pocketwatch.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.adapters.ShowAdapter;
import com.pocketwatch.demo.adapters.TrendingEpisodeAdapter;
import com.pocketwatch.demo.models.Episode;
import com.pocketwatch.demo.models.Show;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/22/14.
 */
public class ShowsFragment extends BaseTabFragment {
    private static final String TAG = "ShowsFragment";
    private View mView;
    private HorizontalListView mFeaturedView;
    private HorizontalListView mTrendingView;
    private HorizontalListView mRecommendedView;
    private List<Show> mFeaturedList = new ArrayList<Show>();
    private List<Episode> mTrendingList = new ArrayList<Episode>();
    private List<Show> mRecommendedList = new ArrayList<Show>();
    private ShowAdapter mFeaturedAdapter;
    private TrendingEpisodeAdapter mTrendingAdapter;
    private ShowAdapter mRecommendedAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String func = "onCreate()";

        Utils.Entry(TAG, func);

        mFeaturedAdapter = new ShowAdapter(getActivity(), R.id.show, mFeaturedList);
        mTrendingAdapter = new TrendingEpisodeAdapter(getActivity(), R.id.trending, mTrendingList);
        mRecommendedAdapter = new ShowAdapter(getActivity(), R.id.show, mRecommendedList);

        Utils.Exit(TAG, func);
    }

    @Override
    public void onResume() {
        super.onResume();

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mFeaturedAdapter.clear();
                mFeaturedList = Show.getShows(json);
                for (Show show : mFeaturedList)
                    mFeaturedAdapter.add(show);
                mFeaturedAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getFeaturedShows());

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mTrendingAdapter.clear();
                mTrendingList = Episode.getEpisodes(json);
                for (Episode episode : mTrendingList)
                    mTrendingAdapter.add(episode);
                mTrendingAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getTrendingEpisodes());

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mRecommendedAdapter.clear();
                mRecommendedList = Show.getShows(json);
                for (Show show : mRecommendedList)
                    mRecommendedAdapter.add(show);
                mRecommendedAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getRecommendedShows());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);

        mFeaturedView = (HorizontalListView) mView.findViewById(R.id.featured_shows);
        mFeaturedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String func = "Featured Shows: onItemClick()";
                Utils.Entry(TAG, func, "Clicked position: " + position);

                Intent intent = new Intent(getActivity(), ShowActivity.class);

                ShowAdapter showAdapter = (ShowAdapter) adapterView.getAdapter();
                Show show = (Show) showAdapter.getItem(position);

                intent.putExtra(ShowActivity.SHOW_UUID, show.getUuid());
                startActivity(intent);
            }
        });
        mTrendingView = (HorizontalListView) mView.findViewById(R.id.trending_episodes);
        mTrendingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String func = "Trending Episodes: onItemClick()";
                Utils.Entry(TAG, func, "Clicked position: " + position);

                Intent intent = new Intent(getActivity(), EpisodeActivity.class);

                TrendingEpisodeAdapter episodeAdapter = (TrendingEpisodeAdapter) adapterView.getAdapter();
                Episode episode = (Episode) episodeAdapter.getItem(position);
                ArrayList<String> tabArray = episode.getTabArray();

                intent.putExtra(EpisodeActivity.EPISODE_UUID, episode.getUuid());
                intent.putStringArrayListExtra(EpisodeActivity.EPISODE_TABS, tabArray);
                startActivity(intent);
            }
        });
        mRecommendedView = (HorizontalListView) mView.findViewById(R.id.recommended_episodes);
        mRecommendedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String func = "Recommended Episodes: onItemClick()";
                Utils.Entry(TAG, func, "Clicked position: " + position);

                Intent intent = new Intent(getActivity(), ShowActivity.class);

                ShowAdapter showAdapter = (ShowAdapter) adapterView.getAdapter();
                Show show = (Show) showAdapter.getItem(position);

                intent.putExtra(ShowActivity.SHOW_UUID, show.getUuid());
                startActivity(intent);
            }
        });

        mFeaturedView.setAdapter(mFeaturedAdapter);
        mTrendingView.setAdapter(mTrendingAdapter);
        mRecommendedView.setAdapter(mRecommendedAdapter);

        mFeaturedAdapter.notifyDataSetChanged();
        mTrendingAdapter.notifyDataSetChanged();
        mRecommendedAdapter.notifyDataSetChanged();

        return mView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shows_layout;
    }

    public static String getTAG() {
        return TAG;
    }
}
