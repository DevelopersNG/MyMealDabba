package com.example.mymealdabba;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.mymealdabba.databinding.ActivityMessDetailsBinding;
import com.example.mymealdabba.model.ImageModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.example.mymealdabba.model.ReviewsModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MessDetailsActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Context context;
    ActivityMessDetailsBinding b;
    Messdeatilslistmodel model;
     String url = Utils.URL + "addReview";
    String rating;

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

        b.mtbNavigationMess.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        b.btnTapToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        b.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSubText = "\t\n" +
                        "MyMealDabba (Tiffin Service Listings)";
                String shareBodyText = Utils.MESS+"36";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(shareIntent, "Share With"));

            }
        });

    }

    private void showDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout_rating);

        Button btnRateSubmit = dialog.findViewById(R.id.btnRateSubmit);
        RatingBar ratingbar=dialog.findViewById(R.id.ratingBar);

        btnRateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rating=String.valueOf(ratingbar.getRating());

                getDataRate();
                dialog.dismiss();
                Toast.makeText(MessDetailsActivity.this,rating, Toast.LENGTH_SHORT).show();

            }
        });



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialig_animation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void getDataRate() {
            final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.e("response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String code = jsonObject.getString("result");
                        if (code.equalsIgnoreCase("1")) {

                            Toast.makeText(context, "Thanks for Rating", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    error.printStackTrace();
                    Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("apikey", Utils.API_KEY);
                    params.put("Rating", rating);
                    params.put("ReviewerName", sessionManager.getName());
                    params.put("ReviewerEmail",sessionManager.getEmail());
                    params.put("ReviewerNo", sessionManager.getPhone());
                    params.put("MemberID", model.MemberID);
                    Log.e("user",params.toString());
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
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



        if(model.IsSpecialOrdersAccepted.equals("1"))
        {
            b.lblSpecialOrders.setText("YES");
        }
        else {
            b.lblSpecialOrders.setText("NO");
        }



        if(model.IsSpecialOrdersForPatientAccepted.equals("1"))
        {
            b.lblSpecialOrdersPatient.setText("YES");
        }
        else {
            b.lblSpecialOrdersPatient.setText("NO");
        }

        ((MessDetailsActivity) context).b.mtbNavigationMess.setTitle(model.MemberName);

        for (ImageModel image : model.Images) {
            Log.e("image",Utils.IMAGEURL+ image.ImagePath);
            if (image.IsDefault.equalsIgnoreCase("1")) {
                Glide.with(context)
                        .load(Utils.IMAGEURL+ image.ImagePath)
                        .into(b.ivMess);

                break;
            }
            else
            {
                Glide.with(context)
                        .load(Utils.IMAGEURL+ image.ImagePath)
                        .into(b.ivMess);
            }

                  b.txtAvgRating.setText(model.AvgReviews);
        }


        b.lblMonthlyRate.setText(model.MonthlyRate);
        b.lblDailyRate.setText(model.DailyRate);
        b.lblTime.setText(model.StartTimeMorning+"A.M"+"TO"+model.CloseTimeMorning+"A.M"+ "&"+model.StartTimeEvening+"P.M"+"TO"+model.CloseTimeEvening+"P.M");
        b.lblNotes.setText(model.Note);
        b.lblContactNo.setText(model.ContactNo1);
        b.lblContactAddress.setText(model.Location);


    }
}