package com.example.mymealdabba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mymealdabba.adapter.Messdeatlislist;
import com.example.mymealdabba.databinding.ActivityLoginBinding;
import com.example.mymealdabba.databinding.ActivityMessDetailsBinding;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;

public class MessDetailsActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Context context;
    ActivityMessDetailsBinding b;
    Messdeatilslistmodel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMessDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        model = new Gson().fromJson(data, Messdeatilslistmodel.class);
        context = this;
        getData();
        sessionManager = new SessionManager(context);

        ///ViewContact Details Visiblecode///
        b.btnViewContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.relativeLayoutContactDetails.setVisibility(View.VISIBLE);
                b.btnViewContactDetails.setVisibility(View.INVISIBLE);
            }
        });

        b.relativeLayoutContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.relativeLayoutContactDetails.setVisibility(View.INVISIBLE);
                b.btnViewContactDetails.setVisibility(View.VISIBLE);
            }
        });
        //calling code-----------------------------


        b.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = model.ContactNo1;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);

            }
        });

        b.btnTapToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MessDetailsActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.rating, null);

                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        b.btnViewContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.relativeLayoutContactDetails.setVisibility(View.VISIBLE);
            }
        });

    }


    private void getData() {
        Log.e("data", new Gson().toJson(model));
////
//        Glide.with(context)
//                .load(model.Images.)
//                .into(ivNewsImage);
        b.lblMessName.setText(model.MemberName);
      b.lblMessAddress.setText(model.BussinessAddress);
        b.lblType.setText(model.Type);
        b.lblExperience.setText(model.ExpYears);

        b.lblCategory.setText(model.Category);
        b.lblCuisineType.setText(model.CuisineType);
        b.lblService.setText(model.Service);
        b.lblSpecialOrders.setText(model.IsSpecialOrdersAccepted);
        b.lblSpecialOrdersPatient.setText(model.IsSpecialOrdersForPatientAccepted);

        b.lblMonthlyRate.setText(model.MonthlyRate);
        b.lblDailyRate.setText(model.DailyRate);
        b.lblTime.setText(model.CloseTimeMorning + "&"+ model.CloseTimeEvening);
        b.lblNotes.setText(model.Note);
        b.lblContactNo.setText(model.ContactNo1);
        b.lblContactAddress.setText(model.Location);


    }
}