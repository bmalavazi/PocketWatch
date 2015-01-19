package com.pocketwatch.demo.models;

import android.util.Log;

import com.pocketwatch.demo.Callbacks.ModelParser;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 1/18/15.
 */
public class Subscription extends BaseModel {
    private static final String TAG = "Subscription";

    private static final String POCKETWATCH_SUBSCRIPTION_KEY_ARRAY = "subscriptions";
    private static final String POCKETWATCH_SUBSCRIPTION_KEY_SHOW = "show";
    private static final String POCKETWATCH_SUBSCRIPTION_ID = "id";
    private static final String POCKETWATCH_SUBSCRIPTION_UUID = "uuid";
    private static final String POCKETWATCH_SUBSCRIPTION_TYPE = "type";
    private static final String POCKETWATCH_SUBSCRIPTION_RECEIVE_PUSH = "receive_push_notifications";
    private static final String POCKETWATCH_SUBSCRIPTION_UNVIEWED_COUNT = "unviewed_episode_count";
    private static final String POCKETWATCH_SUBSCRIPTION_UNVIEWED_DURATION = "unviewed_episode_duration";
    private static final String POCKETWATCH_SUBSCRIPTION_UNVIEWED_UUIDS = "unviewed_episode_uuids";

    private int mId;
    private String mUuid;
    private Show mShow;
    private String mType;
    private boolean mReceivePushNotifications;
    private int mUnviewedEpisodeCount;
    private int mUnviewedEpisodeDuration;
    private List<String> mUnviewedUuids = new ArrayList<String>();

    public static final Subscription getSubscription(JSONObject json) {
         Subscription subscription = new Subscription();

        try {
            subscription.mId = Utils.getJsonInt(json, POCKETWATCH_SUBSCRIPTION_ID);
            subscription.mUuid = Utils.getJsonString(json, POCKETWATCH_SUBSCRIPTION_UUID);
            JSONObject show = Utils.getJsonObject(json, POCKETWATCH_SUBSCRIPTION_KEY_SHOW);
            subscription.mShow = Show.getShow(show);
            subscription.mType = Utils.getJsonString(json, POCKETWATCH_SUBSCRIPTION_TYPE);
            subscription.mReceivePushNotifications = Utils.getJsonBoolean(json, POCKETWATCH_SUBSCRIPTION_RECEIVE_PUSH);
            subscription.mUnviewedEpisodeCount = Utils.getJsonInt(json, POCKETWATCH_SUBSCRIPTION_UNVIEWED_COUNT);
            subscription.mUnviewedEpisodeDuration = Utils.getJsonInt(json, POCKETWATCH_SUBSCRIPTION_UNVIEWED_DURATION);
            JSONArray uuids =  Utils.getJsonArray(json, POCKETWATCH_SUBSCRIPTION_UNVIEWED_UUIDS);
            subscription.mUnviewedUuids = getEpisodeUuids(uuids);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return subscription;
    }

    public static final List<Subscription> getSubscriptions(JSONArray jar){
        Log.d(TAG, "getSubscriptions(JSONArray)");
        List<Subscription> list = new ArrayList<Subscription>();
        if(jar != null){
            Log.d(TAG, "getSubscriptions(JSONArray): Length: " + jar.length());
            try {
                for (int i = 0; i < jar.length(); i++){
                    Subscription subscription = getSubscription((JSONObject) jar.get(i));
                    if(subscription != null){
                        list.add(subscription);
                    }
                }
            } catch (Exception e){
                Log.e(TAG, "unable to build array list of subscriptions", e);
            }
        }
        return list;
    }

    public static final List<Subscription> getSubscriptions(JSONObject json){
        JSONArray jar = null;
        Log.d(TAG, "getSubscriptions(JSONObject)");
        try {
            jar = Utils.getJsonArray(json, POCKETWATCH_SUBSCRIPTION_KEY_ARRAY);
        } catch (JSONException e) {
            throw new AssertionError();
        }
        return getSubscriptions(jar);
    }

    public static final List<String> getEpisodeUuids(JSONArray jar){
        Log.d(TAG, "getEpisodeUuids(JSONArray)");
        List<String> list = new ArrayList<String>();
        if(jar != null){
            Log.d(TAG, "getEpisodeUuids(JSONArray): Length: " + jar.length());
            try {
                for (int i = 0; i < jar.length(); i++){
                    list.add((String) jar.get(i));
                }
            } catch (Exception e){
                Log.e(TAG, "unable to build array list of uuids", e);
            }
        }
        return list;
    }

    public int getId() {
        return mId;
    }

    public String getUuid() {
        return mUuid;
    }

    public Show getShow() {
        return mShow;
    }

    public String getType() {
        return mType;
    }

    public boolean isReceivePushNotifications() {
        return mReceivePushNotifications;
    }

    public int getUnviewedEpisodeCount() {
        return mUnviewedEpisodeCount;
    }

    public int getUnviewedEpisodeDuration() {
        return mUnviewedEpisodeDuration;
    }

    public List<String> getUnviewedUuids() {
        return mUnviewedUuids;
    }

    @Override
    public ModelParser getModelParser() {
        return null;
    }
}
