package com.example.nyary.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class WeatherForecast extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ProgressBar progress = findViewById(R.id.progress);

        ForecastQuery query = new ForecastQuery();
        query.execute();


        progress.setVisibility(View.VISIBLE);
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String current;
        String min;
        String max;
        String wind;
        Bitmap picture;


        @Override
        public String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                XmlPullParser parser = parse(conn.getInputStream());
                getData(parser);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        public XmlPullParser parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                return parser;
            } finally {
                in.close();
            }
        }

        public void getData(XmlPullParser parser) throws XmlPullParserException, IOException{
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getName().equals("temperature")) {
                    current = parser.getAttributeValue(null,"value");
                    min = parser.getAttributeValue(null,"min");
                    max = parser.getAttributeValue(null,"max");
                }
                if(parser.getName().equals("wind")){
                    wind = parser.getAttributeValue(null,"value");
                }
                parser.next();
            }
        }
        public void onPostExecute(String result ){
            TextView currentV= findViewById(R.id.current);
            TextView minV= findViewById(R.id.min);
            TextView maxV= findViewById(R.id.max);
            currentV.setText(current);
            minV.setText(min);
            maxV.setText(max);
        }
    }
}

