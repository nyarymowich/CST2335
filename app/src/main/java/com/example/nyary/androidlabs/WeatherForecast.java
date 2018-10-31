package com.example.nyary.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;



import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ProgressBar progress = findViewById(R.id.progress);

        progress.setVisibility(View.VISIBLE);
    }
    public class ForecastQuery extends AsyncTask<String,Integer,String> {
        String current;
        String min;
        String max;
        String wind;
        Bitmap picture;

        @Override
        protected String doInBackground(String... strings) {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        }

    }
    }

