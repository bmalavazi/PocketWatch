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
public class Episode extends BaseModel {
    private static final String TAG = "Episode";

    private static final String POCKETWATCH_EPISODE_KEY_ARRAY = "episodes";
    private static final String POCKETWATCH_EPISODE_KEY_SINGLE = "episode";
    private static final String POCKETWATCH_EPISODE_ID = "id";
    private static final String POCKETWATCH_EPISODE_UUID = "uuid";
    private static final String POCKETWATCH_EPISODE_SHOW_ID = "show_id";
    private static final String POCKETWATCH_EPISODE_SHOW_UUID = "show_uuid";
    private static final String POCKETWATCH_EPISODE_SHOW_TITLE = "show_title";
    private static final String POCKETWATCH_EPISODE_TITLE = "title";
    private static final String POCKETWATCH_EPISODE_DESCRIPTION = "description";
    private static final String POCKETWATCH_EPISODE_NUMBER = "episode_number";
    private static final String POCKETWATCH_EPISODE_DURATION = "duration";
    private static final String POCKETWATCH_EPISODE_TILE_IMAGE_URL = "tile_image_url";
    private static final String POCKETWATCH_EPISODE_PLACEHOLDER_IMAGE_URL = "placeholder_image_url";
    private static final String POCKETWATCH_EPISODE_THUMBNAILS = "thumbnails";
    private static final String POCKETWATCH_EPISODE_VIDEO_URL = "video_url";
    private static final String POCKETWATCH_EPISODE_TAB_TYPES = "tab_types";
    private static final String POCKETWATCH_EPISODE_IS_UNVIEWED = "is_unviewed";
    private static final String POCKETWATCH_EPISODE_CREATED_AT = "created_at";
    private static final String POCKETWATCH_EPISODE_UPDATED_AT = "updated_at";

    private static final String POCKETWATCH_EPISODE_THUMBNAIL_NAME = "name";
    private static final String POCKETWATCH_EPISODE_THUMBNAIL_URL = "url";
    private static final String POCKETWATCH_EPISODE_THUMBNAIL_WIDTH = "width";
    private static final String POCKETWATCH_EPISODE_THUMBNAIL_HEIGHT = "height";

    private static final String POCKETWATCH_EPISODE_THUMBNAIL_IPHONE_5 = "iphone5";
    private static final String POCKETWATCH_EPISODE_THUMBNAIL_IPHONE_6 = "iphone6";
    private static final String POCKETWATCH_EPISODE_THUMBNAIL_IPHONE_6p = "iphone6p";

    private int mId;
    private String mUuid;
    private int mShowId;
    private String mShowUuid;
    private String mTitle;
    private String mShowTitle;
    private String mDescription;
    private int mEpisodeNumber;
    private int mDuration;
    private String mTileImageUrl;
    private String mPlaceholderUrl;
    private JSONArray mThumbnails;
    private List<EpisodeThumbnail> mThumbnailList = new ArrayList<EpisodeThumbnail>();
    private String mVideoUrl;
    private ArrayList<String> mTabArray = new ArrayList<String>();
    private boolean mIsViewed;
    private String mCreatedAt;
    private String mUpdatedAt;

    public static class EpisodeThumbnail {
        private String mName;
        private String mUrl;
        private int mWidth;
        private int mHeight;

