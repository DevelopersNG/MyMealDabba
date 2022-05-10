package com.example.mymealdabba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.Messdeatlislist;
import com.example.mymealdabba.databinding.ActivityNavigationBinding;
import com.example.mymealdabba.model.DataModelCity;
import com.example.mymealdabba.model.DataModelMessDetailsList;
import com.example.mymealdabba.ui.bookmark.BookmarkFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActivityNavigationBinding binding;
    Context context;
    String url = Utils.URL + "getMessList";
    DataModelMessDetailsList data;
    String cityId;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = NavigationActivity.this;
       // setSupportActionBar(binding.appBarNavigation.toolbar);
        cityId = getIntent().getStringExtra("id");
        Log.e("cityid",cityId);
        DrawerLayout drawer = binding.drawerLayout;
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
                    Intent intent = new Intent(context, BookmarkFragment.class);
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




        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Mess response", response);
                Gson gson = new Gson();
                data = gson.fromJson(response, DataModelMessDetailsList.class);
                if (data.result == 1) {
                    Log.e("Mess details", new Gson().toJson(data.MessList));

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
                params.put("CityID", cityId);
                Log.e("params", params.toString());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.rvMessList.setLayoutManager(layoutManager);
        binding.rvMessList.setHasFixedSize(true);
        binding.rvMessList.setNestedScrollingEnabled(true);
        Messdeatlislist adapter = new Messdeatlislist(context, data.MessList);
        binding.rvMessList.setAdapter(adapter);
    }
}