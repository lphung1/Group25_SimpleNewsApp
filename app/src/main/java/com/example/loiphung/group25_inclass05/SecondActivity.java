package com.example.loiphung.group25_inclass05;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Articles");

        ListView listView = findViewById(R.id.second_listview);
        CustomArticleAdapter adapter = new CustomArticleAdapter(SecondActivity.this, R.layout.news_row, MainActivity.newsArrayList);
        listView.setAdapter(adapter);

        //Log.d("Description", ""+ description);
        //Log.d("url", ""+ url);

        TextView textView = findViewById(R.id.articleTitleDescription_textview);
        //textView.setText(description);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String url = MainActivity.newsArrayList.get(position).getArticle().getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });





    }




}




