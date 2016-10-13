package com.gobbledygook.theawless.eventlock2;

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
        EventNotificationManager eventNotificationManager = new EventNotificationManager();
        if (action.equals(Intent.ACTION_USER_PRESENT)) {
            eventNotificationManager.cancel(context);
        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            eventNotificationManager.show(context);
        }
    }
}
