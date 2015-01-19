package com.pocketwatch.demo.utils;

import android.util.Log;

import com.pocketwatch.demo.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by bmalavazi on 12/23/14.
 */
public class Utils {
    private static final String TAG = "Utils";
    /*
    private static final String POCKETWATCH_API_BASE = "http://104.200.20.15:3000/api/v1/";
    private static final String POCKETWATCH_SHOWS = POCKETWATCH_API_BASE + "channels?edges=shows";
    private static final String POCKETWATCH_EPISODE = POCKETWATCH_API_BASE + "episodes/";
    private static final String POCKETWATCH_EPISODES = POCKETWATCH_API_BASE + "shows/";
    private static final String POCKETWATCH_SHOW = POCKETWATCH_API_BASE + "shows/";
    private static final String POCKETWATCH_SOCIAL = "/social_items";
    private static final String POCKETWATCH_PRODUCTS = "/products";
    */

    //private static final String POCKETWATCH_API_URL = "http://104.200.20.15:3000";
    //private static final String POCKETWATCH_API_URL = "https://54.173.83.140";
    private static final String POCKETWATCH_API_URL = "https://getpocket.watch";
    private static final String POCKETWATCH_API_BASE = POCKETWATCH_API_URL + "/api/v1/";
    private static final String POCKETWATCH_API_CHANNELS = POCKETWATCH_API_BASE + "channels";
    private static final String POCKETWATCH_API_CHANNEL = POCKETWATCH_API_BASE + "channels/%s";
    private static final String POCKETWATCH_API_SHOWS = POCKETWATCH_API_BASE + "channels/%s/shows";
    private static final String POCKETWATCH_API_SHOWS_RECOMMENDED = POCKETWATCH_API_BASE + "shows/recommended";
    private static final String POCKETWATCH_API_SHOWS_FEATURED = POCKETWATCH_API_BASE + "shows/featured";
    private static final String POCKETWATCH_API_SHOW = POCKETWATCH_API_BASE + "shows/%s";
    private static final String POCKETWATCH_API_EPISODES = POCKETWATCH_API_BASE + "shows/%s/episodes";
    private static final String POCKETWATCH_API_EPISODE = POCKETWATCH_API_BASE + "episodes/%s";
    private static final String POCKETWATCH_API_EPISODES_TRENDING = POCKETWATCH_API_BASE + "episodes/trending";
    private static final String POCKETWATCH_API_SHOW_CHANNELS = POCKETWATCH_API_BASE + "shows/%s/channels";
    private static final String POCKETWATCH_API_PRODUCTS = POCKETWATCH_API_BASE + "episodes/%s/products";
    private static final String POCKETWATCH_API_SOCIAL_ITEMS = POCKETWATCH_API_BASE + "episodes/%s/social_items";
    private static final String POCKETWATCH_API_THUMBNAIL = POCKETWATCH_API_URL + "/%s";
    private static final String POCKETWATCH_API_USERS = "users/";
    private static final String POCKETWATCH_API_SUBSCRIPTIONS = POCKETWATCH_API_BASE + POCKETWATCH_API_USERS + "subscriptions";
    private static final String POCKETWATCH_API_SUBSCRIBE = POCKETWATCH_API_BASE + POCKETWATCH_API_USERS + "subscribe";
    private static final String POCKETWATCH_API_UNSUBSCRIBE = POCKETWATCH_API_BASE + POCKETWATCH_API_USERS + "unsubscribe";
    private static final String POCKETWATCH_API_API_KEY = "api_key=" + Constants.POCKETWATCH_API_KEY;
    private static final String POCKETWATCH_API_SHOW_ID = "show_id=";
    private static final String POCKETWATCH_API_FIRST_ARG = "?";
    private static final String POCKETWATCH_API_DELIMITER_ARG = "&";

    private static HashMap<String, String> EPISODE_TAB_MAPS = new HashMap<String, String>();

    static {
        EPISODE_TAB_MAPS.put(Constants.EPISODE_SOCIAL_MEDIA_KEY, Constants.EPISODE_SOCIAL_MEDIA_TAB);
        EPISODE_TAB_MAPS.put(Constants.EPISODE_SHOPPING_KEY, Constants.EPISODE_SHOPPING_TAB);
        EPISODE_TAB_MAPS.put(Constants.EPISODE_CHAT_KEY, Constants.EPISODE_CHAT_TAB);
    }

    public static String getTabMap(String key) {
        if (EPISODE_TAB_MAPS.containsKey(key))
            return EPISODE_TAB_MAPS.get(key);

        return null;
    }

    public static String getThumbnail(String path) {
        //String url = POCKETWATCH_API_THUMBNAIL;
        //url = String.format(url, path);

        return path;
    }

    public static String getChannels() {
        return POCKETWATCH_API_CHANNELS;
    }

    public static String getChannel(String uuid) {
        String url = POCKETWATCH_API_CHANNEL;
        url = String.format(url, uuid);

        return url;
    }

    public static String getRecommendedShows() {
        return POCKETWATCH_API_SHOWS_RECOMMENDED;
    }