        public EpisodeThumbnail(String name, String url, int width, int height) {
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

    public static final List<EpisodeThumbnail> getThumbnails(JSONArray jar){
        List<EpisodeThumbnail> list = new ArrayList<EpisodeThumbnail>();
        final String func = "getThumbnails(JSONArray)";

        Utils.Entry(TAG, func);

        if (jar != null) {
            try {
                for (int i = 0; i < jar.length(); i++){
                    JSONObject json = ((JSONObject) jar.get(i));
                    if(json != null) {
                        list.add(new EpisodeThumbnail(Utils.getJsonString(json, POCKETWATCH_EPISODE_THUMBNAIL_NAME),
                                                      Utils.getJsonString(json, POCKETWATCH_EPISODE_THUMBNAIL_URL),
                                                      Utils.getJsonInt(json, POCKETWATCH_EPISODE_THUMBNAIL_WIDTH),
                                                      Utils.getJsonInt(json, POCKETWATCH_EPISODE_THUMBNAIL_HEIGHT)));
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

    public static final Episode getEpisode(JSONObject json) {
        Episode episode = new Episode();

        try {
            episode.mId = Utils.getJsonInt(json, POCKETWATCH_EPISODE_ID);
            episode.mUuid = Utils.getJsonString(json, POCKETWATCH_EPISODE_UUID);
            episode.mShowId = Utils.getJsonInt(json, POCKETWATCH_EPISODE_SHOW_ID);
            episode.mShowUuid = Utils.getJsonString(json, POCKETWATCH_EPISODE_SHOW_UUID);
            episode.mTitle = Utils.getJsonString(json, POCKETWATCH_EPISODE_TITLE);
            episode.mShowTitle = Utils.getJsonString(json, POCKETWATCH_EPISODE_SHOW_TITLE);
            episode.mDescription = Utils.getJsonString(json, POCKETWATCH_EPISODE_DESCRIPTION);
            episode.mEpisodeNumber = Utils.getJsonInt(json, POCKETWATCH_EPISODE_NUMBER);
            episode.mDuration = Utils.getJsonInt(json, POCKETWATCH_EPISODE_DURATION);
            episode.mTileImageUrl = Utils.getJsonString(json, POCKETWATCH_EPISODE_TILE_IMAGE_URL);
            episode.mPlaceholderUrl = Utils.getJsonString(json, POCKETWATCH_EPISODE_PLACEHOLDER_IMAGE_URL);
            episode.mThumbnails = Utils.getJsonArray(json, POCKETWATCH_EPISODE_THUMBNAILS);
            episode.mThumbnailList = getThumbnails(episode.mThumbnails);
            episode.mVideoUrl = Utils.getJsonString(json, POCKETWATCH_EPISODE_VIDEO_URL);
            JSONArray tabArray = Utils.getJsonArray(json, POCKETWATCH_EPISODE_TAB_TYPES);
            episode.mTabArray = getEpisodeTabs(tabArray);
            episode.mIsViewed = Utils.getJsonBoolean(json, POCKETWATCH_EPISODE_IS_UNVIEWED);
            episode.mCreatedAt = Utils.getJsonString(json, POCKETWATCH_EPISODE_CREATED_AT);
            episode.mUpdatedAt = Utils.getJsonString(json, POCKETWATCH_EPISODE_UPDATED_AT);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return episode;
    }

    public static final Episode getSingleEpisode(JSONObject json) {
        JSONObject jsonEpisode = null;
        Log.d(TAG, "getSingleEpisode(JSONObject)");
        try {
            jsonEpisode = Utils.getJsonObject(json, POCKETWATCH_EPISODE_KEY_SINGLE);
        } catch (JSONException e) {
            throw new AssertionError();
        }
        return getEpisode(jsonEpisode);
    }

    public static final ArrayList<String> getEpisodeTabs(JSONArray jar){
        final String func = "getEpisodeTabs()";

        Utils.Entry(TAG, func);

        ArrayList<String> list = new ArrayList<String>();
        if(jar != null){
            try {
                for (int i = 0; i < jar.length(); i++){
                    String tab = Utils.getTabMap((String) jar.get(i));
                    if(tab != null){
                        Utils.Debug(TAG, func, "Tab: " + tab);
                        list.add(tab);
                    }
                }
            } catch (Exception e){
                Utils.Debug(TAG, func, "unable to build array list of tabs");
                Utils.Debug(TAG, func, e.getMessage());
            }
        }

        Utils.Exit(TAG, func);

        return list;
    }

    public static final List<Episode> getEpisodes(JSONArray jar){
        List<Episode> list = new ArrayList<Episode>();
        final String func = "getEpisodes(JSONArray)";

        Utils.Entry(TAG, func);

        if(jar != null){
            try {
                for (int i = 0; i < jar.length(); i++){
                    Episode episode = getEpisode((JSONObject) jar.get(i));
                    if(episode != null){
                        list.add(episode);
                    }
                }
            } catch (Exception e){
                Utils.Debug(TAG, func, "unable to build array list of episodes");
                Utils.Debug(TAG, func, e.getMessage());
            }
        }

        Utils.Exit(TAG, func);

        return list;
    }

    public static final List<Episode> getEpisodes(JSONObject json){
        JSONArray jar = null;
        final String func = "getEpisodes(JSONObject)";

        Utils.Entry(TAG, func);

        try {
            jar = Utils.getJsonArray(json, POCKETWATCH_EPISODE_KEY_ARRAY);
        } catch (JSONException e) {
            throw new AssertionError();
        }

        Utils.Exit(TAG, func);

        return getEpisodes(jar);
    }

    public int getId() {
        return mId;
    }

    public String getUuid() {
        return mUuid;
    }

    public int getShowId() {
        return mShowId;
    }

    public String getShowUuid() {
        return mShowUuid;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getDuration() {
        return mDuration;
    }

    public String getTileImageUrl() {
        return mTileImageUrl;
    }

    public String getPlaceholderUrl() {
        return mPlaceholderUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public ArrayList<String> getTabArray() { return mTabArray; }

    public boolean isViewed() {
        return mIsViewed;
    }

    public String getCreationTime() { return mCreatedAt; }

    public String getUpdateTime() { return mUpdatedAt; }

    public String getShowTitle() { return mShowTitle; }

    public int getEpisodeNumber() { return mEpisodeNumber; }

    public List<EpisodeThumbnail> getThumbnailList() { return mThumbnailList; }

    @Override
    public ModelParser getModelParser() {
        return new ModelParser(){
            @Override
            public List<Episode> getBulk(JSONObject json){
                return Episode.getEpisodes(json);
            }
            @Override
            public Episode getSingle(JSONObject json){
                return Episode.getEpisode(json);
            }
        };
    }
}
