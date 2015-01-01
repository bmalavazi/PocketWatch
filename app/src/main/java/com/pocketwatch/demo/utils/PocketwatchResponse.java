package com.pocketwatch.demo.utils;

import com.pocketwatch.demo.models.BaseModel;
import com.pocketwatch.demo.Callbacks.ModelParser;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by bmalavazi on 12/24/14.
 */
public class PocketwatchResponse {
    List<? extends BaseModel> list;
    BaseModel item;
    ModelParser model;

    PocketwatchResponse(JSONObject json, ModelParser parser) {
        this.model = parser;
        list = model.getBulk(json);
    }
}
