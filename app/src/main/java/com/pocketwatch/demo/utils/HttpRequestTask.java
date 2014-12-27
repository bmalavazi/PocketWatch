package com.pocketwatch.demo.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.pocketwatch.demo.model.JsonCallback;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by bmalavazi on 12/24/14.
 */
public class HttpRequestTask extends AsyncTask<String, Integer, JSONObject> {
    private static final String TAG = "HttpRequestTask";
    protected JsonCallback mCallback;

    public HttpRequestTask(JsonCallback callback) {
        mCallback = callback;
    }

    @Override
    protected JSONObject doInBackground(String... uri) {
        final String func = "AsyncTask<>:doInBackground()";
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        JSONObject json = null;

        Utils.Entry(TAG, func, uri[0]);

        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (HttpStatus.SC_OK == statusCode) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                response.getEntity().writeTo(out);
                out.close();

                Log.d(TAG, "Response: " + out.toString());

                if (true == Utils.isEmpty(out.toString()))
                    Utils.Debug(TAG, func, "Received empty HTTP response");
                else
                    json = new JSONObject(out.toString());
            } else {
                //Closes the connection.
                Utils.Debug(TAG, func, "HTTP Status Code: " + statusCode);
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (ClientProtocolException e) {
            Utils.Exit(TAG, func, "Client Protocol Exception");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Utils.Exit(TAG, func, "IOException");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Utils.Exit(TAG, func, "JSONException");
            e.printStackTrace();
            return null;
        }

        Utils.Exit(TAG, func);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        final String func = "AsyncTask<>:onPostExecute()";

        Utils.Entry(TAG, func);

        if (null == result) {
            Utils.Exit(TAG, func, "JSON Response is NULL");
            return;
        }

        mCallback.setJsonObject(result);

        Utils.Exit(TAG, func);
    }
}