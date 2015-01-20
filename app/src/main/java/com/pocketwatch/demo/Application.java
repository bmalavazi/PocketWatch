package com.pocketwatch.demo;

/**
 * Created by bmalavazi on 1/18/15.
 */
public class Application extends android.app.Application {
    private static final String TAG = "Application";
    private static Application mInstance;

    public Application() {}

    public static Application getInstance() {
        if (null == mInstance) {
            mInstance = new Application();
        }
        return mInstance;
    }
}
