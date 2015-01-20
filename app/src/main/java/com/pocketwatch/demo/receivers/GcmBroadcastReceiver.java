package com.pocketwatch.demo.receivers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.pocketwatch.demo.services.GcmIntentService;
import com.pocketwatch.demo.utils.Utils;

/**
 * Created by bmalavazi on 1/17/15.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = "GcmBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String func = "onReceive()";

        Utils.Entry(TAG, func);

        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                                               GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

        Utils.Exit(TAG, func);
    }
}
