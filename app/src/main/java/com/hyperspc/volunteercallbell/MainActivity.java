package com.hyperspc.volunteercallbell;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    Button callButton;
    TextView status;
    EditText room;

    private boolean callSent;
    private String lastID;

    private FirebaseDatabase firebase;
    private DatabaseReference ref;
    private DatabaseReference allRef;

    protected void onStart(){
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.statusText);
        callButton = (Button) findViewById(R.id.call);
        callButton.setBackgroundColor(Color.GRAY);
        ref = FirebaseDatabase.getInstance().getReference("activeCalls");
        allRef = FirebaseDatabase.getInstance().getReference("allCalls");
        room = (EditText) findViewById(R.id.roomNumber);
        callSent = false;
        callButton.setBackgroundColor(Color.parseColor("#F26430"));

        callButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(callSent){
                    callSent = false;
                    callButton.setBackgroundColor(Color.parseColor("#F26430"));
                    callButton.setText("Call");
                    updateFirebase(callSent);
                    Toast.makeText(getApplicationContext(), "Call Terminated", Toast.LENGTH_SHORT).show();
                }else{
                    callSent = true;
                    callButton.setBackgroundColor(Color.GREEN);
                    callButton.setText("Terminate Call");
                    sendToFirebase(callSent);
                    Toast.makeText(getApplicationContext(), "New Call Added", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void sendToFirebase(boolean state){
        int roomNum = Integer.parseInt(String.valueOf(room.getText()));
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        Call newCall = new Call(roomNum, timeStamp, true);

        String id = ref.push().getKey();
        ref.child(id).setValue("");
        ref.child(id).child("roomNumber").setValue(newCall.getRoomNumber());
        ref.child(id).child("timeStamp").setValue(newCall.getTimeStamp());
        ref.child(id).child("status").setValue(newCall.getStatus());

        allRef.child(id).setValue("");
        allRef.child(id).child("roomNumber").setValue(newCall.getRoomNumber());
        allRef.child(id).child("timeStamp").setValue(newCall.getTimeStamp());
        allRef.child(id).child("status").setValue(newCall.getStatus());

        status.setText("<VCB>: Call Added: Room " + newCall.getRoomNumber() + " | Time: "
                + newCall.getTimeStamp() + " | Status: " + newCall.getStatus()
                + "\n" + status.getText());

        lastID = id;
    }


    private void updateFirebase(boolean state){
        DatabaseReference tRef = FirebaseDatabase.getInstance().getReference("activeCalls").child(lastID);
        DatabaseReference tAllRef = FirebaseDatabase.getInstance().getReference("allCalls").child(lastID);
        tAllRef.child("status").setValue(state);
        tRef.getRef().removeValue();
        status.setText("<VCB>: Call ID: " + tRef.getKey() + " has been terminated.\n" + status.getText());
    }

}
