package com.s333329.mappe2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class RegisterBirthday  extends AppCompatActivity {

    private BirthdayDataSource dataSource;
    private EditText nameText;
    private EditText numberText;
    private EditText birthdayDate;
    private List<Birthday> birthdaylist;
    private TextView showBirthdays;

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


    }
    public void leggtil(View v) {
        String writename = nameText.getText().toString();
        String writenumber = numberText.getText().toString();
        String writebirthday = birthdayDate.getText().toString();
        if (!writename.isEmpty() && !writebirthday.isEmpty() && writenumber.isEmpty()) {
            Birthday data = dataSource.addBirthday(writename, writenumber, writebirthday);
            nameText.setText("");
            numberText.setText("");
            birthdayDate.setText("");
        }
    }

    public void showList(View v) {
        String tekst = "";
        List<Birthday> oppgaver = dataSource.findAllBirthdays();
        for (Birthday opp : oppgaver) {
            tekst = tekst + " " + opp.getName();
        }
        showBirthdays.setText(tekst);
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
