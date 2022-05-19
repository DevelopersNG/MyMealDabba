package com.example.mymealdabba;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.LocationAdapter;
import com.example.mymealdabba.databinding.FragmentLocationBinding;
import com.example.mymealdabba.model.DataModelLocation;
import com.example.mymealdabba.model.LocationModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class LocationFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String urlLocation = Utils.URL + "getLocationsByCityID";
    FragmentLocationBinding b;
    Context context;
    SessionManager sessionManager;
    DataModelLocation data1;
    LocationAdapter locationAdapter;
    HomeViewModel viewModel;

    public LocationFragment() {

    }

    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int getTheme() {
        return R.style.FullScreenDialogTheme;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentLocationBinding.inflate(inflater, container, false);
        context = getContext();
        sessionManager = new SessionManager(context);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        listener();
        getLocationData();
        return b.getRoot();
    }

    private void listener() {

        b.svLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (locationAdapter != null) {
                    filterLocation(newText);
                }
                return false;
            }
        });

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
                params.put("CityID", sessionManager.getCityId());
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
        b.rvLocation.setLayoutManager(layoutManager);
        b.rvLocation.setHasFixedSize(true);
        b.rvLocation.setNestedScrollingEnabled(true);
        locationAdapter = new LocationAdapter(context, data1.Locations, viewModel, this);
        b.rvLocation.setAdapter(locationAdapter);
    }


    private void filterLocation(String text) {
        ArrayList<LocationModel> filteredList = new ArrayList<>();
        for (LocationModel item : data1.Locations) {
            if (item.Location.toLowerCase().startsWith(text.toLowerCase(Locale.ROOT), 0)) {
                filteredList.add(item);
            }
        }
        if (text.isEmpty()) {
            filteredList.clear();
        }
        locationAdapter.filterList(filteredList);
    }
}