package com.example.mymealdabba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessDetailsActivity extends AppCompatActivity {
    RelativeLayout relativeLayoutContactDetails;
SessionManager sessionManager;
    TextView btnTapToRate,btnViewContactDetails;
    ImageView call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_details);

        relativeLayoutContactDetails=findViewById(R.id.relativeLayoutContactDetails);
        btnViewContactDetails=findViewById(R.id.btnViewContactDetails);
        call=findViewById(R.id.call);
        btnTapToRate=findViewById(R.id.btnTapToRate);

            getdata();

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


        //calling code-----------------------------


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "0123456789";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);

            }
        });

        /////Open the RatingDailogPage//////////

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

    private void getdata() {
        //your text do u want pass
//        tvUserName.setText(sessionManager.get(SessionManager.KEY_NAME));
//        Log.e("name", sessionManager.get(SessionManager.KEY_NAME));
        Log.e("details", sessionManager.getDetailsFromSession().toString());
    }


}