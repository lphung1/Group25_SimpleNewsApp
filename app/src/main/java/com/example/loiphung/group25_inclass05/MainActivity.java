package com.example.loiphung.group25_inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
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
            new GetDataAsync(this).execute("https://newsapi.org/v2/top-headlines?country=us&apiKey=b2c985005ade4ecdab27c1abace702a8");
        }
        else{
            Toast.makeText(this, "Not connected to the internet", Toast.LENGTH_LONG).show();
        }


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

                    JSONArray articles = root.optJSONArray("articles");

                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject articlesJSONObject = articles.optJSONObject(i);
                        JSONObject sourceJsonObject = articlesJSONObject.optJSONObject("source");

                        Source s = new Source();
                        Article a = new Article();

                        s.setName(sourceJsonObject.optString("name"));
                        s.setId(sourceJsonObject.optString("id"));
                        a.setDescription(articlesJSONObject.getString("description"));
                        a.setUrl(articlesJSONObject.optString("url"));
                        a.setTitle(articlesJSONObject.optString("title"));
                        a.setAuthor(articlesJSONObject.optString("author"));
                        if(articlesJSONObject.optString("urlToImage").startsWith("https"))
                        {
                            a.setUrlToImage(articlesJSONObject.optString("urlToImage"));
                        }
                        a.setDate(articlesJSONObject.optString("publishedAt"));
                        Log.d("JsonName", "" + sourceJsonObject.getString("name"));
                        Log.d("Jsonid", "" + sourceJsonObject.getString("id"));

                        s.setArticle(a);
                        result.add(s);
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


            final ArrayList<Source> thisSource = result;
            MainActivity.newsArrayList = result;

            Log.d("news Array List", ""+ result);

            ListView listView = findViewById(R.id.ListView);
            CustomSourceAdapter adapter = new CustomSourceAdapter(MainActivity.this, R.layout.news_row, result);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);


                    Source s = thisSource.get(position);
                    Log.d("s item", "" + s);
                    intent.putExtra("picture", s.getArticle().getUrlToImage());

                    startActivity(intent);
                }
            });



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




