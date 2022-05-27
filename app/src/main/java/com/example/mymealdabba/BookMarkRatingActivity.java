package com.example.mymealdabba;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.RatingAdapter;
import com.example.mymealdabba.databinding.ActivityBookMarkRatingBinding;
import com.example.mymealdabba.model.DataModelRating;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.example.mymealdabba.model.RatingModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BookMarkRatingActivity extends AppCompatActivity {
    ActivityBookMarkRatingBinding b;
    String urlRating = Utils.URL + "getMessReview";
    Context context;
    String url = Utils.URL + "addReview";
    SessionManager sessionManager;
    String rating;
    Messdeatilslistmodel model;
    DataModelRating data;
    Double count1;
    int count2;
    int count3;
    int count4;
    int count5;
    boolean flag1 = true, flag2 = true, flag3 = true, flag4 = true, flag5 = true;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark_rating);
        b = ActivityBookMarkRatingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        model = new Gson().fromJson(data, Messdeatilslistmodel.class);

        context = this;
        getRatingData();
        sessionManager = new SessionManager(context);
        listener();
        b.BAvgRating.setText(sessionManager.getAvgReviewB());
        b.BTvRatingCount.setText(model.TotalReviews + " Rating(S)");

        if(model.OneRating.equals("0"))
        {
            b.rbRating.setRating(0);
        }
        else
        {
            int progress1 = (((Integer.parseInt(model.AvgReviews) * Integer.parseInt(model.OneRating)) + 1 ) / Integer.parseInt(model.TotalReviews) + 1);
            b.progressbar1.setProgress(progress1*20);
            Log.e("progress1", String.valueOf(progress1));

        }

        if(model.TwoRating.equals("0"))
        {
            b.rbRating.setRating(0);
        }
        else
        {
            int progress2 = (((Integer.parseInt(model.AvgReviews) * Integer.parseInt(model.TwoRating)) + 2 )/ Integer.parseInt(model.TotalReviews) + 1);
            b.progressbar2.setProgress(progress2*20);
            Log.e("progress2", String.valueOf(progress2));

        }

        if(model.ThreeRating.equals("0"))
        {
            b.rbRating.setRating(0);
        }
        else
        {
            int progress3 = (((Integer.parseInt(model.AvgReviews) * Integer.parseInt(model.ThreeRating)) + 3 )/ Integer.parseInt(model.TotalReviews) + 1);
            b.progressbar3.setProgress(progress3*20);
            Log.e("progress3", String.valueOf(progress3));

        }

        if(model.FourRating.equals("0"))
        {
            b.rbRating.setRating(0);
        }
        else
        {
            int progress4 = (((Integer.parseInt(model.AvgReviews) * Integer.parseInt(model.FourRating)) + 4 )/ Integer.parseInt(model.TotalReviews) + 1);
            b.progressbar4.setProgress(progress4*20);
            Log.e("progress4", String.valueOf(progress4));

        }

        if(model.FiveRating.equals("0"))
        {
            b.rbRating.setRating(0);
        }
        else
        {
            int progress5 = (((Integer.parseInt(model.AvgReviews) * Integer.parseInt(model.FiveRating)) + 5 )/ Integer.parseInt(model.TotalReviews) + 1);
            b.progressbar5.setProgress(progress5*20);
            Log.e("progress5", String.valueOf(progress5));
        }


    }


    private void listener() {
        b.tvBRateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = String.valueOf(b.rbRating.getRating());
                getDataRate();
                Toast.makeText(com.example.mymealdabba.BookMarkRatingActivity.this, rating, Toast.LENGTH_SHORT).show();

            }
        });

        b.tvBShowRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.rvBRatingDetail.setVisibility(View.VISIBLE);
            }
        });

        b.mtbRateUsBookmark.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                Log.e("bOOK MARKReviewer Detail", params.toString());
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
                    sessionManager.BookmarkReviewerDetails(ratingModel);
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
                params.put("MemberID", sessionManager.getMemberIdB());
                Log.e("Rating", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        b.rvBRatingDetail.setLayoutManager(layoutManager);
        b.rvBRatingDetail.setHasFixedSize(true);
        b.rvBRatingDetail.setNestedScrollingEnabled(true);
        RatingAdapter adapter = new RatingAdapter(context, data.reviews);
        b.rvBRatingDetail.setAdapter(adapter);


        if (adapter.getItemCount() != 0) {
            b.llNoMessDataRating.setVisibility(View.GONE);
            b.rvBRatingDetail.setAdapter(adapter);
        } else {
            b.llNoMessDataRating.setVisibility(View.VISIBLE);
        }
    }


}