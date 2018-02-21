package com.example.loiphung.group25_inclass05;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Description");

        Intent i = getIntent();
        String description = i.getExtras().getString("description");
        final String url = i.getExtras().getString("url");

        Log.d("Description", ""+ description);
        Log.d("url", ""+ url);

        TextView textView = findViewById(R.id.descriptionText);
        textView.setText(description);

        findViewById(R.id.gotoUrlButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });



    }
}
