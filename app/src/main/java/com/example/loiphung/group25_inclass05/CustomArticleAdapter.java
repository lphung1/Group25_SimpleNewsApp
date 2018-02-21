package com.example.loiphung.group25_inclass05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LoiPhung on 2/20/18.
 */


public class CustomArticleAdapter extends ArrayAdapter<Source> {

    public CustomArticleAdapter(Context context, int resource, ArrayList<Source> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Source source = this.getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_row, parent, false);

        TextView sourceName = convertView.findViewById(R.id.articleTitleDescription_textview);
        View imageView = convertView.findViewById(R.id.article_image_view);

        //set the data from the email object
        sourceName.setText(source.getArticle().toString());

        return convertView;
    }

}