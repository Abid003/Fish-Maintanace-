package com.example.fishmaintanance.fragments;

import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fishmaintanance.AddActivity;
import com.example.fishmaintanance.NotiAlarm;
import com.example.fishmaintanance.R;
import com.example.fishmaintanance.classes.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;


public class Feeding extends Fragment {

    Button increaseBtn, decreaseBtn, feedBtn, notibtn;
    TextView feedAmount,testTv;
    int amount;
    DatabaseReference dref;
    Timer timer;
    int flag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feeding, container, false);


       String userPhoneKey = Prevalent.currentOnlineUser.getPhone();


        dref = FirebaseDatabase.getInstance().getReference();

        increaseBtn = view.findViewById(R.id.increaseBtnId);
        decreaseBtn = view.findViewById(R.id.decreaseBtnId);
        feedBtn =  view.findViewById(R.id.feedBtnId);
        feedAmount =  view. findViewById(R.id.feedAmountId);
        notibtn = view.findViewById(R.id.noti_time_btn);


        timer = new Timer();


        amount = parseInt((String) feedAmount.getText());

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount++;
                if (amount>=10)
                    amount = 10;
                feedAmount.setText(String.valueOf(amount));
            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amount--;
                if (amount<=0)
                    amount = 0;
                feedAmount.setText(String.valueOf(amount));

            }
        });


        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   testTv.setText(String.valueOf(amount));




                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                builder.setMessage("Are you sure?").setCancelable(false).
                        setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                flag = 1;
                                dref.child("Users").child(userPhoneKey).child("fStatus").setValue(flag);
                                Toast.makeText(getContext(), "please wait for "+amount+"sec", Toast.LENGTH_LONG).show();

                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        flag = 0;
                                        dref.child("Users").child(userPhoneKey).child("fStatus").setValue(flag);
                                    }

                                },(amount*1000));


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                       Toast.makeText(getContext(), "Feeding Done! ", Toast.LENGTH_SHORT).show();

                                   }
                                },(amount*1000)+ 1000);


                                dref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int flagValue = parseInt(snapshot.child("Feeding").getValue().toString()) ;
                                        if(flagValue == 0){
                                        ///    Toast.makeText(Feeding.this, "Feeding Done! ", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getContext(), "Oh no! connection lost -_-", Toast.LENGTH_SHORT).show();
                                    }
                                });






                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();



            }
        });


        notibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotiAlarm.class);
                startActivity(intent);
            }
        });


        return view;
    }
}