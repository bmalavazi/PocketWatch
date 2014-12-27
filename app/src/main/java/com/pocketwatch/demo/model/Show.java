package com.pocketwatch.demo.model;

import android.util.Log;

import com.pocketwatch.demo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/23/14.
 */
public class Show extends BaseModel {
    private static final String TAG = "Show";

    private static final String POCKETWATCH_SHOW_KEY_ARRAY = "shows";
    private static final String POCKETWATCH_SHOW_KEY_SINGLE = "show";
    private static final String POCKETWATCH_SHOW_ID = "id";
    private static final String POCKETWATCH_SHOW_UUID = "uuid";
    private static final String POCKETWATCH_SHOW_TITLE = "title";
    private static final String POCKETWATCH_SHOW_DESCRIPTION = "description";
    private static final String POCKETWATCH_SHOW_TILE_IMAGE_URL = "tile_image_url";
    private static final String POCKETWATCH_SHOW_BANNER_URL = "banner_url";
    private static final String POCKETWATCH_SHOW_EPISODE_COUNT = "episode_count";
    private static final String POCKETWATCH_SHOW_UNVIEWED_CONTENT = "has_unviewed_content";

    private int mId;
    private String mUuid;
    private String mTitle;
    private String mDescription;
    private String mTileImageUrl;
    private String mBannerUrl;
    private int mEpisodeCount;
    private boolean mUnviewedContent;

    public static final Show getShow(JSONObject json) {
        Show show = new Show();

        try {
            show.mId = Utils.getJsonInt(json, POCKETWATCH_SHOW_ID);
            show.mUuid = Utils.getJsonString(json, POCKETWATCH_SHOW_UUID);
            show.mTitle = Utils.getJsonString(json, POCKETWATCH_SHOW_TITLE);
            show.mDescription = Utils.getJsonString(json, POCKETWATCH_SHOW_DESCRIPTION);
            show.mTileImageUrl = Utils.getJsonString(json, POCKETWATCH_SHOW_TILE_IMAGE_URL);
            show.mBannerUrl = Utils.getJsonString(json, POCKETWATCH_SHOW_BANNER_URL);
            show.mEpisodeCount = Utils.getJsonInt(json, POCKETWATCH_SHOW_EPISODE_COUNT);
            show.mUnviewedContent = Utils.getJsonBoolean(json, POCKETWATCH_SHOW_UNVIEWED_CONTENT);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return show;
    }

    public static final Show getSingleShow(JSONObject json) {
        JSONObject jsonShow = null;
        Log.d(TAG, "getSingleShow(JSONObject)");
        try {
            jsonShow = Utils.getJsonObject(json, POCKETWATCH_SHOW_KEY_SINGLE);
        } catch (JSONException e) {
            throw new AssertionError();
        }
        return getShow(jsonShow);
    }

    public static final List<Show> getShows(JSONArray jar){
        Log.d(TAG, "getShows(JSONArray)");
        List<Show> list = new ArrayList<Show>();
        if(jar != null){
            Log.d(TAG, "getShows(JSONArray): Length: " + jar.length());
            try {
                for (int i = 0; i < jar.length(); i++){
                    Show show = getShow((JSONObject) jar.get(i));
                    if(show != null){
                        list.add(show);
                    }
                }
            } catch (Exception e){
                Log.e(TAG, "unable to build array list of shows", e);
            }
        }
        return list;
    }

    public static final List<Show> getShows(JSONObject json){
        JSONArray jar = null;
        Log.d(TAG, "getShows(JSONObject)");
        try {
            jar = Utils.getJsonArray(json, POCKETWATCH_SHOW_KEY_ARRAY);
        } catch (JSONException e) {
            throw new AssertionError();
        }
        return getShows(jar);
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

    public String getDescription() {
        return mDescription;
    }

    public String getTileImageUrl() {
        return mTileImageUrl;
    }

    public String getBannerUrl() {
        return mBannerUrl;
    }

    public int getEpisodeCount() {
        return mEpisodeCount;
    }

    public boolean isUnviewedContent() {
        return mUnviewedContent;
    }

    @Override
    public ModelParser getModelParser() {
        return new ModelParser(){
            @Override
            public List<Show> getBulk(JSONObject json){
                return Show.getShows(json);
            }
            @Override
            public Show getSingle(JSONObject json){
                return Show.getShow(json);
            }
        };
    }
}
