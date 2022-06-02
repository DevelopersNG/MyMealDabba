package com.example.mymealdabba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.RatingAdapter;
import com.example.mymealdabba.databinding.ActivityRatingBinding;
import com.example.mymealdabba.model.DataModelRating;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.example.mymealdabba.model.RatingModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RatingActivity extends AppCompatActivity {
    ActivityRatingBinding b;
    String urlRating = Utils.URL + "getMessReview";
    Context context;
    String url = Utils.URL + "addReview";
    SessionManager sessionManager;
    String rating;
    Messdeatilslistmodel model;
    DataModelRating data;
    int count = 0;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        b = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = this;
        getRatingData();
        sessionManager = new SessionManager(context);
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        model = new Gson().fromJson(data, Messdeatilslistmodel.class);
        listener();
        b.AvgRating.setText(sessionManager.getAvgReview());
        b.tvRatingCount.setText(model.TotalReviews + " Rating(S)");

        if(model.OneRating.equals("0"))
        {
            b.progress1.setProgress(0);
        }
        else
        {
            float progress1 = (((Float.parseFloat(model.AvgReviews) * Float.parseFloat(model.OneRating)) + 1 ) / (Float.parseFloat(model.TotalReviews) + 1));
            b.progress1.setProgress((int)((progress1*20)));
            Log.e("progress1", String.valueOf(progress1));
        }


        if(model.TwoRating.equals("0"))
        {
            b.progress2.setProgress(0);
        }
        else {
            float progress2 = (((Float.parseFloat(model.AvgReviews) * Float.parseFloat(model.TwoRating)) + 2) / (Float.parseFloat(model.TotalReviews) + 1));
            b.progress2.setProgress((int) (progress2 * 20));
            Log.e("progress2", String.valueOf(progress2));
        }
        if(model.ThreeRating.equals("0"))
        {
            b.progress3.setProgress(0);
        }
        else
        {
            float progress3 = (((Float.parseFloat(model.AvgReviews) * Float.parseFloat(model.ThreeRating)) + 3 )/ (Float.parseFloat(model.TotalReviews) + 1));
            b.progress3.setProgress((int)((progress3 * 20)));
            Log.e("progress3", String.valueOf(progress3));

        }

        if(model.FourRating.equals("0"))
        {
            b.progress4.setProgress(0);
        }
        else
        {
            float progress4 = (((Float.parseFloat(model.AvgReviews) * Float.parseFloat(model.FourRating)) + 4 )/ (Float.parseFloat(model.TotalReviews) + 1));
            b.progress4.setProgress((int)((progress4 * 20)));
            Log.e("progress4", String.valueOf(progress4));

        }

        if(model.FiveRating.equals("0"))
        {
            b.progress5.setProgress(0);
        }
        else
        {
            float progress5 = (((Float.parseFloat(model.AvgReviews) * Float.parseFloat(model.FiveRating)) + 5 )/ (Float.parseFloat(model.TotalReviews) + 1));
            b.progress5.setProgress((int)((progress5 * 20)));
            Log.e("progress5", String.valueOf(progress5));
        }
        if(model.AvgReviews.equals("") && model.AvgReviews.equals("0"))
        {
            b.rbRatingBar.setRating(1);
        }else
        {
            b.rbRatingBar.setRating(Float.parseFloat(model.AvgReviews));

        }

    }


    private void listener() {
       b.tvRateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = String.valueOf(b.rbRatingBar.getRating());
                getDataRate();
                Toast.makeText(RatingActivity.this, rating, Toast.LENGTH_SHORT).show();

            }
        });

       b.tvShowRating.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               b.rvRatingDetail.setVisibility(View.VISIBLE);

           }

       });


        b.mtbRateUs.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        b.rbRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               if(b.rbRatingBar.getRating() >0) {
                    b.tvRateSubmit.setVisibility(View.VISIBLE);
                }
               else {
                   b.tvRateSubmit.setVisibility(View.GONE);
               }
            }
        });
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
                params.put("MemberID", sessionManager.getMemberId());
                Log.e("Reviewer Detail", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void getRatingData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Ratings response", response);
                Gson gson = new Gson();
                data = gson.fromJson(response, DataModelRating.class);
                if (data.result == 1) {
                    setRecyclerView();

                    RatingModel ratingModel = gson.fromJson(String.valueOf((response)), RatingModel.class);
                    sessionManager.createReviewerDetails(ratingModel);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey", Utils.API_KEY);
                params.put("MemberID",sessionManager.getMemberId());
                Log.e("Rating", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        b.rvRatingDetail.setLayoutManager(layoutManager);
        b.rvRatingDetail.setHasFixedSize(true);
        b.rvRatingDetail.setNestedScrollingEnabled(true);
        RatingAdapter adapter = new RatingAdapter(context,data.reviews);
        b.rvRatingDetail.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {
            b.llNoDataRating.setVisibility(View.GONE);
            b.rvRatingDetail.setAdapter(adapter);
        } else {
            b.llNoDataRating.setVisibility(View.VISIBLE);
        }
    }



}