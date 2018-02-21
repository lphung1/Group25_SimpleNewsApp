package com.example.loiphung.group25_inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<Source> newsArrayList = new ArrayList<Source>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("News Sites");


        if (isConnected()){
            //https://newsapi.org/v1/sources/
            //https://newsapi.org/v2/top-headlines?country=us&apiKey=b2c985005ade4ecdab27c1abace702a8
            new GetDataAsync(this).execute("https://newsapi.org/v1/sources/");
        }
        else{
            Toast.makeText(this, "Not connected to the internet", Toast.LENGTH_LONG).show();
        }

        Log.d("news Array List", ""+ newsArrayList);
        /*
        ListView listView = findViewById(R.id.ListView);
        CustomSourceAdapter adapter = new CustomSourceAdapter(this, R.layout.news_row, newsArrayList);
        listView.setAdapter(adapter);
        */




    } //end of oncreate

    private class GetDataAsync extends AsyncTask<String, Void, ArrayList<Source> > {

        private ProgressDialog dialog;

        public GetDataAsync(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading sources");
            dialog.show();
        }


        @Override
        protected ArrayList<Source> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Source> result = new ArrayList<Source>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray sources = root.getJSONArray("sources");
                    for (int i = 0; i < sources.length(); i++) {
                        JSONObject sourceJson = sources.getJSONObject(i);
                        Source source = new Source();
                        source.setName(sourceJson.getString("name"));
                        source.setId(sourceJson.getString("id"));

                        Log.d("JsonName", "" + sourceJson.getString("name"));
                        Log.d("Jsonid", "" + sourceJson.getString("id"));

                        //JSONObject addressJson = sourceJson.getJSONObject("address");

                    /*
                    Address address = new Address();
                    address.line1 = addressJson.getString("line1");
                    address.city = addressJson.getString("city");
                    address.state = addressJson.getString("state");
                    address.zip = addressJson.getString("zip");

                    source.address = address;
                    */

                        result.add(source);
                    }
                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {
                //Close the connections
            }
            return result;
        }

        protected void onPostExecute(ArrayList<Source> result) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }



            Log.d("news Array List", ""+ result);

            ListView listView = findViewById(R.id.ListView);
            CustomSourceAdapter adapter = new CustomSourceAdapter(MainActivity.this, R.layout.news_row, result);
            listView.setAdapter(adapter);



        }
    }


    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }




}//end of mainactivity




