package com.s333329.mappe2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String CHANNEL_ID = "MinKanal";

    private static final int PERMISSION_REQUEST_SEND_SMS = 1;

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
        checkSmsPermission();
        createNotificationChannel();
        createBroadcast();


    }
    public void goToRegisterfromHome (View v){
        Intent i = new Intent(this, RegisterBirthday.class);
        startActivity(i);
    }
    public void goToAllRegistered (View v){
            Intent i = new Intent(this, ListOverview.class);
            startActivity(i);
    }
    public void goToSettings (View v){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    private void createNotificationChannel () {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(channel);
    }

    private void checkSmsPermission(){
        // Check if the SEND_SMS permission has been granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_SEND_SMS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the SMS
                Toast.makeText(this, "SMS-tillatelse gitt", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void createBroadcast(){
        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter;
        filter = new IntentFilter("com.s333329.mappe2.MITTSIGNAL"); // man lager eget signal
        filter.addAction("com.s333329.mappe2.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter, Context.RECEIVER_EXPORTED);

        // Starter broadcast
        Intent intent = new Intent();
        intent.setAction("com.s333329.mappe2.MITTSIGNAL");
        sendBroadcast(intent);
    }
}
