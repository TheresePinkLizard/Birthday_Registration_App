package com.s333329.mappe2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;

public class BirthdayAdapter extends ArrayAdapter<Birthday> {
    private Context context;
    private List<Birthday> birthdaylist;
    private int resource;
    private HashSet<Integer> selectedPositions = new HashSet<>();

    public BirthdayAdapter(Context context, int resource, List<Birthday> birthdaylist) {
        super(context, resource, birthdaylist);
        this.context = context;
        this.birthdaylist = birthdaylist;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Birthday currentBirthday = birthdaylist.get(position);

        TextView nameTextView = convertView.findViewById(R.id.name_text);
        nameTextView.setText(currentBirthday.getName());

        TextView numberTextView = convertView.findViewById(R.id.number_text);
        numberTextView.setText(currentBirthday.getNumber());

        TextView dateTextView = convertView.findViewById(R.id.date_text);
        dateTextView.setText(currentBirthday.getDate());

        if (selectedPositions.contains(position)) {
            convertView.setBackgroundColor(Color.LTGRAY); // Or your selection color
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT); // Or your default color
        }

        return convertView;
    }
}