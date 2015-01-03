package com.pocketwatch.demo.models;

import android.util.Log;

import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bmalavazi on 12/30/14.
 */
public class SocialItem {
    private static final String TAG = "SocialItem";

    private static final String POCKETWATCH_SOCIAL_ITEM_KEY_ARRAY = "social_items";
    private static final String POCKETWATCH_SOCIAL_ITEM_KEY_SINGLE = "social_item";
    private static final String POCKETWATCH_SOCIAL_ITEM_ID = "id";
    private static final String POCKETWATCH_SOCIAL_ITEM_UUID = "uuid";
    private static final String POCKETWATCH_SOCIAL_ITEM_EPISODE_ID = "episode_id";
    private static final String POCKETWATCH_SOCIAL_ITEM_EPISODE_UUID = "episode_uuid";
    private static final String POCKETWATCH_SOCIAL_ITEM_PROVIDER = "provider";
    private static final String POCKETWATCH_SOCIAL_ITEM_CREATOR_NAME = "creator_name";
    private static final String POCKETWATCH_SOCIAL_ITEM_CREATOR_SCREEN_NAME = "creator_screen_name";
    private static final String POCKETWATCH_SOCIAL_ITEM_CONTENT = "content";
    private static final String POCKETWATCH_SOCIAL_ITEM_PERMALINK = "permalink";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES = "images";
    private static final String POCKETWATCH_SOCIAL_ITEM_VIDEO = "videos";
    private static final String POCKETWATCH_SOCIAL_ITEM_TIMESTAMP = "timestamp";

    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_AVATAR = "avatar";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_THUMBNAIL = "thumbnail";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_SMALL = "small";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_MEDIUM = "medium";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_LARGE = "large";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_STANDARD = "standard";

    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_URL = "url";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_WIDTH = "width";
    private static final String POCKETWATCH_SOCIAL_ITEM_IMAGES_HEIGHT = "height";

    public static final class SocialItemImage {
        private String mUrl;
        private int mWidth;
        private int mHeight;

        public SocialItemImage(String url, int width, int height) {
            this.mUrl = url;
            this.mWidth = width;
            this.mHeight = height;
        }

        public String getSocialItemImageUrl() { return mUrl; }
        public int getSocialItemImageWidth() { return mWidth; }
        public int getSocialItemImageHeight() { return mHeight; }
    }

    private int mId;
    private String mUuid;
    private int mEpisodeId;
    private String mEpisodeUuid;
    private String mProvider;
    private String mCreatorName;
    private String mCreatorScreenName;
    private String mContent;
    private String mPermalink;
    private JSONObject mImages;
    private HashMap<String, SocialItemImage> mSocialImageMap = new HashMap<String, SocialItemImage>();
    private JSONObject mVideos;
    private String mTimeStamp;
    private boolean mImagesPopulated = false;

    public int getId() {
        return mId;
    }

    public String getUuid() {
        return mUuid;
    }

    public int getEpisodeId() {
        return mEpisodeId;
    }

    public String getEpisodeUuid() {
        return mEpisodeUuid;
    }

    public String getProvider() {
        return mProvider;
    }

    public Constants.SOCIAL_ITEMS_PROVIDER getProviderEnum() {
        Constants.SOCIAL_ITEMS_PROVIDER provider = Constants.SOCIAL_ITEMS_PROVIDER.UNKNOWN;

        if (mProvider.equals(Constants.SOCIAL_ITEM_PROVIDER_INSTAGRAM))
            provider = Constants.SOCIAL_ITEMS_PROVIDER.INSTAGRAM;
        //XXX: TODO add remaining providers

        return provider;
    }

    public String getCreatorName() {
        return mCreatorName;
    }

    public String getCreatorScreenName() { return mCreatorScreenName; }

    public String getContent() { return mContent; }

    public String getPermalink() { return mPermalink; }

    public JSONObject getImages() { return mImages; }

    public JSONObject getVideos() { return mVideos; }

    public String getTimestamp() { return mTimeStamp; }

