package com.pocketwatch.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.pocketwatch.demo.adapter.ShowAdapter;
import com.pocketwatch.demo.model.Channel;
import com.pocketwatch.demo.model.JsonCallback;
import com.pocketwatch.demo.model.Show;
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
    private List<Show> mTrendingList = new ArrayList<Show>();
    private List<Show> mRecommendedList = new ArrayList<Show>();
    private ShowAdapter mFeaturedAdapter;
    private ShowAdapter mTrendingAdapter;
    private ShowAdapter mRecommendedAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFeaturedAdapter = new ShowAdapter(getActivity(), R.id.show, mFeaturedList);
        mTrendingAdapter = new ShowAdapter(getActivity(), R.id.show, mTrendingList);
        mRecommendedAdapter = new ShowAdapter(getActivity(), R.id.show, mRecommendedList);

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                List<Channel> channels = Channel.getChannels(json);
                mFeaturedList = channels.get(0).getShows();
                for (Show show : mFeaturedList)
                    mFeaturedAdapter.add(show);
                mFeaturedAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getShows());


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
            }
        });
        mRecommendedView = (HorizontalListView) mView.findViewById(R.id.recommended_episodes);
        mRecommendedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String func = "Recommended Episodes: onItemClick()";
                Utils.Entry(TAG, func, "Clicked position: " + position);
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
