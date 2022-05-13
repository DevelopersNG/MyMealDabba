package com.example.mymealdabba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mymealdabba.databinding.ActivityBookMarkBinding;
import com.example.mymealdabba.databinding.ActivityBookmarkDetailBinding;
import com.example.mymealdabba.databinding.ActivityMessDetailsBinding;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;

public class BookmarkDetailActivity extends AppCompatActivity {


    SessionManager sessionManager;
    Context context;
    ActivityBookmarkDetailBinding b;
    Messdeatilslistmodel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityBookmarkDetailBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        model = new Gson().fromJson(data, Messdeatilslistmodel.class);
        context = this;
        getData();
        sessionManager = new SessionManager(context);

        ///ViewContact Details Visiblecode///
        b.btnFavViewContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.relativeLayoutFavContactDetails.setVisibility(View.VISIBLE);
                b.btnFavViewContactDetails.setVisibility(View.INVISIBLE);
            }
        });

        b.relativeLayoutFavContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.relativeLayoutFavContactDetails.setVisibility(View.INVISIBLE);
                b.btnFavViewContactDetails.setVisibility(View.VISIBLE);
            }
        });
        //calling code-----------------------------


        b.Favcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = model.ContactNo1;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);

            }
        });

        b.btnFavTapToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookmarkDetailActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.rating, null);

                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        b.btnFavViewContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.relativeLayoutFavContactDetails.setVisibility(View.VISIBLE);
            }
        });


        b.imageFavViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }




    private void getData() {
        Log.e("data", new Gson().toJson(model));
////
//        Glide.with(context)
//                .load(model.Images.)
//                .into(ivNewsImage);

        ((BookmarkDetailActivity) context).b.mtbNavigationBookmark.setTitle(model.MemberName);

        b.lblFavMessName.setText(model.MemberName);
        b.lblFavMessAddress.setText(model.BussinessAddress);
        b.lblFavType.setText(model.Type);
        b.lblFavExperience.setText(model.ExpYears);

        b.lblFavCategory.setText(model.Category);
        b.lblFavCuisineType.setText(model.CuisineType);
        b.lblFavService.setText(model.Service);

        if(model.IsSpecialOrdersAccepted.equals("1"))
        {
            b.lblFavSpecialOrders.setText("YES");
        }
        else {
            b.lblFavSpecialOrders.setText("Not Available");
        }



        if(model.IsSpecialOrdersForPatientAccepted.equals("1"))
        {
            b.lblFavSpecialOrdersPatient.setText("YES");
        }
        else {
            b.lblFavSpecialOrdersPatient.setText("Not  Meal Available For Patient");
        }

        b.lblFavMonthlyRate.setText(model.MonthlyRate);
        b.lblFavDailyRate.setText(model.DailyRate);
        b.lblFavTime.setText(model.StartTimeMorning+"A.M"+"TO"+model.CloseTimeMorning+"A.M"+ "&"+model.StartTimeEvening+"P.M"+"TO"+model.CloseTimeEvening+"P.M");
        b.lblFavNotes.setText(model.Note);
        b.lblFavContactNo.setText(model.ContactNo1);
        b.lblFavContactAddress.setText(model.Location);


    }
}