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
    private static final String POCKETWATCH_API_BASE = "http://104.200.20.15:3000/api/v1/";
    private static final String POCKETWATCH_SHOWS = POCKETWATCH_API_BASE + "channels?edges=shows";
    private static final String POCKETWATCH_EPISODE = POCKETWATCH_API_BASE + "episodes/";
    private static final String POCKETWATCH_EPISODES = POCKETWATCH_API_BASE + "shows/";
    private static final String POCKETWATCH_SHOW = POCKETWATCH_API_BASE + "shows/";


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

    public static String getEpisodes(String uuid) {
        return POCKETWATCH_EPISODES + uuid + "/episodes";
    }

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