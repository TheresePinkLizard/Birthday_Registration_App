package com.s333329.mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.preference.PreferenceManager;

public class MinPeriodisk extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {/*
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, MinSendService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);

        // Get the time from SharedPreferences (replace with your own key and default value)
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long timeMillis = sharedPreferences.getLong("preference_time", System.currentTimeMillis());

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);

*/
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

