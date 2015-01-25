package com.pocketwatch.demo.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.Preferences;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.models.Episode;
import com.pocketwatch.demo.models.Metadata;
import com.pocketwatch.demo.ui.EpisodeActivity;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bmalavazi on 1/20/15.
 */
public class ExternalReceiver extends BroadcastReceiver {
    private static final String TAG = "ExternalReceiver";

    public void onReceive(final Context context, Intent intent) {
        final String func = "onReceive()";

        Utils.Entry(TAG, func);

        if (null != intent) {
            final Bundle extras = intent.getExtras();

            //String messageType = gcm.getMessageType(intent);

            if (!extras.isEmpty()) {
                final String message = extras.getString(Constants.APP_PUSH_MESSAGE);
                if (null != message)
                    Utils.Debug(TAG, func, "Push Message Extra: " + message);

                String uuid = null;

                try {
                    JSONObject json = new JSONObject(extras.getString(Constants.APP_PUSH_METADATA));
                    if (null != json) {
                        Metadata metadata = Metadata.getMetadata(json);
                        uuid = metadata.getUuid();
                        Utils.Debug(TAG, func, "Episode UUID Extra: " + uuid);
                    }
                } catch (Exception e) {
                    Utils.Debug(TAG, func, e.getMessage());
                }

                if (Utils.isEmpty(message) || Utils.isEmpty(uuid)) {
                    Utils.Exit(TAG, func, "Message/UUID is NULL");
                    return;
                }

                new HttpRequestTask(new JsonCallback() {
                    @Override
                    public void setJsonObject(JSONObject json) {
                        Log.d(TAG, "setJsonObject()");
                        Episode episode = Episode.getSingleEpisode(json);

                        Intent notificationIntent = new Intent(context, EpisodeActivity.class);

                        ArrayList<String> tabArray = episode.getTabArray();
                        String uuid = episode.getUuid();

                        notificationIntent.putExtra(EpisodeActivity.EPISODE_UUID, uuid);
                        notificationIntent.putStringArrayListExtra(EpisodeActivity.EPISODE_TABS, tabArray);

                        postNotification(notificationIntent, context, message);
                    }
                }).execute(Utils.getEpisode(uuid));
            }
        }

        Utils.Exit(TAG, func);
    }

    protected static void postNotification(Intent intentAction, Context context, String message) {
        final String func = "postNotification()";

        Utils.Entry(TAG, func);

        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentAction, PendingIntent.FLAG_UPDATE_CURRENT); //stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification notification = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(message)
                .setContentText("")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .getNotification();

        mNotificationManager.notify(Preferences.getNotificationId(context), notification);

        Utils.Exit(TAG, func);
    }
}
