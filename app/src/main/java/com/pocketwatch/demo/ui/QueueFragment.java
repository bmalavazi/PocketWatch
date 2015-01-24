package com.pocketwatch.demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.Preferences;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.adapters.SubscriptionAdapter;
import com.pocketwatch.demo.models.Subscription;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/22/14.
 */
public class QueueFragment extends BaseTabFragment {
    private static final String TAG = "QueueFragment";
    private View mView;
    private ListView mSubscriptionView;
    private List<Subscription> mSubscriptionList = new ArrayList<Subscription>();
    private SubscriptionAdapter mSubscriptionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String func = "onCreate()";

        Utils.Entry(TAG, func);

        mSubscriptionAdapter = new SubscriptionAdapter(getActivity(), R.id.subscription_list, mSubscriptionList);

        Utils.Exit(TAG, func);
    }

    @Override
    public void onResume() {
        super.onResume();

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mSubscriptionAdapter.clear();
                mSubscriptionList = Subscription.getSubscriptions(json);
                for (Subscription subscription : mSubscriptionList) {
                    mSubscriptionAdapter.add(subscription);
                    if (subscription.isReceivePushNotifications()) {
                        Preferences.setSubscriptions(getActivity(), subscription.getUuid());
                    }
                }
                mSubscriptionAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getSubscriptions());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);

        mSubscriptionView = (ListView) mView.findViewById(R.id.subscription_list);
        mSubscriptionView.setAdapter(mSubscriptionAdapter);
        mSubscriptionAdapter.notifyDataSetChanged();

        return mView;
    }

    @Override
    public int getLayoutId() {
        return R.layout.queue_layout;
    }

    public static String getTAG() {
        return TAG;
    }
}
