package com.gobbledygook.theawless.eventlock2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

class EventNotificationManager {
    private final static int notificationId = 0;

    void cancel(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (!preferences.getBoolean(context.getString(R.string.always_key), Boolean.parseBoolean(context.getString(R.string.always_default)))) {
            notificationManager.cancel(notificationId);
        }
    }

    void show(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
        String title = preferences.getString(context.getString(R.string.event_title_key), null);
        String time = preferences.getString(context.getString(R.string.event_time_key), null);
        //case when the calendar checking service didn't start
        if (title == null || time == null) {
            title = context.getString(R.string.start_text);
            time = "";
        }

        if (TextUtils.isEmpty(title)) {
            title = preferences.getString(context.getString(R.string.free_key), context.getString(R.string.free_default));
            if (TextUtils.isEmpty(title)) {
                //do not show any notification
                return;
            }
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(time)
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_EVENT)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setShowWhen(false);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
