package com.pocketwatch.demo.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by bmalavazi on 1/17/15.
 */
public class GcmIntentService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
