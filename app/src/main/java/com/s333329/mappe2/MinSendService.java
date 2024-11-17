package com.s333329.mappe2;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MinSendService extends Service {

    private BirthdayDataSource dataSource;
    private List<Birthday> birthdayList;
    private List<Birthday> todaysBirthday = new ArrayList<>();
    private Birthday todayB;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    //lager service hvis det ikke eksisterer fra før
    public void onCreate() {
        super.onCreate();
        Log.d("Minservice", "Service laget");
    }
    // denne koden kjører uansett om service eksisterer allerede eller ikke. Toast vil bli vist
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MinService","er inni MinSendService");
        dataSource = new BirthdayDataSource(this);
        // sjekker om database eksisterer og hvis den gjør det, så åpne den
        try {
            if (dataSource != null){
                dataSource.open();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //henter database med bursdager
        birthdayList = dataSource.findAllBirthdays();

        // sjekker dagens bursdager og lagrer i todaysbirthdays liste
        checkBirthdays();
        return super.onStartCommand(intent, flags, startId);
    }


    private void sendNotification(Birthday bday){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notifikasjon = new NotificationCompat.Builder(this,"MinKanal").setContentTitle("BursdagsApp").setContentText("Bursdagshilsen er sendt til " + bday.getName()).setSmallIcon(R.mipmap.ic_launcher).setPriority(NotificationCompat.PRIORITY_DEFAULT).build();
        notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(88, notifikasjon);

    }
    //kode for å sende sms
    private void sendSms(){
        //henter standard melding og sms toggle verdi(setter også verdi til false ved oppstart)
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultMessage = preferences.getString("preference_default_message", "Gratulerer med dagen!");
        boolean sendSms = preferences.getBoolean("preference_send_sms", false);

        // hvis sms toggle er på så sendes sms til bursdager i listen
        if (sendSms) {
            for (Birthday todayb : todaysBirthday){
                Log.i("MinService","Henter fra dagens bursdag: " + todayb.getName());
                //sender sms
                String todaysNumber= todayb.getNumber();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(todaysNumber,null,defaultMessage,null,null);
                sendNotification(todayb);
                Log.i("MinSendService","Sender sms");
            } todaysBirthday.clear();
        }else {
            Toast.makeText(this, "Kunne ikke sende SMS, sjekk innstillinger",
                    Toast.LENGTH_SHORT).show();
            Log.i("MinSendService","Kunne ikke sende sms");
        }

    }
    @Override
    public void onDestroy() {
        if (dataSource != null){
            dataSource.close();
        }
        super.onDestroy();
        Log.d("Minservice", "Service fjernet");
    }



    // kode for å sjekke bursdager
    private void checkBirthdays(){
        // variabel for dagens dato
        Calendar today = Calendar.getInstance();
        Log.i("Minservice","Er inni checkbirthdays");

        for (Birthday bday : birthdayList) {
            //prøver å konvertere bursdagsdato til date/calendar
            try {
                // Lager en SimpleDateFormat instans med datoformat
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                // Parse bursdagsdato til Date objekt
                Date date = format.parse(bday.getDate());
                // Sette parsed date til Calendar objekt
                Calendar birthday = Calendar.getInstance();
                if (date != null) {
                    birthday.setTime(date);
                }
                // sjekk hvis det matcher
                if (birthday.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
                        && birthday.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
                    //Legges til i liste med dagens bursdager
                    todaysBirthday.add(bday);
                    Log.i("MinService",bday.getName() + " legges til i listen");
                }

            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("AMinService","Kunne ikke parse bursdagsdato, sørg for at formatet er riktig",e);
            }

        }
        // Sender sms
        sendSms();

    }


}