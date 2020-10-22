package com.example.assignment3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class ProfileActivity extends AppCompatActivity {

    ImageButton
            captureButton;
    Button goToChatButton,gotoweatherforecastBtn;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        captureButton = (ImageButton)findViewById(R.id.capture);
        goToChatButton = (Button) findViewById(R.id.gotochat);
        gotoweatherforecastBtn= findViewById(R.id.gotoweatherforecast);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        Log.e(ACTIVITY_NAME, "In function: " + "onCreate()");

        goToChatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //what happens
                openNewActivity();
            }
        });

        gotoweatherforecastBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //what happens
                openWeatherActivity();
            }
        });
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, ChatRoomActivity.class);
        startActivity(intent);
    }

    public void openWeatherActivity(){
        Intent intent = new Intent(this, WeatherForecast.class);
        startActivity(intent);
    }

    private void dispatchTakePictureIntent(){
        final int REQUEST_IMAGE_CAPTURE = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final int REQUEST_IMAGE_CAPTURE = 1;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            captureButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In function: " + "onActivityResult()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: " + "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function: " + "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: " + "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: " + "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function: " + "onResume()");
    }

}
