package com.pocketwatch.demo.utils;

import android.os.AsyncTask;

import com.pocketwatch.demo.Callbacks.HttpPostCallback;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by bmalavazi on 1/22/15.
 */
public class HttpPostTask extends AsyncTask<String, Integer, JSONObject> {
    private static final String TAG = "HttpPostTask";
    private HttpPostCallback mCallback;

    public HttpPostTask(HttpPostCallback callback) {
        mCallback = callback;
    }

    @Override
    protected JSONObject doInBackground(String... uri) {
        final String func = "AsyncTask<>:doInBackground()";
        HttpClient httpclient = new DefaultHttpClient();
        JSONObject json = null;

        Utils.Entry(TAG, func, uri[0]);

        try {
            httpclient.execute(new HttpPost(uri[0]));
        } catch (ClientProtocolException e) {
            Utils.Exit(TAG, func, "Client Protocol Exception");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Utils.Exit(TAG, func, "IOException");
            e.printStackTrace();
            return null;
        }

        Utils.Exit(TAG, func);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        mCallback.callback();
    }
}
