package com.example.mymealdabba;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.FavoriteAdapter;
import com.example.mymealdabba.databinding.ActivityBookMarkBinding;
import com.example.mymealdabba.model.DataModelMessDetailsList;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookMarkActivity extends AppCompatActivity {
    ActivityBookMarkBinding b;
    DataModelMessDetailsList data;
    Context context;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    String url = Utils.URL + "getMessList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityBookMarkBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = this;
        sessionManager = new SessionManager(context);
        getData();
        listener();
    }

    private void listener() {
        b.srlRecycleBookmark.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        b.mtbBookmark.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getData() {
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                b.srlRecycleBookmark.setRefreshing(false);
                progressDialog.dismiss();
                Log.e("Bookmark response", response);
                Gson gson = new Gson();
                data = gson.fromJson(response, DataModelMessDetailsList.class);
                if (data.result == 1) {
                    setRecyclerView();
                }
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                       b.srlRecycleBookmark.setRefreshing(false);
                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey", Utils.API_KEY);
                params.put("Bookmarks","1");
                params.put("UserID", sessionManager.getId());
                Log.e("params", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            b.rvFavorite.setLayoutManager(layoutManager);
            b.rvFavorite.setHasFixedSize(true);
            b.rvFavorite.setNestedScrollingEnabled(true);
            FavoriteAdapter adapter = new FavoriteAdapter(context,data.MessList );
            b.rvFavorite.setAdapter(adapter);
        if (adapter.getItemCount() != 0) {
            b.llNoData.setVisibility(View.GONE);
            b.rvFavorite.setVisibility(View.VISIBLE);

        } else {
            b.llNoData.setVisibility(View.VISIBLE);
        }
    }
}
