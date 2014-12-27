package com.pocketwatch.demo.model;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by bmalavazi on 12/24/14.
 */
public interface ModelParser {
    public List<? extends BaseModel> getBulk(JSONObject json);
    public BaseModel getSingle(JSONObject json);
}
