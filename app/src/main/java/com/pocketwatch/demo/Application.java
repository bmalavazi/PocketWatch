package com.pocketwatch.demo;

/**
 * Created by bmalavazi on 1/18/15.
 */
public class Application extends android.app.Application {

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
