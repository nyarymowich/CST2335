package com.example.nyary.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";
    ForecastQuery query;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progress = findViewById(R.id.progress);

        query = new ForecastQuery();
        query.execute();


        progress.setVisibility(View.VISIBLE);
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String current;
        String min;
        String max;
        String wind;
        String icon;
        Bitmap picture;


        @Override
        public String doInBackground(String... strings) {
            try {
                //connect to Server:
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                //Read the XML:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");


                while(xpp.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    switch(xpp.getEventType())
                    {

                        case XmlPullParser.START_TAG:
                            String name = xpp.getName();

                            if(name.equals("temperature"))
                            {
                                current = xpp.getAttributeValue(null, "value");
                                publishProgress(20);
                                min = xpp.getAttributeValue(null, "min");
                                publishProgress(40);
                                max = xpp.getAttributeValue(null, "max");
                                publishProgress(60);
                                Log.i(ACTIVITY_NAME,"value "+ current +" min " + min + " max " + max );
                            }else if(name.equals("speed")){
                                wind = xpp.getAttributeValue(null, "value");
                                publishProgress(80);
                            }else if(name.equals("weather")){
                                icon = xpp.getAttributeValue(null, "icon");
                            }
                            Log.i("read XML tag:" , name);
                            break;

                        case XmlPullParser.TEXT:
                            break;
                    }

                    xpp.next();//look at next XML tag
                }
            }
            catch(Exception e)
            {
                Log.i("Exception", e.getMessage());
            }
            if(fileExistance(query.icon + ".png") == false) {
                try {
                    picture = HttpUtils.getImage("http://openweathermap.org/img/w/" + query.icon + ".png");
                    publishProgress(100);
                    FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(ACTIVITY_NAME,"Downloading image " + query.icon +".png");
                } catch (Exception e) {
                    Log.i("Exception", e.getMessage());
                }
            }else{
                FileInputStream fis = null;
                try {    fis = openFileInput(query.icon + ".png");   }
                catch (FileNotFoundException e) {    e.printStackTrace();  }
                picture = BitmapFactory.decodeStream(fis);
                Log.i(ACTIVITY_NAME,"Getting image from file "+ query.icon +".png");

            }
            return "done";
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }
        @Override
        public void onProgressUpdate(Integer ... args)
        {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(args[0]);

        }
        @Override
        public void onPostExecute(String s){
            TextView currentV = findViewById(R.id.current);
            TextView minV = findViewById(R.id.min);
            TextView maxV = findViewById(R.id.max);
            TextView windV = findViewById(R.id.wind);
            ImageView imageV =findViewById(R.id.image);
            currentV.setText("Current: " + current + "°C");
            minV.setText("Min: " + min +"°C");
            maxV.setText("Max: " + max + "°C");
            windV.setText("Wind: " + wind + " km/h");
            imageV.setImageBitmap(picture);
            progress.setVisibility(View.INVISIBLE);

        }

    }
    static class HttpUtils {
        public static Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public static Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }
}

