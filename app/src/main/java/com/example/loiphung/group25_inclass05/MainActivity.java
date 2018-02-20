package com.example.loiphung.group25_inclass05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> newsArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            setTitle("News Sites");
            ListView lv = findViewById(R.id.LinearLayout_Main);
            CustomAdapter customAdapter = new CustomAdapter();
            lv.setAdapter(customAdapter);

        protected ArrayList<String> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<String> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray persons = root.getJSONArray("persons");
                    for (int i=0;i<persons.length();i++) {
                        JSONObject personJson = persons.getJSONObject(i);
                        Person person = new Person();
                        person.name = personJson.getString("name");
                        person.id = personJson.getLong("id");
                        person.age = personJson.getInt("age");

                        JSONObject addressJson = personJson.getJSONObject("address");

                        Address address = new Address();
                        address.line1 = addressJson.getString("line1");
                        address.city = addressJson.getString("city");
                        address.state = addressJson.getString("state");
                        address.zip = addressJson.getString("zip");

                        person.address = address;
                        result.add(person);
                    }
                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {
                //Close the connections
            }
            return result;
        }








    }

        public class CustomAdapter extends BaseAdapter {
            @Override
            public int getCount() {

                return MainActivity.newsArrayList.size();

            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup parent) {

                View view = getLayoutInflater().inflate(R.layout.news_row, null);
                //ImageView imageView = (ImageView) view.findViewById(R.id.contactImageView);
                //TextView textView = (TextView) view.findViewById(R.id.firstLastPhoneView);

                /*
                imageView.setImageResource(R.drawable.default_image);
                textView.setText(MainActivity.contactArrayList.get(i).toString());
                Log.d("CustomAdapter Count: ", "" + getCount());
                Log.d("Position ", "" + i);
                */
                return view;
            }

        }





}
