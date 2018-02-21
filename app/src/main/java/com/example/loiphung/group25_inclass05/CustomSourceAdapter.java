package com.example.loiphung.group25_inclass05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LoiPhung on 2/19/18.
 */

public class CustomSourceAdapter extends ArrayAdapter<Source> {

    public CustomSourceAdapter(Context context, int resource, ArrayList<Source> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Source source = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_row, parent, false);

        TextView sourceName = convertView.findViewById(R.id.newsText);


        //set the data from the email object
        sourceName.setText(source.getName());

        return convertView;
    }

}
