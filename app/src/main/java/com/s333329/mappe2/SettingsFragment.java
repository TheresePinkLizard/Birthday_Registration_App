package com.s333329.mappe2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference timePreference = findPreference("preference_time");
        if (timePreference != null) {
            timePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.d("SettingsFragment", "Time preference clicked");
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), null, 8, 0, true);
                    timePickerDialog.show();
                    return true;
                }
            });
        } else{
            Log.d("SettingsFragment", "Time preference not found");
        }

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("sms")){
                    boolean sendSMS = sharedPreferences.getBoolean("sms",false);
                    if(sendSMS){
                        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                            sendBroadcast();
                            Toast.makeText(getActivity(),"Sms tjeneste er på",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        stoppPeriodisk();
                    }
                }
            }
        };
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("hour", hourOfDay);
                        editor.putInt("minute", minute);
                        editor.apply();
                    }
                }, 0, 0, true);
        timePickerDialog.show();
    }

    public void sendBroadcast(){
        Intent intent = new Intent();
        intent.setAction("com.example.s333329.mappe2.MITTSIGNAL");
        getActivity().sendBroadcast(intent);
    }

    public void stoppPeriodisk(){
        Intent i = new Intent(getActivity(), AMinService.class);
        PendingIntent pintent = PendingIntent.getService(getActivity(),0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        if(alarm != null){
            alarm.cancel(pintent);
            Toast.makeText(getActivity(),"SMS tjeneste er av", Toast.LENGTH_SHORT).show();
        }
    }
}
