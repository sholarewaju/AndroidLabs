package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ProgressBar progressbar;
    ImageView WeatherImage;
    TextView textviewTemp,textviewMinTemp,textviewMaxTemp,textviewUV;
    private XmlPullParserFactory xmlFactoryObject;

    private  static String weatherInfoURL="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
    private  static String uvRatingURL="http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressbar=findViewById(R.id.progressbar);
        WeatherImage=findViewById(R.id.image);
        textviewTemp=findViewById(R.id.textviewTemp);
        textviewMinTemp=findViewById(R.id.textviewMinTemp);
        textviewMaxTemp=findViewById(R.id.textviewMaxTemp);
        textviewUV=findViewById(R.id.textviewUV);

        progressbar.setVisibility(View.VISIBLE);
        new ForecastQuery().execute(weatherInfoURL,uvRatingURL);
    }


    // Implementation of AsyncTask used to fetch weather info
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String UV, minTemp, maxTemp, Temp;
        String iconName;
        Bitmap image;

        @Override
        protected String doInBackground(String... args) {

            //UV RATING
            try {

                //create a URL object of what server to contact:
                URL url = new URL(args[1]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();


                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string


                // convert string to JSON:
                JSONObject uvReport = new JSONObject(result);

                //get the double associated with "value"
                double uvRating = uvReport.getDouble("value");
                UV=uvRating+"";
                Log.i("MainActivity", "The uv is now: " + uvRating) ;

            }
            catch (Exception e)
            {

            }

            publishProgress(25);
            //TEMP
            try {
                URL url = new URL(args[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                InputStream stream = connection.getInputStream();

                xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myParser = xmlFactoryObject.newPullParser();
                xmlFactoryObject.setNamespaceAware(false);
                myParser.setInput(stream,  "UTF-8");
                parseXML(myParser);
                stream.close();

                //return "result";

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("AsyncTask", "exception");

            }

            publishProgress(50);

            Log.i("info","Image Filename: "+iconName+".png");
            if(fileExistance(iconName+".png")){
                Log.i("info","Image found in local storage ");
                //dont download
                FileInputStream fis = null;
                try {
                    fis = openFileInput(iconName + ".png");
                    image = BitmapFactory.decodeStream(fis);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();

                }
            }else {
                Log.i("info","Image to be downloaded online ");
                //download
                try {
                    URL url = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                        FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                        image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(75);

            publishProgress(100);
            return "Done";
        }

        @Override
        protected void onPostExecute(String result) {
            // Displays the HTML string in the UI via a WebView
            setDisplay(Temp,minTemp,maxTemp,UV,image);

            progressbar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressbar.setProgress(values[0]);
        }

        public void parseXML(XmlPullParser myParser) {

            int event;
            String text = null;
            String[] result = new String[5];

            try {
                event = myParser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {
                    String name = myParser.getName();

                    switch (event) {
                        case XmlPullParser.START_TAG:
                            if (name.equals("weather")) { //get weather icon
                                iconName = myParser.getAttributeValue(null, "icon");
                            } else if (name.equals("temperature")) { //get temperature
                                String unit= " "+myParser.getAttributeValue(null, "unit");
                                Temp = myParser.getAttributeValue(null, "value")+unit;
                                minTemp = myParser.getAttributeValue(null, "min")+unit;
                                maxTemp = myParser.getAttributeValue(null, "max")+unit;

                            }
                            break;
                        case XmlPullParser.TEXT:
                            text = myParser.getText();
                            break;

                        case XmlPullParser.END_TAG:

                            break;
                    }
                    event = myParser.next();
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
        }


        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists(); }

    }

    private void setDisplay(String temp, String minTemp, String maxTemp, String uv, Bitmap image) {
        textviewTemp.setText(temp);
        textviewMinTemp.setText(minTemp);
        textviewMaxTemp.setText(maxTemp);
        textviewUV.setText(uv);
        WeatherImage.setImageBitmap(image);

    }
}