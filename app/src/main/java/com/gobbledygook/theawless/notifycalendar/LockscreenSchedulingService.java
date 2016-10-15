package com.gobbledygook.theawless.notifycalendar;


import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class LockscreenSchedulingService extends Service {
    private static LockcreenReceiver lockcreenReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        lockcreenReceiver = new LockcreenReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(lockcreenReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(lockcreenReceiver);
        lockcreenReceiver = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}