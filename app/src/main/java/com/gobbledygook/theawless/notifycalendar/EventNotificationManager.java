package com.gobbledygook.theawless.notifycalendar;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.display.DisplayManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.Display;

class EventNotificationManager {
    private final static int notificationId = 0;
    private final SharedPreferences preferences;
    private final NotificationManager notificationManager;
    private Context context;

    EventNotificationManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    void handleNotification(String action) {
        if (action.equals(Intent.ACTION_USER_PRESENT)) {
            handleUnlock();
        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            handleLock();
        }
    }

    private void handleLock() {
        if (preferences.getBoolean(context.getString(R.string.lock_key), Boolean.parseBoolean(context.getString(R.string.lock_default)))) {
            show();
        } else {
            cancel();
        }
    }

    private void handleUnlock() {
        if (preferences.getBoolean(context.getString(R.string.unlock_key), Boolean.parseBoolean(context.getString(R.string.unlock_default)))) {
            show();
        } else {
            cancel();
        }
    }

    void handleNotification() {
        if (isScreenOn()) {
            if (isLocked()) {
                handleLock();
            } else {
                handleUnlock();
            }
        } else {
            handleLock();
        }
    }

    private boolean isLocked() {
        return ((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
    }

    private boolean isScreenOn() {
        for (Display display : ((DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE)).getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                return true;
            }
        }
        return false;
    }

    private void cancel() {
        notificationManager.cancel(notificationId);
    }

    private void show() {
        cancel();
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
