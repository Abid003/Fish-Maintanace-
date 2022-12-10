package com.example.fishmaintanance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fishmaintanance.classes.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class ResetPasswordActivity extends AppCompatActivity {

   private EditText inputName,inputPassword,inputPhone;
   private  Button resetBtn;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Paper.init(this);
        resetBtn = findViewById(R.id.reset_btn);
        inputPhone = findViewById(R.id.find_phone_number);
        inputName = findViewById(R.id.new_wifiName);
        inputPassword = findViewById(R.id.new_password);
        loadingBar = new ProgressDialog(this);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValues();
            }
        });


    }
    private void resetValues() {
        String name = inputName.getText().toString();
        String phone = inputPhone.getText().toString();
        String password = inputPassword.getText().toString();

        if(TextUtils.isEmpty(name)){
            inputName.setError("please enter wifiname");
            inputName.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(phone)){
            inputPhone.setError("please enter phone number");
            inputPhone.requestFocus();
            return;
        }

        else if(TextUtils.isEmpty(password)){
            inputPassword.setError("please enter new password");
            inputPassword.requestFocus();
            return;
        }
        else{
            loadingBar.setTitle("reseting values");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            checkValidity(phone, name, password);
        }

    }

    private void checkValidity(String phone, String name,String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phone).exists()){
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("password",password);
                    userDataMap.put("name",name);

                    rootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ResetPasswordActivity.this,"Value changed successfully ",Toast.LENGTH_LONG).show();
                                        Paper.book().write(Prevalent.userPasswordKey,password);
                                        loadingBar.dismiss();


                                    }
                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(ResetPasswordActivity.this,"Value reset unsuccessful, check your internet and try again",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }
                else {
                    Toast.makeText(ResetPasswordActivity.this,"this phone number is not exist",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


