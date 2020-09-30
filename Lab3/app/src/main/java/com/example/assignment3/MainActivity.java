package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editEmail, editPassword;
    Button loginButton;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefers";
    public static final String email = "emailKey";
    public static final String password = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText)findViewById(R.id.email);
        editPassword = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);

        //load saved preferences at creation time
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String value = sharedpreferences.getString("emailKey", "");
        editEmail.setText(value);
        Log.d("Load Pref", "Preference loaded");

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onPause();
            }
        });
    }

    @Override
    protected void onPause() {
        String e = editEmail.getText().toString();
        String p = editEmail.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(email, e);
        editor.putString(password, p);
        editor.commit();
        Toast.makeText(MainActivity.this, "Email saved", Toast.LENGTH_LONG).show();

        super.onPause();
        Log.d("Pref saved", e);
    }
}