    public static String getFeaturedShows() {
        return POCKETWATCH_API_SHOWS_FEATURED;
    }

    public static String getTrendingEpisodes() {
        return POCKETWATCH_API_EPISODES_TRENDING;
    }

    public static String getChannelShows(String uuid) {
        String url = POCKETWATCH_API_SHOWS;
        url = String.format(url, uuid);

        return url;
    }

    public static String getShow(String uuid) {
        String url = POCKETWATCH_API_SHOW;
        url = String.format(url, uuid);

        return url;
    }

    public static String getShowEpisodes(String uuid) {
        String url = POCKETWATCH_API_EPISODES;
        url = String.format(url, uuid);

        return url;
    }

    public static String getEpisode(String uuid) {
        String url = POCKETWATCH_API_EPISODE;
        url = String.format(url, uuid);

        return url;
    }

    public static String getShowChannels(String uuid) {
        String url = POCKETWATCH_API_SHOW_CHANNELS;
        url = String.format(url, uuid);

        return url;
    }

    public static String getEpisodeSocialItems(String uuid) {
        String url = POCKETWATCH_API_SOCIAL_ITEMS;
        url = String.format(url, uuid);

        return url;
    }

    public static String getEpisodeProducts(String uuid) {
        String url = POCKETWATCH_API_PRODUCTS;
        url = String.format(url, uuid);

        return url;
    }

    public static String getSubscriptions() {
        return POCKETWATCH_API_SUBSCRIPTIONS + POCKETWATCH_API_FIRST_ARG + POCKETWATCH_API_API_KEY;
    }

    /*
    public static String getShows() {
        return POCKETWATCH_SHOWS;
    }

    public static String getShow(String uuid) {
        return POCKETWATCH_SHOW + uuid;
    }

    public static String getEpisode(int episode) {
        return POCKETWATCH_EPISODE + episode;
    }

    public static String getEpisode(String uuid) {
        return POCKETWATCH_EPISODE + uuid;
    }

    public static String getSocialItems(String uuid) { return getEpisode(uuid) +  POCKETWATCH_SOCIAL; }

    public static String getProducts(String uuid) { return getEpisode(uuid) +  POCKETWATCH_PRODUCTS; }

    public static String getEpisodes(String uuid) {
        return POCKETWATCH_EPISODES + uuid + "/episodes";
    }
    */

    public static boolean isEmpty(String text) {
        if (null == text ||
            text.isEmpty()) {
            return true;
        }
        return false;
    }

    public static void Entry(String tag, String func) {
        Log.d(tag, func + ": Entry");
    }
    public static void Entry(String tag, String func, String message) { Log.d(tag, func + " Entry: " + message); }
    public static void Exit(String tag, String func) {
        Log.d(tag, func + ": Exit");
    }
    public static void Exit(String tag, String func, String message) { Log.d(tag, func + " Exit: " + message); }
    public static void Debug(String tag, String func, String message) { Log.d(tag, func + ": " + message); }

    public static String getFormatDuration(int duration) {
        String fmtDuration = null;
        int hours = duration / Constants.DURATION_HOUR;
        int minutes = (duration % Constants.DURATION_HOUR) / Constants.DURATION_MINUTE;
        int seconds = duration % Constants.DURATION_MINUTE;

        if (hours > 0) {
            fmtDuration = String.format("%02dH %02dM %02dS", hours, minutes, seconds);
        } else if (minutes > 0) {
            fmtDuration = String.format("%02dM %02dS", minutes, seconds);
        } else {
            fmtDuration = String.format("%02dS", seconds);
        }

        return fmtDuration;
    }

    public static String getFormattedUnwatched(int unwatched) {
        StringBuilder sb = new StringBuilder();

        switch (unwatched) {
            case 0:
                sb.append("You're all caught up.");
                break;
            case 1:
                sb.append("1 Unwatched Episode");
                break;
            default:
                sb.append(unwatched + " Unwatched Episodes");
                break;
        }

        return sb.toString();
    }

    public static boolean getJsonBoolean(JSONObject json, String name) throws JSONException{
        if(!json.isNull(name)){
            return json.getBoolean(name);
        }
        return false;
    }

    public static int getJsonInt(JSONObject json, String name) throws JSONException{
        if(!json.isNull(name)){
            return json.getInt(name);
        }
        return 0;
    }

    public static String getJsonString(JSONObject json, String name) throws JSONException{
        if(!json.isNull(name)){
            return json.getString(name);
        }
        return null;
    }

    public static JSONObject getJsonObject(JSONObject json, String name) throws JSONException{
        if(!json.isNull(name)){
            return json.getJSONObject(name);
        }
        return null;
    }

    public static JSONArray getJsonArray(JSONObject json, String name) throws JSONException{
        if(!json.isNull(name)){
            return json.getJSONArray(name);
        }
        return null;
    }

    public static double getJsonDouble(JSONObject json, String name) throws JSONException{
        if(!json.isNull(name)){
            return json.getDouble(name);
        }
        return 0;
    }
}