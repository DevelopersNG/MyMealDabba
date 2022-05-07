package com.example.mymealdabba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MessDetailsActivity extends AppCompatActivity {
    RelativeLayout relativeLayoutContactDetails;
    TextView btnTapToRate, btnViewContactDetails, btnlogout;
    ImageView call;
    SessionManager sessionManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_details);
        context = this;
        relativeLayoutContactDetails = findViewById(R.id.relativeLayoutContactDetails);
        btnViewContactDetails = findViewById(R.id.btnViewContactDetails);
        call = findViewById(R.id.call);
        btnTapToRate = findViewById(R.id.btnTapToRate);
        btnlogout = findViewById(R.id.btnlogout);

        sessionManager = new SessionManager(context);

        ///ViewContact Details Visiblecode///
        btnViewContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutContactDetails.setVisibility(View.VISIBLE);
                btnViewContactDetails.setVisibility(View.INVISIBLE);
            }
        });

        relativeLayoutContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutContactDetails.setVisibility(View.INVISIBLE);
                btnViewContactDetails.setVisibility(View.VISIBLE);
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });

        //calling code-----------------------------


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "0123456789";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);

            }
        });

        btnTapToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MessDetailsActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.rating, null);

                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }
}