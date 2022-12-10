package com.example.fishmaintanance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fishmaintanance.classes.Prevalent;
import com.example.fishmaintanance.classes.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Paper.init(this);


        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey= Paper.book().read(Prevalent.userPasswordKey);



        //for full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        // For Hide Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //For Splash Screen


                if (userPhoneKey != null && userPasswordKey != null) {
                        allowAccess(userPhoneKey, userPasswordKey);
                }else{
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(intent);

                }


            }






    private void allowAccess(String phone, String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("Users").child(phone).exists()) {

                    Users userData = snapshot.child("Users").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            Toast.makeText(Splash.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            Prevalent.currentOnlineUser = userData;
                            startActivity(intent);
                        }


                    }
                } else {
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(intent);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Splash.this, "net error", Toast.LENGTH_SHORT).show();


            }
        });


    }
}