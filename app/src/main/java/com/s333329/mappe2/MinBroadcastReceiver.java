package com.s333329.mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MinBroadcastReceiver extends BroadcastReceiver {

    public MinBroadcastReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        // Starter periodisk sservice
        Intent i = new Intent(context, MinPeriodisk.class);
        context.startService(i);
        Log.i("MinbroadcastReceiver","i MinBroadcastReceiver");
    }
}

