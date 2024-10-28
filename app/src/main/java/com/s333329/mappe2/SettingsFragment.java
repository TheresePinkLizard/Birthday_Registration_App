package com.s333329.mappe2;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Preference timePreference = findPreference("preference_time");
        if (timePreference != null) {
            timePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.d("SettingsFragment", "Time preference clicked");
                    // Show the TimePickerDialog here
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    // Save or use the selected time
                                }
                            }, 8, 0, true);  // 8:00 is set as the default time
                    timePickerDialog.show();

                    return true;
                }
            });
        } else{
            Log.d("SettingsFragment", "Time preference not found");
        }
    }
}