package com.example.yuanli.androidlab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "WeatherForecast";

    TextView first, second, third;
    ProgressBar progressBar;
    ImageView imageView;
    String min, max, currentTemp, iconName;
    Bitmap bitmap;
    HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        first = (TextView) findViewById(R.id.currentTemp);
        second = (TextView) findViewById(R.id.minTemp);
        third = (TextView) findViewById(R.id.maxTemp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        imageView = (ImageView) findViewById(R.id.weatherView);
        new WeatForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
    }

    public class WeatForecastQuery extends AsyncTask<String, Integer, String> {


        public String doInBackground(String... args) {
            XmlPullParser parser = Xml.newPullParser();

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();

                boolean finished = false;
                int type = XmlPullParser.START_DOCUMENT;

                while (type != XmlPullParser.END_DOCUMENT) {

                    switch (type) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            finished = true;
                            break;
                        case XmlPullParser.START_TAG:
                            String name = parser.getName();
                            if (name.equals("temperature")) {
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(25);

                                max = parser.getAttributeValue(null, "max");
                                publishProgress(50);

                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(75);


                            } else if (name.equals("weather")) {
                                iconName = parser.getAttributeValue(null, "icon");
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                    type = parser.next(); //advances to next xml event
                }

            } catch (IOException | XmlPullParserException ex) {
                Log.e("XML PARSING", ex.getMessage());
            }

            return null;
        }


        protected void onPreExecute() {
            // do something before start
        }

        private int state;

        public void onProgressUpdate(Integer... args) {
           progressBar.setVisibility(View.VISIBLE);
            super.onProgressUpdate(args);
            if (progressBar != null) {
                progressBar.setProgress(args[0]);
            }

            if (iconName != null) {
                String imageURL = "http://openweathermap.org/img/w/" + iconName + ".png";
                String fileName = iconName + ".png";
                boolean exist = fileExistance(fileName);
                if (exist) {
                    Log.i(ACTIVITY_NAME, fileName + " exists and no need to download again!");
                    FileInputStream fis = null;
                    File file = getBaseContext().getFileStreamPath(fileName);
                    try {
                        fis = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        bitmap = null;
                    }
                    bitmap = BitmapFactory.decodeStream(fis);
                } else {
                    Log.i(ACTIVITY_NAME, fileName + " does not exist and need to download!");
                    new DownloadBitmap().execute(imageURL);
                }


                if (bitmap == null) {
                    new DownloadBitmap().execute(imageURL);
                }
            }


        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        protected void onPostExecute(String result) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            first.setText("current temperature " + currentTemp + "°C");
            second.setText("min temperature " + min + "°C");
            third.setText("max temperature " + max + "°C");
            imageView.setImageBitmap(bitmap);

            progressBar.setVisibility(View.INVISIBLE);

        }
    }


    private class DownloadBitmap extends AsyncTask<String, Integer, String> {


        public String doInBackground(String... args) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(args[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {


                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                    try {
                        FileOutputStream fos = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outstream);
                        byte[] byteArray = outstream.toByteArray();
                        fos.write(byteArray);
                        fos.close();
                        publishProgress(100);
                    } catch (Exception e) {
                        Log.i(ACTIVITY_NAME, "DownloadBitmap doInBackground with Exception " + e.getMessage());
                    }
                } else {
                    return null;
                }
                return null;
            } catch (Exception e) {
                Log.i(ACTIVITY_NAME, "DownloadBitmap doInBackground with Exception " + e.getMessage());
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public void onProgressUpdate(Integer... updateInfo) {
            progressBar.setProgress(updateInfo[0]);
        }


        public void onPostExecute(String result) {
            imageView.setImageBitmap(bitmap);
        }
    }
}

