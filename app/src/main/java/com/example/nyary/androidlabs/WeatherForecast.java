package com.example.nyary.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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
            return null;
        }
    }
    }

