package com.pocketwatch.demo.models;

import android.util.Log;

import com.pocketwatch.demo.Callbacks.ModelParser;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

/**
 * Created by bmalavazi on 1/24/15.
 */
public class Metadata extends BaseModel  {
    private static final String TAG = "Metadata";
    private static final String POCKETWATCH_METADATA_KEY = "metadata";
    private static final String POCKETWATCH_METADATA_EPISODE_UUID = "episode_uuid";

    private String mUuid;

    public String getUuid() {
        return mUuid;
    }

    public static final Metadata getMetadata(JSONObject json) {
        Metadata metadata = new Metadata();

        try {
            metadata.mUuid = Utils.getJsonString(json, POCKETWATCH_METADATA_EPISODE_UUID);

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return metadata;
    }

    @Override
    public ModelParser getModelParser() {
        return null;
    }
}
