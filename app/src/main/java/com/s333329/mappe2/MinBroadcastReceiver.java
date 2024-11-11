package com.s333329.mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MinBroadcastReceiver extends BroadcastReceiver {

    public MinBroadcastReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ASettPeriodiskService.class);
        context.startService(i);
    }
}

