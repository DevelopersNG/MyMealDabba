package com.example.mymealdabba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    Context context;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = SplashActivity.this;
        sessionManager = new SessionManager(context);

        start();
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.isLoggedIn()) {
                    startActivity(new Intent(context, HomeActivity.class));
                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }

                finish();
            }

        }, 2000);
    }
}