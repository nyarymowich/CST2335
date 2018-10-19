package com.example.nyary.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    protected static final String ACTIVITY_NAME = "ChatWindow";
    Button send;
    EditText text;
    ListView chat;
    ArrayList<String> log = new ArrayList<>();
    ChatDatabaseHelper dbHelp = new ChatDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        send = findViewById(R.id.send);
        text = findViewById(R.id.text);
        chat = findViewById(R.id.chat);


        SQLiteDatabase db = dbHelp.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ChatDatabaseHelper.KEY_MESSAGE +" FROM " + ChatDatabaseHelper.TABLE_NAME,null);

        Log.i(ACTIVITY_NAME, "Cursor's column count =" + cursor.getColumnCount() );

        for(int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Cursor's column name =" + cursor.getColumnName(i));
            cursor.moveToNext();
        }

        for(int i = 0; i < cursor.getCount(); i++) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            String s = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            log.add(s);
            cursor.moveToNext();
        }

            ChatAdapter messageAdapter =new ChatAdapter( this );
        chat.setAdapter (messageAdapter);


        send.setOnClickListener(e->{
            log.add(text.getText().toString());
            ContentValues cv = new ContentValues();
            cv.put(ChatDatabaseHelper.KEY_MESSAGE,text.getText().toString());
            db.insert(ChatDatabaseHelper.TABLE_NAME,"NullColumnName",cv);
            messageAdapter.notifyDataSetChanged();
            text.setText("");
        });

    }
    protected void onDestroy(){
        dbHelp.close();
        super.onDestroy();
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

