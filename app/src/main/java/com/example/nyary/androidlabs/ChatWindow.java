package com.example.nyary.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    Cursor cursor;
    Boolean tablet = false;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        if (findViewById(R.id.frame)!=null){
            tablet = true;
        }
        send = findViewById(R.id.send);
        text = findViewById(R.id.text);
        chat = findViewById(R.id.chat);


        db = dbHelp.getWritableDatabase();
        cursor = db.rawQuery("SELECT " + ChatDatabaseHelper.KEY_ID +"," + ChatDatabaseHelper.KEY_MESSAGE +" FROM " + ChatDatabaseHelper.TABLE_NAME,null);

        Log.i(ACTIVITY_NAME, "Cursor's column count =" + cursor.getColumnCount() );

        for(int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Cursor's column name =" + cursor.getColumnName(i));
            cursor.moveToNext();
        }
        cursor.moveToFirst();
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
        chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView test = view.findViewById(R.id.message_text);
                String test2 = (String) test.getText();
                Log.i(ACTIVITY_NAME, test2);
                Bundle bundle = new Bundle();
                bundle.putString("message",test2);
                if (tablet ==true){
                    MessageFragment newFragment = new MessageFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ftrans = fm.beginTransaction();
                    ftrans.replace(R.id.frame,newFragment); //load a fragment into the framelayout
                    ftrans.addToBackStack("name doesn't matter"); //changes the back button behaviour
                    ftrans.commit(); //actually load it
                }else{
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    startActivityForResult(intent,50);
                }
            }
        });
    }
    protected void onDestroy(){
        dbHelp.close();
        super.onDestroy();
    }
    public void deleteMessage(long id){
        db.delete("Log","Id=?",new String[]{Long.toString(id)});
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
           Log.i(ACTIVITY_NAME, String.valueOf(pos));
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

