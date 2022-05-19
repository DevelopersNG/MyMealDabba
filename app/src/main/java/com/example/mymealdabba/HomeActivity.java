package com.example.mymealdabba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
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
import com.google.android.material.navigation.NavigationView;
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
    SessionManager sessionManager;
    ActionBarDrawerToggle actionBarDrawerToggle;
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


        setSupportActionBar(b.mtbNavigation);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, b.home, b.mtbNavigation, R.string.navigation_open, R.string.navigation_close);
        b.home.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void listener() {
        b.nvHeader.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());

                if (id == R.id.nav_home) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    b.home.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_works) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    b.home.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_specialOffer) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://mymealdabba.com/site/offers"));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        intent.setData(Uri.parse("https://mymealdabba.com/site/offers"));
                    }
                } else if (id == R.id.nav_bookmark) {
                    Intent intent = new Intent(context, BookMarkActivity.class);
                    startActivity(intent);
                    b.home.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_aboutUs) {
                    Intent intent = new Intent(context, AboutUs.class);
                    startActivity(intent);
                    b.home.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_Registration) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://mymealdabba.com/register"));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        // intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
                    }

                } else if (id == R.id.nav_connect) {
                    Intent intent = new Intent(context, ConnectWithActivity.class);
                    startActivity(intent);
                    b.home.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_rateUs) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
                    }


                } else if (id == R.id.nav_logout) {
                    sessionManager.logoutUser();

                } else if (id == R.id.nav_shareApp) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareSubText = "\t\n" +
                            "MyMealDabba (Tiffin Service Listings)";
                    String shareBodyText = "https://play.google.com/store/apps/details?id=app.com.mymealdabba";
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                    startActivity(Intent.createChooser(shareIntent, "Share With"));
                    return true;
                } else if (id == R.id.nav_location) {
                    Intent intent = new Intent(context, UserCurrentLocationActivity.class);
                    startActivity(intent);
                    b.home.closeDrawer(GravityCompat.START);
                    return true;

                }
                return false;
            }
        });


    }




    private void filter(String text) {
        ArrayList<CityModel> filteredList = new ArrayList<>();
        for (CityModel item : data.Cities) {
            if (item.City.toLowerCase().startsWith(text.toString().toLowerCase(Locale.ROOT),0)){
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
        final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();                Log.e("response", response);
                Gson gson = new Gson();

                data = gson.fromJson(response, DataModelCity.class);
                if (data.result == 1) {
                    Log.e("cities", new Gson().toJson(data.Cities));
                  //  setSearch();
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
            }
        },
                new Response.ErrorListener() {
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