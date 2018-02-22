package com.example.loiphung.group25_inclass05;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        ImageView imageView = convertView.findViewById(R.id.article_image_view);


        sourceName.setText(source.getArticle().toString());
        new ImageDownloaderTask(imageView).execute(source.getArticle().getUrlToImage());
        Log.d("Picture URL", source.getArticle().getUrlToImage());



        return convertView;
    }


    public static Bitmap getBitmapFromURL(String src) {

        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String result = null;
        Bitmap image = null;
        try{
            URL url = new URL(src);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                image = BitmapFactory.decodeStream(connection.getInputStream());
            }
        }
        catch(MalformedURLException e){
            e.printStackTrace();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection == null){
                connection.disconnect();
            }
            if (reader != null){
                try{
                    reader.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }

        }

        return image;
    }



    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;

        public ImageDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {

                        Drawable placeholder = null;
                        imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();

                final int responseCode = urlConnection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("ImageDownloader", "Error Downloading Image " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }



} //end class









