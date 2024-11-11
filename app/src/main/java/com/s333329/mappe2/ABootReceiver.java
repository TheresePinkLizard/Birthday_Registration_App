package com.s333329.mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// får programmet til å starte når mobilen slår seg på
public class ABootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}