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

public class BirthdayAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;
    private int resource;
    private HashSet<Integer> selectedPositions = new HashSet<>();

    public BirthdayAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.list_item_text);
        textView.setText(items.get(position));

        if (selectedPositions.contains(position)) {
            convertView.setBackgroundColor(Color.LTGRAY); // Or your selection color
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT); // Or your default color
        }

        return convertView;
    }
}