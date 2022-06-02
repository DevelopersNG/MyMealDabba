package com.example.mymealdabba;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentActivity;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
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
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        context = this;
        getData();
        sessionManager = new SessionManager(context);

        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        setSupportActionBar(b.mtbNavigation);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, b.home, b.mtbNavigation, R.string.navigation_open, R.string.navigation_close);
        b.home.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


      TextView tvEmailHeader= b.nvHeader.getHeaderView(0).findViewById(R.id.tvEmailHeader);
      tvEmailHeader.setText(sessionManager.getEmail());
        TextView tvUserPhoneHeader= b.nvHeader.getHeaderView(0).findViewById(R.id.tvUserPhoneHeader);
        tvUserPhoneHeader.setText(sessionManager.getPhone());
        Log.e("name",sessionManager.getName());


//
        b.nvHeader.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());

                if (id == R.id.nav_home) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    b.home.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_work) {
                    Intent intent = new Intent(context, SlideActivity.class);
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
                }
                return false;
            }
        });


//        b.ibLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new CurrentLocationFragment().show(getSupportFragmentManager(), "CurrentLocationFragment");
//
//            }
//        });


    }

    private InstallStateUpdatedListener installStateUpdatedListener =new InstallStateUpdatedListener()
    {
        @Override
        public void onStateUpdate(InstallState state)
        {
            if(state.installStatus() == InstallStatus.DOWNLOADED)
            {
                showCompletedUpdate();
            }
        }
    };



    @Override
    protected void onStop()
    {
        //if(mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }

    private void showCompletedUpdate()
    {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"New app is ready!",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {

            Toast.makeText(context,"cancel",Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();


        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>()
        {
            @Override
            public void onSuccess(AppUpdateInfo result)
            {
                if(result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                {
                    try
                    {
                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE, HomeActivity.this
                                ,RC_APP_UPDATE);

                    } catch (IntentSender.SendIntentException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    private void filter(String text) {
        ArrayList<CityModel> filteredList = new ArrayList<>();
        for (CityModel item : data.Cities) {
            if (item.City.toLowerCase().startsWith(text.toString().toLowerCase(Locale.ROOT), 0)) {
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