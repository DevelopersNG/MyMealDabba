package com.example.mymealdabba;

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
    }

    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Bookmark response", response);
                Gson gson = new Gson();
                data = gson.fromJson(response, DataModelMessDetailsList.class);
                if (data.result == 1) {
                    setRecyclerView();
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
                params.put("CityID", sessionManager.getCityId());
                params.put("UserID", sessionManager.getId());
                Log.e("params", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {

        List<Messdeatilslistmodel> list = new ArrayList<>();

        for (Messdeatilslistmodel m : data.MessList) {
            if ((m.BookMarksStatus.equals("1"))) {
                list.add(m);
            }
        }


        if (list.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            b.rvFavorite.setLayoutManager(layoutManager);
            b.rvFavorite.setHasFixedSize(true);
            b.rvFavorite.setNestedScrollingEnabled(true);
            FavoriteAdapter adapter = new FavoriteAdapter(context, list);
            b.rvFavorite.setAdapter(adapter);
            b.llNoData.setVisibility(View.GONE);
            b.rvFavorite.setVisibility(View.VISIBLE);

        } else {
            b.llNoData.setVisibility(View.VISIBLE);
        }

    }


}
