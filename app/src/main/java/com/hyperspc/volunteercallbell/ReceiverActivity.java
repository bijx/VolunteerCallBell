package com.hyperspc.volunteercallbell;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ReceiverActivity extends ListActivity {

    DatabaseReference db;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;

    protected void onStart(){
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getListView().clearChoices();
                data = new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    try {
                        /*if(postSnapshot.child("status").getValue().equals("true")) {
                            data.add(postSnapshot.child("roomNumber").getValue().toString());
                        }*/

                        data.add("Room: " + postSnapshot.child("roomNumber").getValue().toString() + "  |  " + postSnapshot.child("timeStamp").getValue().toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                adapter = new ArrayAdapter<String>(getListView().getContext(),android.R.layout.simple_list_item_1,data);
                getListView().setAdapter(adapter);

                removeDone();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        db = FirebaseDatabase.getInstance().getReference("activeCalls");

    }

    private void removeDone(){
        Log.e("", "Number of elements: " +getListView().getCount());
        for(int i = 0; i<getListView().getCount(); i++){
            if(getListView().getItemAtPosition(i).toString().contains("false")){
                data.remove(i);
            }
            adapter = new ArrayAdapter<String>(getListView().getContext(),android.R.layout.simple_list_item_1,data);
            getListView().setAdapter(adapter);
        }

    }


}
