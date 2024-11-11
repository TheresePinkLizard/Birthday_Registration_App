package com.s333329.mappe2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AMinService extends Service {

    private BirthdayDataSource dataSource;
    private List<Birthday> birthdayList;
    private List<Birthday> todaysBirthday = new ArrayList<>();

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

        dataSource = new BirthdayDataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //henter database

        birthdayList = dataSource.findAllBirthdays();

        // sjekker dagens bursdager og lagrer i todaysbirthdays liste
        checkBirthdays();

        //henter default melding og sms toggle verdi(setter også default verdi flase)
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultMessage = preferences.getString("preference_default_message", "Gratulerer med dagen!");
        boolean sendSms = preferences.getBoolean("preference_send_sms", false);


        // hvis sms toggle er på så sendes sms til bursdager i listen
        if (sendSms) {
            for (Birthday todayb : todaysBirthday){
                Log.i("MinService","output i loop" + todayb.getName());
                sendSMSBirthdays(todayb.getNumber(), defaultMessage);
                //notifikasjon for å sende ut sms
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent i = new Intent(this,MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_IMMUTABLE);
                Notification notifikasjon = new NotificationCompat.Builder(this,"MinKanal").setContentTitle("Bursdagsvarsel").setContentText("Har sendt ut en gratulasjon til "+todayb.getName()+"!").setSmallIcon(R.mipmap.ic_launcher).setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pIntent).build();

                //For å få sendt notifikasjon til hver person må vi ha unik ID
                Random random = new Random();
                int uniqueID = random.nextInt(100);

                notifikasjon.flags |=Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(uniqueID,notifikasjon);
                todaysBirthday.clear();
            }
        }else {
            Toast.makeText(this, "Kunne ikke sende SMS",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        dataSource.close();
        super.onDestroy();
        Log.d("Minservice", "Service fjernet");
    }

    //kode for å sende sms
    private void sendSMSBirthdays(String phoneNumber,String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null,message,null,null);
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
                    todaysBirthday.add(bday);
                    Log.i("MinService",bday.getName());
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("AMinService","Kunne ikke parse bursdagsdato",e);
            }
        }
    }
}