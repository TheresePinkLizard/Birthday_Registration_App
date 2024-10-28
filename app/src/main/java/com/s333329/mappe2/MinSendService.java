package com.s333329.mappe2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MinSendService extends Service {

    private BirthdayDataSource dataSource;
    private List<Birthday> birthdaylist;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
        // Check the database and send out congratulations

        dataSource = new BirthdayDataSource(this);
        birthdaylist = dataSource.findAllBirthdays();

        // Get current date
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);

        // Prepare the date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for(Birthday bd : birthdaylist){

        }
*/
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;


    }

}

