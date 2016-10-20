package com.example.kashishgrover.mttn;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<ListItem> {

    private final Context context;
    private final ArrayList<ListItem> itemsArrayList;

    public MyListAdapter(Context context, ArrayList<ListItem> itemsArrayList) {
        super(context, R.layout.websis_result_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.websis_result_row, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.rowTextView);
        TextView valueView = (TextView) rowView.findViewById(R.id.rowParams);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getSubjectName());
        valueView.setText(itemsArrayList.get(position).getSubjectParameters());

        // 5. return rowView
        return rowView;
    }
}