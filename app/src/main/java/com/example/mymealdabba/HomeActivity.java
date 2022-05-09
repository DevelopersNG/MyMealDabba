package com.example.mymealdabba;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.CityAdapter;
import com.example.mymealdabba.databinding.ActivityHomeBinding;
import com.example.mymealdabba.model.CityModel;
import com.example.mymealdabba.model.DataModelCity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding b;
    Context context;
    DataModelCity data;
    String url = "https://mymealdabba.com/stage/search/getAllCities";
    CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = this;
        listener();
        getData();
    }


    private void listener() {

        b.svCityList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }


    private void filter(String text) {
        ArrayList<CityModel> filteredList = new ArrayList<>();

        for (CityModel item : data.Cities) {
            if (item.City.toLowerCase().contains(text.toString().toLowerCase(Locale.ROOT))){
                filteredList.add(item);
            }
        }
        if (text.isEmpty()) {
            filteredList.clear();
        }
        setRecyclerView(filteredList);

       //cityAdapter.filterList(filteredList);
    }


    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                Gson gson = new Gson();
                data = gson.fromJson(response, DataModelCity.class);
                if (data.result == 1) {
                    Log.e("cities", new Gson().toJson(data.Cities));
//                    setRecyclerView();
                    setSearch();
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
                Log.e("params", params.toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setSearch() {
        List<String> cities = new ArrayList<>();
        for (CityModel city : data.Cities) {
            cities.add(city.City);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.layout_allcity_iteam, R.id.tvCityName, cities.toArray(new String[0]));
        b.actvHomeSearch.setAdapter(adapter);
    }

    private void setRecyclerView(List<CityModel> cities) {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        b.rvCityName.setLayoutManager(layoutManager);
        b.rvCityName.setHasFixedSize(true);
        b.rvCityName.setNestedScrollingEnabled(true);
        cityAdapter = new CityAdapter(context, cities);
        b.rvCityName.setAdapter(cityAdapter);

    }
}