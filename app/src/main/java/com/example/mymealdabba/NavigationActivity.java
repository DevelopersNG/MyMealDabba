package com.example.mymealdabba;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.mymealdabba.adapter.LocationAdapter;
import com.example.mymealdabba.adapter.MessListAdapter;
import com.example.mymealdabba.adapter.MessNameAdapter;
import com.example.mymealdabba.databinding.ActivityNavigationBinding;
import com.example.mymealdabba.model.DataModelLocation;
import com.example.mymealdabba.model.DataModelMessDetailsList;
import com.example.mymealdabba.model.DataModelMessNameList;
import com.example.mymealdabba.model.LocationModel;
import com.google.android.material.navigation.NavigationView;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActivityNavigationBinding binding;
    Context context;
    String url = Utils.URL + "getMessList";
    String urlMessName = Utils.URL + "getMessListByCityID";
    DataModelMessDetailsList data;
    String cityId;
    SessionManager sessionManager;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String id;
    String location;
    String MessName;
    DataModelLocation data1;
    DataModelMessNameList data2;
    String urlLocation = Utils.URL + "getLocationsByCityID";
    LocationAdapter locationAdapter;
    MessNameAdapter messNameAdapter;
    MessListAdapter messListAdapter;

//    Handler handler = new Handler();
//    Runnable runnable;
//    int delay = 10000;

    HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = NavigationActivity.this;
        //setSupportActionBar(binding.appBarNavigation.toolbar);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        cityId = getIntent().getStringExtra("id");
        location = getIntent().getStringExtra("location");
        MessName = getIntent().getStringExtra("messName");
        DrawerLayout drawer = binding.drawerLayout;
        sessionManager = new SessionManager(context);
        id = sessionManager.getId();
        listener();

        setSupportActionBar(binding.mtbNavigation);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mtbNavigation, R.string.navigation_open, R.string.navigation_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        TextView tvEmailHeader= binding.nvHeader.getHeaderView(0).findViewById(R.id.tvEmailHeader);
        tvEmailHeader.setText(sessionManager.getEmail());
        TextView tvUserPhoneHeader= binding.nvHeader.getHeaderView(0).findViewById(R.id.tvUserPhoneHeader);
        tvUserPhoneHeader.setText(sessionManager.getPhone());
        Log.e("name",sessionManager.getName());

    }

    private void listener() {
        viewModel.selectedLocation.observe(this, locationModel -> {
            binding.btnLocation.setText(locationModel.Location);

        });
        binding.nvHeader.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());
//               binding.nvHeader.setCheckedItem(id);
                if (id == R.id.nav_home) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_work) {
                    Intent intent = new Intent(context, SlideActivity.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
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
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;

                } else if (id == R.id.nav_aboutUs) {
                    Intent intent = new Intent(context, AboutUs.class);
                    startActivity(intent);
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
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
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
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
//                } else if (id == R.id.nav_location) {
//                    Intent intent = new Intent(context, UserCurrentLocationActivity.class);
//                    startActivity(intent);
//                    binding.drawerLayout.closeDrawer(GravityCompat.START);
//                    return true;
//
//                }
                return false;
            }
        });

        binding.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LocationFragment().show(((FragmentActivity) context).getSupportFragmentManager(), "location_fragment");

            }
        });



        binding.btnMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessFilterFragment().show(((FragmentActivity) context).getSupportFragmentManager(), "MessFilterFragment");

            }
        });


        binding.btnCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessCuisineFragment().show(((FragmentActivity) context).getSupportFragmentManager(), "MessCuisineFragment");

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


}