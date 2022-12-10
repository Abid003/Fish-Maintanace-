package com.example.fishmaintanance.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fishmaintanance.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PHFragment extends Fragment {

    float phValue;


    TextView phTextview;
    TextView conditionTextview;
    TextView recommendationTextview;

    DatabaseReference dref;
    String sensorValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_p_h, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ///find components
        phTextview = view.findViewById(R.id.phTextviwId);
        conditionTextview = view.findViewById(R.id.conditionTextvieId);
        recommendationTextview = view.findViewById(R.id.recommedationTextviewId);



        setValuesToTextView(sensorValue);


          dref = FirebaseDatabase.getInstance().getReference();

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if( snapshot.child("PH").getValue().toString() != null) {
//                    sensorValue = snapshot.child("PH").getValue().toString();
//                    setValuesToTextView(sensorValue);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    private void setValuesToTextView(String sensorValue) {

        phValue = 6;

        String conditionText="hm";
        String recommendationText = "hm";

        if(phValue >= 7.0 && phValue <= 9){
            conditionText = "good";
            recommendationText = "no changes needed";
        }
        else if(phValue < 7.0 &&  phValue >= 4.5){
            conditionText = "Not good, The water is little acidic";
            recommendationText = "Change water or add some Basic salt";
        }
        else if(phValue<4.5){
            conditionText = "Very bad, The water is very acidic";
            recommendationText = "Change water";
        }
        else if(phValue>9 && phValue<=12.0){
            conditionText = "Not good, The water is little bacic";
            recommendationText = "Change water or make water little acidic";
        }

        else if(phValue>12.0){
            conditionText = "Very bad, The water is very basic";
            recommendationText = "Change water";
        }
        conditionTextview.setText(conditionText);
        recommendationTextview.setText(recommendationText);
        phTextview.setText(Float. toString(phValue));
    }
}
