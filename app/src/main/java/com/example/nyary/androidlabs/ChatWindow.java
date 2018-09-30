package com.example.nyary.androidlabs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    Button send;
    EditText text;
    ListView chat;
    ArrayList<String> log = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        send = findViewById(R.id.send);
        text = findViewById(R.id.text);
        chat = findViewById(R.id.chat);

        send.setOnClickListener(e->{
            log.add(text.getText().toString());
        });

    }
    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        @Override
        public int getCount(){
            return  log.size();
        }
        @Override
        public String getItem(int pos){
            return  log.get(pos);
        }
       @Override
        public long getItemId(int pos){
            return pos;
       }
    }
}
