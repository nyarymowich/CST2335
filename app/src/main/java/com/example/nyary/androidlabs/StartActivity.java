package com.example.nyary.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_start);
        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(click ->{
            Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
            startActivityForResult(intent,50);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 50 ){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if (resultCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            int duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
            Toast toast = Toast.makeText(getApplicationContext(), messagePassed, duration); //this is the ListActivity
            toast.show(); //display your message box
        }
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
