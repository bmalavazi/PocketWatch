package com.pocketwatch.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pocketwatch.demo.Callbacks.EpisodeCallback;
import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.adapters.SocialItemAdapter;
import com.pocketwatch.demo.models.SocialItem;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/26/14.
 */
public class SocialFragment extends Fragment {
    private static final String TAG = "SocialFragment";
    private EpisodeCallback mCallback;
    private String mUuid;
    private List<SocialItem> mSocialItems = new ArrayList<SocialItem>();
    private ListView mSocialItemView;
    private SocialItemAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String func = "onCreate()";

        mCallback = (EpisodeCallback) getActivity();
        mUuid = mCallback.getEpisodeUuid();

        mAdapter = new SocialItemAdapter(getActivity(), R.id.social, mSocialItems);

        Utils.Debug(TAG, func, "UUID: " + mUuid);
    }

    @Override
    public void onResume() {
        super.onResume();

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mAdapter.clear();
                mSocialItems = SocialItem.getSocialItems(json);

                for (SocialItem socialItem : mSocialItems)
                    mAdapter.add(socialItem);
                mAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getEpisodeSocialItems(mUuid));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social_fragment, container, false);
        mSocialItemView = (ListView) view.findViewById(R.id.social_item_list);
        mSocialItemView.setAdapter(mAdapter);

        return view;
    }

    public static TabSpec getTabSpec() {
        return new TabSpec(TAG, R.string.tab_social, SocialFragment.class);
    }
}
