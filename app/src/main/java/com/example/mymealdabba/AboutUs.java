package com.example.mymealdabba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymealdabba.databinding.ActivityAboutUsBinding;

public class AboutUs extends AppCompatActivity {
ActivityAboutUsBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

            b.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://www.info@mymealdabba.com"));
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            intent.setData(Uri.parse("https://www.info@mymealdabba.com"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }
