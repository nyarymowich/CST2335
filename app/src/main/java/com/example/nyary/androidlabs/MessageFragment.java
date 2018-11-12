package com.example.nyary.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    ChatWindow parent = null;
    long messageID;
    boolean tablet = false;

    public MessageFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment,container,false);
        TextView message = view.findViewById(R.id.message);
        TextView id = view.findViewById(R.id.id);
        String mess = bundle.getString("message");
        Long iD = bundle.getLong("id");
        messageID =iD;
        id.setText(Long.toString(iD));
        message.setText(mess);

        Button delete = view.findViewById(R.id.delete);
        delete.setOnClickListener(e->{
           if(tablet ==true) {
               parent.deleteMessage(messageID);
               FragmentManager fm = getFragmentManager();
               FragmentTransaction ftrans = fm.beginTransaction();
               ftrans.remove(this);
               ftrans.commit(); //actually load it
           }
        });

        return view;
    }
    public void onAttach(Activity context) {
        super.onAttach(context);

            parent = (ChatWindow)  context; //find out which activity has the fragment


    }
}
