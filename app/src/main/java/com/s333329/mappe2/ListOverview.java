package com.s333329.mappe2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ListOverview extends AppCompatActivity {

    private BirthdayDataSource dataSource;
    private TextView showBirthdays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listoverview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dataSource = new BirthdayDataSource(this);
        dataSource.open();
        showBirthdays = findViewById(R.id.birthdayView);
        String tekst = "";
        List<Birthday> birthdays = dataSource.findAllBirthdays();
        for (Birthday bd : birthdays) {
            tekst = tekst + " " + bd.getName();
        }
        showBirthdays.setText(tekst);
    }
}
