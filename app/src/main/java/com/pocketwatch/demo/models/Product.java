package com.pocketwatch.demo.models;

import android.util.Log;

import com.pocketwatch.demo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 1/1/15.
 */
public class Product {
    private static final String TAG = "Product";

    private static final String POCKETWATCH_PRODUCT_KEY_ARRAY = "products";
    private static final String POCKETWATCH_PRODUCT_KEY_SINGLE = "product";
    private static final String POCKETWATCH_PRODUCT_ID = "id";
    private static final String POCKETWATCH_PRODUCT_UUID = "uuid";
    private static final String POCKETWATCH_PRODUCT_EPISODE_ID = "episode_id";
    private static final String POCKETWATCH_PRODUCT_EPISODE_UUID = "episode_uuid";
    private static final String POCKETWATCH_PRODUCT_PRODUCT_TYPE = "product_type";
    private static final String POCKETWATCH_PRODUCT_NAME = "name";
    private static final String POCKETWATCH_PRODUCT_DESCRIPTION = "description";
    private static final String POCKETWATCH_PRODUCT_STORE_NAME = "store_name";
    private static final String POCKETWATCH_PRODUCT_PURCHASE_URL = "purchase_url";
    private static final String POCKETWATCH_PRODUCT_ITEM_IMAGE_URL = "item_image_url";
    private static final String POCKETWATCH_PRODUCT_PRICE = "price";
    private static final String POCKETWATCH_PRODUCT_THUMBNAILS = "thumbnails";
    private static final String POCKETWATCH_PRODUCT_CREATED_AT = "created_at";
    private static final String POCKETWATCH_PRODUCT_UPDATED_AT = "updated_at";

    private static final String POCKETWATCH_PRODUCT_THUMBNAIL_URL = "url";
    private static final String POCKETWATCH_PRODUCT_THUMBNAIL_WIDTH = "width";
    private static final String POCKETWATCH_PRODUCT_THUMBNAIL_HEIGHT = "height";

    private int mId;
    private String mUuid;
    private int mEpisodeId;
    private String mEpisodeUuid;
    private String mProductType;
    private String mName;
    private String mDescription;
    private String mStoreName;
    private String mPurchaseUrl;
    private String mItemImageUrl;
    private String mPrice;
    private JSONObject mThumbnails;
    private String mCreatedAt;
    private String mUpdatedAt;

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

    public String getProductType() {
        return mProductType;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public String getPurchaseUrl() {
        return mPurchaseUrl;
    }

    public String getItemImageUrl() {
        return mItemImageUrl;
    }

    public String getPrice() {
        return mPrice;
    }

    public JSONObject getThumbnails() {
        return mThumbnails;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public static final Product getProduct(JSONObject json) {
        Product product = new Product();

        try {
            product.mId = Utils.getJsonInt(json, POCKETWATCH_PRODUCT_ID);
            product.mUuid = Utils.getJsonString(json, POCKETWATCH_PRODUCT_UUID);
            product.mEpisodeId = Utils.getJsonInt(json, POCKETWATCH_PRODUCT_EPISODE_ID);
            product.mEpisodeUuid = Utils.getJsonString(json, POCKETWATCH_PRODUCT_EPISODE_UUID);
            product.mProductType = Utils.getJsonString(json, POCKETWATCH_PRODUCT_PRODUCT_TYPE);
            product.mName = Utils.getJsonString(json, POCKETWATCH_PRODUCT_NAME);
            product.mDescription = Utils.getJsonString(json, POCKETWATCH_PRODUCT_DESCRIPTION);
            product.mStoreName = Utils.getJsonString(json, POCKETWATCH_PRODUCT_STORE_NAME);
            product.mPurchaseUrl = Utils.getJsonString(json, POCKETWATCH_PRODUCT_PURCHASE_URL);
            product.mItemImageUrl = Utils.getJsonString(json, POCKETWATCH_PRODUCT_ITEM_IMAGE_URL);
            product.mPrice = Utils.getJsonString(json, POCKETWATCH_PRODUCT_PRICE);
            product.mThumbnails = Utils.getJsonObject(json, POCKETWATCH_PRODUCT_THUMBNAILS);
            product.mCreatedAt = Utils.getJsonString(json, POCKETWATCH_PRODUCT_CREATED_AT);
            product.mUpdatedAt = Utils.getJsonString(json, POCKETWATCH_PRODUCT_UPDATED_AT);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        return product;
    }

    public static final List<Product> getProducts(JSONArray jar){
        List<Product> list = new ArrayList<Product>();
        final String func = "getProducts(JSONArray)";

        Utils.Entry(TAG, func);

        if(jar != null){
            try {
                for (int i = 0; i < jar.length(); i++){
                    Product product = getProduct((JSONObject) jar.get(i));
                    if(product != null){
                        list.add(product);
                    }
                }
            } catch (Exception e){
                Utils.Debug(TAG, func, "unable to build array list of products");
                Utils.Debug(TAG, func, e.getMessage());
            }
        }

        Utils.Exit(TAG, func);

        return list;
    }

    public static final List<Product> getProducts(JSONObject json){
        JSONArray jar = null;
        final String func = "getProducts(JSONObject)";

        Utils.Entry(TAG, func);

        try {
            jar = Utils.getJsonArray(json, POCKETWATCH_PRODUCT_KEY_ARRAY);
        } catch (JSONException e) {
            throw new AssertionError();
        }

        Utils.Exit(TAG, func);

        return getProducts(jar);
    }
}
