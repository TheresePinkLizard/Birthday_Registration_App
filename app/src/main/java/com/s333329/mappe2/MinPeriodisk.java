package com.s333329.mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import java.util.Calendar;


public class MinPeriodisk extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MinPeriodisk","I starten av MinPeriodisk");
        //Tar verdien fra shared preferences og lager et kalender objekt som kan brukes i alarmManager
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String tidspunkt=prefs.getString("preference_time","08:00");
        String[] deler = tidspunkt.split(":");
        int time=Integer.parseInt(deler[0]);
        int minutter=Integer.parseInt(deler[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,time);
        cal.set(Calendar.MINUTE, minutter);
        cal.set(Calendar.SECOND,0);

        Intent i = new Intent(this, MinSendService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_IMMUTABLE);

        // setter alarm
        AlarmManager alarm =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintent);

        Log.i("MinPeriodisk","I slutten av MinPeriodisk");
        return super.onStartCommand(intent, flags, startId);
    }

}

