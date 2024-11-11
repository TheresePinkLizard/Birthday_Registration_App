package com.s333329.mappe2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String CHANNEL_ID = "MinKanal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter;
        filter = new IntentFilter("com.s333329.mappe2.MITTSIGNAL"); // man lager eget signal
        filter.addAction("com.s333329.mappe2.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver,filter, Context.RECEIVER_EXPORTED);

        // Start the AMinService
        Intent intentservice = new Intent(this, AMinService.class);
        // denne koden skjønner at onstartcommand skal kjøres
        this.startService(intentservice);

        // Start the ASettPeriodiskService
        Intent intentperiodisk = new Intent(this,ASettPeriodiskService.class);
        this.startService(intentperiodisk);


    }
    public void goToRegisterfromHome(View v){
        Intent i = new Intent(this, RegisterBirthday.class);
        startActivity(i);
    }
    public void goToAllRegistered(View v){
        Intent i = new Intent(this, ListOverview.class);
        startActivity(i);
    }
    public void goToSettings(View v){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    // stopper service og kjører ondestroy kode i minservice
    public void stoppService(View v) {
        Intent i = new Intent(this, AMinService.class);
        // denne koden skjønner at nå skal ondestroy kalles i minservice
        stopService(i);
    }

    public void sendBroadcast(View v) {
        Intent intent = new Intent();
        intent.setAction("com.s333329.mappe2.MITTSIGNAL");
        sendBroadcast(intent);
    }

    public void stoppPeriodisk(View v){
        Intent i = new Intent(this, AMinService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarm != null){
            alarm.cancel(pintent);
        }
    }
    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new
                NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}