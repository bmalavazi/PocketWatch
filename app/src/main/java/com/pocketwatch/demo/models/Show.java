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
 * Created by bmalavazi on 12/23/14.
 */
public class Show extends BaseModel {
    private static final String TAG = "Show";

    private static final String POCKETWATCH_SHOW_KEY_ARRAY = "shows";
    private static final String POCKETWATCH_SHOW_KEY_SINGLE = "show";
    private static final String POCKETWATCH_SHOW_SUBSCRIPTIONS = "subscriptions";
    private static final String POCKETWATCH_SHOW_ID = "id";
    private static final String POCKETWATCH_SHOW_UUID = "uuid";
    private static final String POCKETWATCH_SHOW_TITLE = "title";
    private static final String POCKETWATCH_SHOW_DESCRIPTION = "description";
    private static final String POCKETWATCH_SHOW_TILE_IMAGE_URL = "tile_image_url";
    private static final String POCKETWATCH_SHOW_CREATOR = "creator";
    private static final String POCKETWATCH_SHOW_COPYRIGHT = "copyright";
    private static final String POCKETWATCH_SHOW_BANNER_URL = "banner_url";
    private static final String POCKETWATCH_SHOW_THUMBNAILS = "thumbnails";
    private static final String POCKETWATCH_SHOW_BANNERS = "banners";
    private static final String POCKETWATCH_SHOW_EPISODE_COUNT = "episode_count";
    private static final String POCKETWATCH_SHOW_UNVIEWED_CONTENT = "has_unviewed_content";

    private static final String POCKETWATCH_SHOW_THUMBNAIL_NAME = "name";
    private static final String POCKETWATCH_SHOW_THUMBNAIL_URL = "url";
    private static final String POCKETWATCH_SHOW_THUMBNAIL_WIDTH = "width";
    private static final String POCKETWATCH_SHOW_THUMBNAIL_HEIGHT = "height";

    private static final String POCKETWATCH_SHOW_THUMBNAIL_IPHONE_5 = "iphone5";
    private static final String POCKETWATCH_SHOW_THUMBNAIL_IPHONE_6 = "iphone6";
    private static final String POCKETWATCH_SHOW_THUMBNAIL_IPHONE_6p = "iphone6p";

    private int mId;
    private String mUuid;
    private String mTitle;
    private String mDescription;
    private String mTileImageUrl;
    private String mCreator;
    private String mCopyright;
    private String mBannerUrl;
    private JSONArray mThumbnails;
    private JSONArray mBanners;
    private List<ShowThumbnail> mThumbnailList = new ArrayList<ShowThumbnail>();
    private List<ShowThumbnail> mBannerList = new ArrayList<ShowThumbnail>();
    private int mEpisodeCount;
    private boolean mUnviewedContent;

    public static class ShowThumbnail {
        private String mName;
        private String mUrl;
        private int mWidth;
        private int mHeight;

        public ShowThumbnail(String name, String url, int width, int height) {
            this.mName = name;
            this.mUrl = url;
            this.mWidth = width;
            this.mHeight = height;
        }

        public String getThumbnailName() { return mName; }
        public String getThumbnailUrl() { return mUrl; }
        public int getThumbnailWidth() { return mWidth; }
        public int getThumbnailHeight() { return mHeight; }
    }

    public static final List<ShowThumbnail> getThumbnails(JSONArray jar){
        List<ShowThumbnail> list = new ArrayList<ShowThumbnail>();
        final String func = "getThumbnails(JSONArray)";

        Utils.Entry(TAG, func);

        if (jar != null) {
            try {
                for (int i = 0; i < jar.length(); i++){
                    JSONObject json = ((JSONObject) jar.get(i));
                    if(json != null) {
                        list.add(new ShowThumbnail(Utils.getJsonString(json, POCKETWATCH_SHOW_THUMBNAIL_NAME),
                                                   Utils.getJsonString(json, POCKETWATCH_SHOW_THUMBNAIL_URL),
                                                   Utils.getJsonInt(json, POCKETWATCH_SHOW_THUMBNAIL_WIDTH),
                                                   Utils.getJsonInt(json, POCKETWATCH_SHOW_THUMBNAIL_HEIGHT)));
                    }
                }
            } catch (Exception e){
                Utils.Debug(TAG, func, "unable to build array list of thumbnails");
                Utils.Debug(TAG, func, e.getMessage());
            }
        }

        Utils.Exit(TAG, func);

        return list;
    }

    public static final Show getShow(JSONObject json) {
        Show show = new Show();

        try {
            show.mId = Utils.getJsonInt(json, POCKETWATCH_SHOW_ID);
            show.mUuid = Utils.getJsonString(json, POCKETWATCH_SHOW_UUID);
            show.mTitle = Utils.getJsonString(json, POCKETWATCH_SHOW_TITLE);
            show.mDescription = Utils.getJsonString(json, POCKETWATCH_SHOW_DESCRIPTION);
            show.mTileImageUrl = Utils.getJsonString(json, POCKETWATCH_SHOW_TILE_IMAGE_URL);
            show.mCreator = Utils.getJsonString(json, POCKETWATCH_SHOW_CREATOR);
            show.mCopyright = Utils.getJsonString(json, POCKETWATCH_SHOW_COPYRIGHT);
            show.mBannerUrl = Utils.getJsonString(json, POCKETWATCH_SHOW_BANNER_URL);
            show.mThumbnails = Utils.getJsonArray(json, POCKETWATCH_SHOW_THUMBNAILS);
            show.mThumbnailList = getThumbnails(show.mThumbnails);
            show.mBanners = Utils.getJsonArray(json, POCKETWATCH_SHOW_BANNERS);
            show.mBannerList = getThumbnails(show.mBanners);
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

    public List<ShowThumbnail> getThumbnailList() { return mThumbnailList; }

    public List<ShowThumbnail> getBannerList() { return mBannerList; }

    public String getCreator() { return mCreator; }

    public String getCopyright() { return mCopyright; }

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
