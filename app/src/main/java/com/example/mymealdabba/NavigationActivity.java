package com.example.mymealdabba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.LocationAdapter;
import com.example.mymealdabba.adapter.MessListAdapter;
import com.example.mymealdabba.databinding.ActivityNavigationBinding;
import com.example.mymealdabba.model.DataModelLocation;
import com.example.mymealdabba.model.DataModelMessDetailsList;
import com.example.mymealdabba.model.LocationModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActivityNavigationBinding binding;
    Context context;
    String url = Utils.URL + "getMessList";
    DataModelMessDetailsList data;
    String cityId;
    SessionManager sessionManager;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String id;
    String location;
    DataModelLocation data1;
    String urlLocation = Utils.URL + "getLocationsByCityID";
    LocationAdapter locationAdapter;
    MessListAdapter messListAdapter;

//    Handler handler = new Handler();
//    Runnable runnable;
//    int delay = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = NavigationActivity.this;
        //setSupportActionBar(binding.appBarNavigation.toolbar);
        cityId = getIntent().getStringExtra("id");
        location = getIntent().getStringExtra("location");
        DrawerLayout drawer = binding.drawerLayout;
        sessionManager = new SessionManager(context);
        id = sessionManager.getId();

        listener();
        getMessData();
        getLocationData();


//        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_Registration, R.id.nav_works, R.id.nav_specialOffer, R.id.nav_bookmark, R.id.nav_shareApp, R.id.nav_rateUs, R.id.nav_aboutUs, R.id.nav_connect)
//                .setOpenableLayout(drawer)
//                .build();
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //  NavigationUI.setupWithNavController(navigationView, navController);
        setSupportActionBar(binding.mtbNavigation);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mtbNavigation, R.string.navigation_open, R.string.navigation_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        binding.nvHeader.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());

                if (id == R.id.nav_home) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_works) {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_specialOffer) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://mymealdabba.com/site/offers"));
                    try{
                        startActivity(intent);
                    }
                    catch(Exception e){
                        intent.setData(Uri.parse("https://mymealdabba.com/site/offers"));
                    }
                }else if(id==R.id.nav_bookmark) {
                    Intent intent = new Intent(context, BookMarkActivity.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                }else if(id==R.id.nav_aboutUs) {
                    Intent intent = new Intent(context, AboutUs.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                }else if(id==R.id.nav_Registration) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://mymealdabba.com/register"));
                    try{
                        startActivity(intent);
                    }
                    catch(Exception e){
                       // intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
                    }

                }else if(id==R.id.nav_connect) {
                    Intent intent = new Intent(context, ConnectWithActivity.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                }else if(id==R.id.nav_rateUs) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
                    try{
                        startActivity(intent);
                    }
                    catch(Exception e){
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=app.com.mymealdabba"));
                    }
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
                }
                return false;
            }
        });


    }

//    @Override
//    protected void onResume() {
//        handler.postDelayed(runnable = new Runnable() {
//            public void run() {
//                handler.postDelayed(runnable, delay);
//
//                binding.llSearch.setVisibility(View.GONE);
//            }
//        }, delay);
//        super.onResume();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
//    }

    private void listener() {
        binding.svLocationList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (locationAdapter != null) {
                    filterLocation(newText);
                }
                if (messListAdapter != null) {
                    filterMess(newText);
                }
                return false;
            }
        });


//        binding.btnLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                binding.llSearch.setVisibility(View.VISIBLE);
//            }
//        });

        binding.toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    binding.btnLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            binding.llSearch.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    private void filterLocation(String text) {
        ArrayList<LocationModel> filteredList = new ArrayList<>();
        for (LocationModel item : data1.Locations) {
            if (item.Location.toLowerCase().startsWith(text.toString().toLowerCase(Locale.ROOT), 0)) {
                filteredList.add(item);
            }
        }
        if (text.isEmpty()) {
            filteredList.clear();
            binding.rvLocationName.setVisibility(View.GONE);
        } else {
            binding.rvLocationName.setVisibility(View.VISIBLE);
        }

        locationAdapter.filterList(filteredList);
        //setLocationRecyclerView(filteredList);
    }

    //    private void setSearch() {
//        List<String> Locations = new ArrayList<>();
//        for (LocationModel location : data1.Locations) {
//
//            Locations.add(location.Location);
//
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.layout_allcity_iteam, R.id.tvCityName, Locations.toArray(new String[0]));
//        binding.actvLocationSearch.setAdapter(adapter);
//
//           binding.actvLocationSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//               @Override
//               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                   filterMess(Locations.get(i));
//               }
//           });
//    }
    private void filterMess(String text) {
        ArrayList<Messdeatilslistmodel> filteredList = new ArrayList<>();
        for (Messdeatilslistmodel item : data.MessList) {
            if (item.Location.toLowerCase().contains(text.toString().toLowerCase(Locale.ROOT))) {
                filteredList.add(item);
            }
        }
//        if (text.isEmpty()) {
//            filteredList.clear();
//        }

        messListAdapter.filterList(filteredList);
    }


    public void getLocationData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(" Location response", response);
                Gson gson = new Gson();

                data1 = gson.fromJson(response, DataModelLocation.class);
                if (data1.Locations != null) {
                    Log.e("Locations", new Gson().toJson(data1.Locations));
                    setLocationRecyclerView();
                    //setSearch();

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
                params.put("CityID", cityId);
                Log.e("params", params.toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }


    public void getMessData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Mess response", response);
                Gson gson = new Gson();

                data = gson.fromJson(response, DataModelMessDetailsList.class);
                if (data.result == 1) {
                    sessionManager.setCityId(cityId);
                    Log.e("session city id", sessionManager.getCityId());
                    setMessRecyclerView();
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
                params.put("CityID", cityId);
                Log.e("params", params.toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }


    private void setLocationRecyclerView() {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.rvLocationName.setLayoutManager(layoutManager);
        binding.rvLocationName.setHasFixedSize(true);
        binding.rvLocationName.setNestedScrollingEnabled(true);
        locationAdapter = new LocationAdapter(context, data1.Locations);
        binding.rvLocationName.setAdapter(locationAdapter);
    }

    private void setMessRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.rvMessList.setLayoutManager(layoutManager);
        binding.rvMessList.setHasFixedSize(true);
        binding.rvMessList.setNestedScrollingEnabled(true);
        messListAdapter = new MessListAdapter(context, data.MessList);
        binding.rvMessList.setAdapter(messListAdapter);
    }
}