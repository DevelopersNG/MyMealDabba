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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.FavoriteAdapter;
import com.example.mymealdabba.adapter.RatingAdapter;
import com.example.mymealdabba.adapter.SliderAdapter;
import com.example.mymealdabba.databinding.ActivityMessDetailsBinding;
import com.example.mymealdabba.model.DataModelMessDetailsList;
import com.example.mymealdabba.model.DataModelRating;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.example.mymealdabba.model.RatingModel;
import com.example.mymealdabba.model.ReviewsModel;
import com.example.mymealdabba.model.SessionModel;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MessDetailsActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Context context;
    ActivityMessDetailsBinding b;
    Messdeatilslistmodel model;
    SliderView sliderView;
    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMessDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        model = new Gson().fromJson(data, Messdeatilslistmodel.class);
        context = this;
//        rvRating=dialog.findViewById(R.id.rvRating);
        getData();
        sessionManager = new SessionManager(context);
        sessionManager.setMemberId(model.MemberID);
        sessionManager.setAvgReview(model.AvgReviews);


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
//                showDialog();

                Intent intent=new Intent(MessDetailsActivity.this,RatingActivity.class);
                startActivity(intent);
            }
        });

        b.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSubText = "\t\n" +
                        "MyMealDabba (Tiffin Service Listings)";
                String shareBodyText = model.MessLink;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(shareIntent, "Share With"));

            }
        });






        sliderView = findViewById(R.id.slider);

        sliderAdapter=new SliderAdapter(model.Images);
        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

//    private void showDialog() {
//        Dialog  dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.bottomsheet_layout_rating);
//        TextView btnRateSubmit = dialog.findViewById(R.id.tvRateSubmit);
//        TextView tvViewAllData = dialog.findViewById(R.id.tvViewAllData);
//        RatingBar ratingbar = dialog.findViewById(R.id.rb_ratingBar);
//        ratingbar.setRating(Float.parseFloat(model.AvgReviews));
//        btnRateSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rating = String.valueOf(ratingbar.getRating());
//                getDataRate();
//                dialog.dismiss();
//                Toast.makeText(MessDetailsActivity.this, rating, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        tvViewAllData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getRatingData();
//            }
//        });
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.dialig_animation;
//        dialog.getWindow().setGravity(Gravity.TOP);
//    }

//    private void getDataRate() {
//        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//                Log.e("response", response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.getString("result");
//                    if (code.equalsIgnoreCase("1")) {
//
//                        Toast.makeText(context, "Thanks for Rating", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                error.printStackTrace();
//                Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("apikey", Utils.API_KEY);
//                params.put("Rating", rating);
//                params.put("ReviewerName", sessionManager.getName());
//                params.put("ReviewerEmail", sessionManager.getEmail());
//                params.put("ReviewerNo", sessionManager.getPhone());
//                params.put("MemberID", model.MemberID);
//
//                Log.e("Reviewer Detail", params.toString());
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
//    }


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

        if (model.IsSpecialOrdersAccepted.equals("1")) {
            b.lblSpecialOrders.setText("YES");
        } else {
            b.lblSpecialOrders.setText("NO");
        }


        if (model.IsSpecialOrdersForPatientAccepted.equals("1")) {
            b.lblSpecialOrdersPatient.setText("YES");
        } else {
            b.lblSpecialOrdersPatient.setText("NO");
        }

        ((MessDetailsActivity) context).b.mtbNavigationMess.setTitle(model.MemberName);

//        for (ImageModel image : model.Images) {
//            Log.e("image", Utils.IMAGEURL + image.ImagePath);
//            if (image.IsDefault.equalsIgnoreCase("1")) {
//                Glide.with(context)
//                        .load(Utils.IMAGEURL + image.ImagePath)
//                        .into(b.ivMess);
//
//                break;
//            } else {
//                Glide.with(context)
//                        .load(Utils.IMAGEURL + image.ImagePath)
//                        .into(b.ivMess);
//            }
//
//        }


        b.txtAvgRating.setText(model.AvgReviews);

        b.tbMessDetailFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b.tbMessDetailFav.isChecked()) {
                    Utils.favourite(context, model.MemberID);
                    model.BookMarksStatus = "1";
                } else {
                    Utils.favourite(context, model.MemberID);
                    model.BookMarksStatus = "0";
                }
            }
        });

        b.tbMessDetailFav.setChecked(model.BookMarksStatus.equals("1"));
        b.lblMonthlyRate.setText(model.MonthlyRate);
        b.lblDailyRate.setText(model.TiffinRate);
        b.lblTime.setText(model.StartTimeMorning + "A.M" + " TO " + model.CloseTimeMorning + "A.M" + " & " + model.StartTimeEvening + "P.M" + " TO " + model.CloseTimeEvening + "P.M");
        b.lblNotes.setText(model.Note);
        b.lblContactNo.setText(model.ContactNo1);
        b.lblContactAddress.setText(model.Location);

    }

//    private void getRatingData() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRating, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("Ratings response", response);
//                Gson gson = new Gson();
//                data = gson.fromJson(response, DataModelRating.class);
//                if (data.result == 1) {
//                    setRecyclerView();
//
//                    RatingModel ratingModel = gson.fromJson(String.valueOf((response)), RatingModel.class);
//                    sessionManager.createReviewerDetails(ratingModel);
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("apikey", Utils.API_KEY);
//                params.put("MemberID",model.MemberID);
//                Log.e("Rating", params.toString());
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
//    }


//    private void setRecyclerView() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        rvRating.setLayoutManager(layoutManager);
//        rvRating.setHasFixedSize(true);
//        rvRating.setNestedScrollingEnabled(true);
//        RatingAdapter adapter = new RatingAdapter(context,data.reviews);
//        rvRating.setAdapter(adapter);
//    }


}