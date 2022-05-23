package com.example.mymealdabba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mymealdabba.databinding.ActivityLoginBinding;
import com.example.mymealdabba.databinding.ActivityTearmAndConditionBinding;

public class TearmAndConditionActivity extends AppCompatActivity {
ActivityTearmAndConditionBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityTearmAndConditionBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
       b. mtbMessList.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });
    }
}