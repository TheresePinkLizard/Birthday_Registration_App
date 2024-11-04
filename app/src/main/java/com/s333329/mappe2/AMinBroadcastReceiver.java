package com.s333329.mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AMinBroadcastReceiver extends BroadcastReceiver {

    public AMinBroadcastReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "I BroadcastReceiver",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, AMinService.class);
        context.startService(i);
        Log.d("Minservice", "Test broadcast");
    }
}

