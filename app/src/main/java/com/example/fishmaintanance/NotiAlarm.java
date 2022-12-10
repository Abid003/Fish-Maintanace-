package com.example.fishmaintanance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.fishmaintanance.adapter.CustomAdapter;
import com.example.fishmaintanance.classes.Alarm;
import com.example.fishmaintanance.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NotiAlarm extends AppCompatActivity {


    public static String activeAlarm = "";
    private ListView listView;




    private static final int REQUEST_CODE = 1000;

    public static List<Alarm> alarmList = new ArrayList<>();
    private CustomAdapter customAdapter;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_alarm);


        Button button = findViewById(R.id.addBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotiAlarm.this,AddActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });



        listView = findViewById(R.id.listview);
        List<Alarm> list = db.getAllAlarms();
        alarmList.addAll(list);
        customAdapter = new CustomAdapter(getApplicationContext(), alarmList);
        listView.setAdapter(customAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            boolean needRefresh = data.getExtras().getBoolean("needRefresh");
            if(needRefresh){
                alarmList.clear();
                List<Alarm> list = db.getAllAlarms();
                alarmList.addAll(list);
                customAdapter.notifyDataSetChanged();
            }
        }
    }
}