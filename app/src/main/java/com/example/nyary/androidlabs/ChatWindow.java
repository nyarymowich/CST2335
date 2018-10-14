package com.example.nyary.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
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

        ChatAdapter messageAdapter =new ChatAdapter( this );
        chat.setAdapter (messageAdapter);


        send.setOnClickListener(e->{
            log.add(text.getText().toString());
            messageAdapter.notifyDataSetChanged();
            text.setText("");
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
       @Override
        public View getView(int pos, View convertView, ViewGroup parent){
           LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
           View result = null ;
           if(pos%2 == 0)
               result = inflater.inflate(R.layout.chat_row_incoming, null);
           else
               result = inflater.inflate(R.layout.chat_row_outgoing, null);

           TextView message = (TextView)result.findViewById(R.id.message_text);
           message.setText(   getItem(pos)  ); // get the string at position
           return result;



       }
    }
}
