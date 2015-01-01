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
 * Created by bmalavazi on 12/24/14.
 */
public class Channel extends BaseModel {
    private static final String TAG = "Channel";
    private static final String POCKETWATCH_CHANNEL_KEY_ARRAY = "channels";
    private static final String POCKETWATCH_CHANNEL_ID = "id";
    private static final String POCKETWATCH_CHANNEL_UUID = "uuid";
    private static final String POCKETWATCH_CHANNEL_TITLE = "title";
    private static final String POCKETWATCH_CHANNEL_SHOWS = "shows";

    private int mId;
    private String mUuid;
    private String mTitle;
    private JSONArray mShows;

    public static final Channel getChannel(JSONObject json) {
        Channel channel = new Channel();

        try {
            channel.mId = Utils.getJsonInt(json, POCKETWATCH_CHANNEL_ID);
            Log.d(TAG, "Channel ID: " + channel.mId);
            channel.mUuid = Utils.getJsonString(json, POCKETWATCH_CHANNEL_UUID);
            Log.d(TAG, "Channel UUID: " + channel.mUuid);
            channel.mTitle = Utils.getJsonString(json, POCKETWATCH_CHANNEL_TITLE);
            channel.mShows = Utils.getJsonArray(json, POCKETWATCH_CHANNEL_SHOWS);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return channel;
    }

    public static final List<Channel> getChannels(JSONArray jar){
        Log.d(TAG, "getChannels(JSONArray)");
        List<Channel> list = new ArrayList<Channel>();
        if(jar != null){
            Log.d(TAG, "getChannels(JSONArray): Length: " + jar.length());
            try {
                for (int i = 0; i < jar.length(); i++){
                    Channel channel = getChannel((JSONObject) jar.get(i));
                    if(channel != null){
                        list.add(channel);
                    }
                }
            } catch (Exception e){
                Log.e(TAG, "unable to build array list of shows", e);
            }
        }
        return list;
    }

    public static final List<Channel> getChannels(JSONObject json){
        JSONArray jar = null;
        Log.d(TAG, "getChannels(JSONObject)");
        try {
            jar = Utils.getJsonArray(json, POCKETWATCH_CHANNEL_KEY_ARRAY);
        } catch (JSONException e) {
            throw new AssertionError();
        }
        return getChannels(jar);
    }

    public int getId() {
        return mId;
    }

    public String getUuid() {
        return mUuid;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<Show> getShows() { return Show.getShows(mShows); }

    @Override
    public ModelParser getModelParser() {
        return new ModelParser(){
            @Override
            public List<Channel> getBulk(JSONObject json){
                return Channel.getChannels(json);
            }
            @Override
            public Channel getSingle(JSONObject json){
                return Channel.getChannel(json);
            }
        };
    }
}
