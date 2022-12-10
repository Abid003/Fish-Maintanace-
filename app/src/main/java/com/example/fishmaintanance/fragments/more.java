package com.example.fishmaintanance.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fishmaintanance.LoginActivity;
import com.example.fishmaintanance.MainActivity;
import com.example.fishmaintanance.ResetPasswordActivity;
import com.example.fishmaintanance.classes.Alarm;
import com.example.fishmaintanance.adapter.CustomAdapter;
import com.example.fishmaintanance.classes.Prevalent;
import com.example.fishmaintanance.classes.Users;
import com.example.fishmaintanance.database.DatabaseHelper;
import com.example.fishmaintanance.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;


public class more extends Fragment {

    private Button logoutBtn;
    private TextView tv;

    private  TextView respass;

    private static final int REQUEST_CODE = 1000;

    public static List<Alarm> alarmList = new ArrayList<>();
    private CustomAdapter customAdapter;
    private DatabaseHelper db = new DatabaseHelper(getContext());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Paper.init(getContext());
        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);

        logoutBtn = view.findViewById(R.id.logoutbtn);
        respass = view.findViewById(R.id.reset_password_tv);
        respass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(getActivity(),ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent intent = new  Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });


        tv = view.findViewById(R.id.testtv);

        Users u = new Users();


        tv.setText( Prevalent.currentOnlineUser.getPhone());


    }

}