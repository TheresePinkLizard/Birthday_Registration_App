package com.s333329.mappe2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListOverview extends AppCompatActivity {

    private BirthdayDataSource dataSource;

    private ListView listView;
    private HashSet<Integer> selectedPositions = new HashSet<>(); // Multiple selections
    private BirthdayAdapter myAdapter;
    private List<String> birthdayList = new ArrayList<>();

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

        // Adds birthdays to birthdaylist
        dataSource = new BirthdayDataSource(this);
        dataSource.open();
        String tekst = "";
        try{
            List<Birthday> birthdays = dataSource.findAllBirthdays();
            for (Birthday bd : birthdays) {
                tekst = tekst + " " + bd.getName() + " tlf: "+bd.getNumber() +" bursdag: "+ bd.getDate();
                birthdayList.add(tekst);
            }
            Log.i("ListOverview","Success receiving database");
        } catch(Exception e){
            Log.e("ListOverview","Error when receiving database",e);
        }

        // Initialize your adapter with birthdaylist with birthdays
        myAdapter = new BirthdayAdapter(this, R.layout.list_birthday, birthdayList);

        // Get ListView from layout and set its adapter
        listView = findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (selectedPositions.contains(position)) { // If already selected, deselect it
                    selectedPositions.remove(position);
                    view.setBackgroundColor(Color.TRANSPARENT); // Or your default color
                } else { // If not selected, select it
                    selectedPositions.add(position);
                    view.setBackgroundColor(Color.LTGRAY); // Or your selection color
                }
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete selected item
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    dataSource.open();
                    List<String> selectedItems = new ArrayList<>();
                    for (int position : selectedPositions) {
                        selectedItems.add(birthdayList.get(position));
                        dataSource.deleteBirthday(position);
                    }
                    birthdayList.removeAll(selectedItems);
                    selectedPositions.clear();
                    myAdapter.notifyDataSetChanged();
                    Log.i("ListOverview","Successfully removed birthday from database");
                } catch (Exception e){
                    Log.e("MainActivity","Could not parse long int",e);
                }

            }
        });
    }



}
