package com.s333329.mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;

import java.util.List;

public class RegisterBirthday  extends AppCompatActivity {

    private BirthdayDataSource dataSource;
    private EditText nameText;
    private EditText numberText;
    private EditText birthdayDate;
    private List<Birthday> birthdaylist;
    private boolean changemode;
    private long birthdayPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registerbirthday);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                    systemBars.bottom);
            return insets;
        });
        dataSource = new BirthdayDataSource(this);
        dataSource.open();
        nameText = findViewById(R.id.writename);
        numberText = findViewById(R.id.writenumber);
        birthdayDate = findViewById(R.id.writebirthday);
        birthdaylist = dataSource.findAllBirthdays();

        // change contents of the birthday object. gets id from long clicking birthday in list
        Intent intent = getIntent();
        long selectedBirthdayId = intent.getLongExtra("birthday_id", -1);
        if(selectedBirthdayId != -1){
            changemode = true; // changemode set to true to update birthday instead of adding
            birthdayPosition = selectedBirthdayId;
            for (Birthday b : birthdaylist){
                if (b.getId() == birthdayPosition){
                    nameText.setText(b.getName());
                    numberText.setText(b.getNumber());
                    birthdayDate.setText(b.getDate());
                }
            }
        }else{
            Log.e("RegisterBirthday","Failed to get id when changing birthday");
        }
    }


    public void leggtil(View v) {
        String writename = nameText.getText().toString();
        String writenumber = numberText.getText().toString();
        String writebirthday = birthdayDate.getText().toString();
        if (!writename.isEmpty() && !writebirthday.isEmpty() && !writenumber.isEmpty()) {
            try {
                // changemode to update birthday instead of adding
                if (changemode) {
                    dataSource.updateBirthday(birthdayPosition, writename, writenumber, writebirthday);
                    Log.i("RegisterBirthday","Updated selected birthday");
                } else {
                    dataSource.addBirthday(writename, writenumber, writebirthday);
                }
                nameText.setText("");
                numberText.setText("");
                birthdayDate.setText("");

            } catch (Exception e) {
                if (changemode){
                    Log.e("RegisterBirthday", "Could not update birthday", e);
                }else{
                    Log.e("RegisterBirthday", "Could not add new birthday", e);
                }
            }
        }
    }

    // button to go to birthday listview
    public void goToList(View v){
        Intent i = new Intent(this, ListOverview.class);
        startActivity(i);
    }
    public void goToFrontPage(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }
    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
