package com.example.mymealdabba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.mymealdabba.databinding.ActivityHomeBinding;
import com.example.mymealdabba.databinding.ActivityLoginBinding;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding b;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = this;
        listener();

    }

    private void listener() {
        b.etCityList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text1 = b.etCityList.getText().toString().trim();
                b.etCityList.setVisibility(View.VISIBLE);

            }
        });
    }
}