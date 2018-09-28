package com.example.nyary.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
       SharedPreferences prefs = getSharedPreferences("emailPrefs", Context.MODE_PRIVATE);
        String email = prefs.getString("email","email@domain.com" );
        EditText login = findViewById(R.id.login_text);
        login.setText(email);

        Button loginB =(Button)findViewById(R.id.button2);
        loginB.setOnClickListener(click ->{
            String input = login.getText().toString();
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("email", input);
            edit.commit();//write to disk
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);



        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    @Override
    protected void onStart(){
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
    }
    @Override
    protected void onPause(){
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
    }
}
