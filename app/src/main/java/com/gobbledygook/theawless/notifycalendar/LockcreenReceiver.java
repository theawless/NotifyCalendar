package com.gobbledygook.theawless.notifycalendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockcreenReceiver extends BroadcastReceiver {
    private final static String TAG = LockcreenReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.v(TAG, "on receive action:" + action);
        EventNotificationManager eventNotificationManager = new EventNotificationManager(context);
        eventNotificationManager.handleNotification(action);
    }
}
