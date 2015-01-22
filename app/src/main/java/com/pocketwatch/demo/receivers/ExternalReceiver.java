package com.pocketwatch.demo.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.pocketwatch.demo.Preferences;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.ui.HomeActivity;
import com.pocketwatch.demo.utils.Utils;

/**
 * Created by bmalavazi on 1/20/15.
 */
public class ExternalReceiver extends BroadcastReceiver {
    private static final String TAG = "ExternalReceiver";

    public void onReceive(Context context, Intent intent) {
        final String func = "onReceive()";

        Utils.Entry(TAG, func);

        if (null != intent) {
            Bundle extras = intent.getExtras();

            Intent notificationIntent = new Intent(context, HomeActivity.class);
            if (null != extras) {
                notificationIntent.putExtras(extras);
            }
            postNotification(notificationIntent, context);
        }

        Utils.Exit(TAG, func);
    }

    protected static void postNotification(Intent intentAction, Context context) {
        final String func = "postNotification()";

        Utils.Entry(TAG, func);

        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(intentAction);
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification notification = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Message Received!")
                .setContentText("")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .getNotification();

        mNotificationManager.notify(Preferences.getNotificationId(context), notification);

        Utils.Exit(TAG, func);
    }
}
