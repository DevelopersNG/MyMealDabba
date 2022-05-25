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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.SliderAdapter;
import com.example.mymealdabba.databinding.ActivityBookmarkDetailBinding;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookmarkDetailActivity extends AppCompatActivity {

    String url = Utils.URL + "addReview";
    SessionManager sessionManager;
    Context context;
    ActivityBookmarkDetailBinding b;
    Messdeatilslistmodel model;
    String rating;
    SliderView sliderView;
    SliderAdapter sliderAdapter;

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
                showDialog();
            }
        });


        b.btnFavViewContactDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.relativeLayoutFavContactDetails.setVisibility(View.VISIBLE);
            }
        });


        b.mtbNavigationBookmark.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        sliderView = findViewById(R.id.Bookmarkslider);

        sliderAdapter = new SliderAdapter(model.Images);
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


    private void showDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout_rating);
        RatingBar ratingbar = dialog.findViewById(R.id.ratingBar);
        ratingbar.setRating(Float.parseFloat(model.AvgReviews));
        TextView btnRateSubmit = dialog.findViewById(R.id.btnRateSubmit);
        btnRateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rating = String.valueOf(ratingbar.getRating());

                getDataRate();
                dialog.dismiss();
                Toast.makeText(BookmarkDetailActivity.this, rating, Toast.LENGTH_SHORT).show();

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
                params.put("ReviewerEmail", sessionManager.getEmail());
                params.put("ReviewerNo", sessionManager.getPhone());
                params.put("MemberID", model.MemberID);
                Log.e("user", params.toString());
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

        ((BookmarkDetailActivity) context).b.mtbNavigationBookmark.setTitle(model.MemberName);

        b.lblFavMessName.setText(model.MemberName);
        b.lblFavMessAddress.setText(model.BussinessAddress);
        b.lblFavType.setText(model.Type);
        b.lblFavExperience.setText(model.ExpYears);

        b.lblFavCategory.setText(model.Category);
        b.lblFavCuisineType.setText(model.CuisineType);
        b.lblFavService.setText(model.Service);

        if (model.IsSpecialOrdersAccepted.equals("1")) {
            b.lblFavSpecialOrders.setText("YES");
        } else {
            b.lblFavSpecialOrders.setText("No");
        }


        if (model.IsSpecialOrdersForPatientAccepted.equals("1")) {
            b.lblFavSpecialOrdersPatient.setText("YES");
        } else {
            b.lblFavSpecialOrdersPatient.setText("No");
        }

        b.lblFavMonthlyRate.setText(model.MonthlyRate);
        b.lblFavDailyRate.setText(model.TiffinRate);
        b.lblFavTime.setText(model.StartTimeMorning + " A.M " + " TO " + model.CloseTimeMorning + " A.M " + " & " + model.StartTimeEvening + " P.M " + " TO " + model.CloseTimeEvening + " P.M ");
        b.lblFavNotes.setText(model.Note);
        b.lblFavContactNo.setText(model.ContactNo1);
        b.lblFavContactAddress.setText(model.Location);
        b.txtFavAvgRating.setText(model.AvgReviews);


        b.imageFavViewShare.setOnClickListener(new View.OnClickListener() {
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


//        for (ImageModel image : model.Images) {
//            Log.e("image", Utils.IMAGEURL + image.ImagePath);
//            if (image.IsDefault.equalsIgnoreCase("1")) {
//                Glide.with(context)
//                        .load(Utils.IMAGEURL + image.ImagePath)
//                        .into(b.ivBookmark);
//
//                break;
//            } else {
//                Glide.with(context)
//                        .load(Utils.IMAGEURL + image.ImagePath)
//                        .into(b.ivBookmark);
//            }
//
//        }

    }
}