    public static final SocialItem getSocialItem(JSONObject json) {
        SocialItem socialItem = new SocialItem();

        try {
            socialItem.mId = Utils.getJsonInt(json, POCKETWATCH_SOCIAL_ITEM_ID);
            socialItem.mUuid = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_UUID);
            socialItem.mEpisodeId = Utils.getJsonInt(json, POCKETWATCH_SOCIAL_ITEM_EPISODE_ID);
            socialItem.mEpisodeUuid = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_EPISODE_UUID);
            socialItem.mProvider = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_PROVIDER);
            socialItem.mCreatorName = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_CREATOR_NAME);
            socialItem.mCreatorScreenName = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_CREATOR_SCREEN_NAME);
            socialItem.mContent = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_CONTENT);
            socialItem.mPermalink = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_PERMALINK);
            socialItem.mImages = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_IMAGES);
            socialItem.mVideos = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_VIDEO);
            socialItem.mTimeStamp = Utils.getJsonString(json, POCKETWATCH_SOCIAL_ITEM_TIMESTAMP);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return socialItem;
    }

    public SocialItemImage getAvatarImage() {
        populateSocialItemImages(mImages);
        return mSocialImageMap.get(POCKETWATCH_SOCIAL_ITEM_IMAGES_AVATAR);
    }

    public SocialItemImage getThumbnailImage() {
        populateSocialItemImages(mImages);
        return mSocialImageMap.get(POCKETWATCH_SOCIAL_ITEM_IMAGES_THUMBNAIL);
    }

    public SocialItemImage getSmallImage() {
        populateSocialItemImages(mImages);
        return mSocialImageMap.get(POCKETWATCH_SOCIAL_ITEM_IMAGES_SMALL);
    }

    public SocialItemImage getMediumImage() {
        populateSocialItemImages(mImages);
        return mSocialImageMap.get(POCKETWATCH_SOCIAL_ITEM_IMAGES_MEDIUM);
    }

    public SocialItemImage getLargeImage() {
        populateSocialItemImages(mImages);
        return mSocialImageMap.get(POCKETWATCH_SOCIAL_ITEM_IMAGES_LARGE);
    }

    public SocialItemImage getStandardImage() {
        populateSocialItemImages(mImages);
        return mSocialImageMap.get(POCKETWATCH_SOCIAL_ITEM_IMAGES_STANDARD);
    }

    private void populateSocialItemImages(JSONObject json) {
        final String func = "populateSocialItemImages()";
        JSONObject imageObject = null;

        if (mImagesPopulated)
            return;

        Utils.Entry(TAG, func);

        try {
            if (null != (imageObject = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_IMAGES_AVATAR))) {
                String url = Utils.getJsonString(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_URL);
                int width = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_WIDTH);
                int height = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_HEIGHT);

                if (!Utils.isEmpty(url)) {
                    Utils.Debug(TAG, func, "Populated Avatar. Width: " + width + " Height: " + height);
                    mSocialImageMap.put(POCKETWATCH_SOCIAL_ITEM_IMAGES_AVATAR, new SocialItemImage(url, width, height));
                }
            }

            if (null != (imageObject = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_IMAGES_THUMBNAIL))) {
                String url = Utils.getJsonString(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_URL);
                int width = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_WIDTH);
                int height = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_HEIGHT);

                if (!Utils.isEmpty(url)) {
                    Utils.Debug(TAG, func, "Populated Thumbnail. Width: " + width + " Height: " + height);
                    mSocialImageMap.put(POCKETWATCH_SOCIAL_ITEM_IMAGES_THUMBNAIL, new SocialItemImage(url, width, height));
                }
            }

            if (null != (imageObject = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_IMAGES_SMALL))) {
                String url = Utils.getJsonString(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_URL);
                int width = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_WIDTH);
                int height = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_HEIGHT);

                if (!Utils.isEmpty(url)) {
                    Utils.Debug(TAG, func, "Populated Small. Width: " + width + " Height: " + height);
                    mSocialImageMap.put(POCKETWATCH_SOCIAL_ITEM_IMAGES_SMALL, new SocialItemImage(url, width, height));
                }
            }

            if (null != (imageObject = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_IMAGES_MEDIUM))) {
                String url = Utils.getJsonString(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_URL);
                int width = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_WIDTH);
                int height = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_HEIGHT);

                if (!Utils.isEmpty(url)) {
                    Utils.Debug(TAG, func, "Populated Medium. Width: " + width + " Height: " + height);
                    mSocialImageMap.put(POCKETWATCH_SOCIAL_ITEM_IMAGES_MEDIUM, new SocialItemImage(url, width, height));
                }
            }

            if (null != (imageObject = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_IMAGES_LARGE))) {
                String url = Utils.getJsonString(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_URL);
                int width = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_WIDTH);
                int height = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_HEIGHT);

                if (!Utils.isEmpty(url)) {
                    Utils.Debug(TAG, func, "Populated Large. Width: " + width + " Height: " + height);
                    mSocialImageMap.put(POCKETWATCH_SOCIAL_ITEM_IMAGES_LARGE, new SocialItemImage(url, width, height));
                }
            }

            if (null != (imageObject = Utils.getJsonObject(json, POCKETWATCH_SOCIAL_ITEM_IMAGES_STANDARD))) {
                String url = Utils.getJsonString(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_URL);
                int width = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_WIDTH);
                int height = Utils.getJsonInt(imageObject, POCKETWATCH_SOCIAL_ITEM_IMAGES_HEIGHT);

                if (!Utils.isEmpty(url)) {
                    Utils.Debug(TAG, func, "Populated Standard. Width: " + width + " Height: " + height);
                    mSocialImageMap.put(POCKETWATCH_SOCIAL_ITEM_IMAGES_STANDARD, new SocialItemImage(url, width, height));
                }
            }

            mImagesPopulated = true;

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        Utils.Exit(TAG, func);
    }

    public static final List<SocialItem> getSocialItems(JSONArray jar){
        List<SocialItem> list = new ArrayList<SocialItem>();
        final String func = "getSocialItems(JSONArray)";

        Utils.Entry(TAG, func);

        if(jar != null){
            try {
                for (int i = 0; i < jar.length(); i++){
                    SocialItem socialItem = getSocialItem((JSONObject) jar.get(i));
                    if(socialItem != null){
                        list.add(socialItem);
                    }
                }
            } catch (Exception e){
                Utils.Debug(TAG, func, "unable to build array list of social items");
                Utils.Debug(TAG, func, e.getMessage());
            }
        }

        Utils.Exit(TAG, func);

        return list;
    }

    public static final List<SocialItem> getSocialItems(JSONObject json){
        JSONArray jar = null;
        final String func = "getSocialItems(JSONObject)";

        Utils.Entry(TAG, func);

        try {
            jar = Utils.getJsonArray(json, POCKETWATCH_SOCIAL_ITEM_KEY_ARRAY);
        } catch (JSONException e) {
            throw new AssertionError();
        }

        Utils.Exit(TAG, func);

        return getSocialItems(jar);
    }
}
