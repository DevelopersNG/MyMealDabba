package com.example.mymealdabba;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.adapter.MessListAdapter;
import com.example.mymealdabba.databinding.FragmentMessListBinding;
import com.example.mymealdabba.model.DataModelMessDetailsList;
import com.example.mymealdabba.model.MessFilterModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MessListFragment extends Fragment {
    String url = Utils.URL + "getMessList";
    String urlMessName = Utils.URL + "getMessListByCityID";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentMessListBinding b;
    private String mParam1;
    private String mParam2;
    Context context;
    SessionManager sessionManager;
    DataModelMessDetailsList data;
    MessListAdapter messListAdapter;
    HomeViewModel viewModel;

    public MessListFragment() {
        // Required empty public constructor
    }


    public static MessListFragment newInstance(String param1, String param2) {
        MessListFragment fragment = new MessListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentMessListBinding.inflate(inflater, container, false);
        context = getContext();
        sessionManager = new SessionManager(context);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        listener();
//        viewModel._filterMess.postValue(new MessFilterModel("", "", "", "", "", "", "", "", ""));
        viewModel.getMessData();
        return b.getRoot();
    }

    private void listener() {
        viewModel.messList.observe(getViewLifecycleOwner(), list -> {
            setMessRecyclerView(list.MessList);
        });

        viewModel.selectedLocation.observe(getViewLifecycleOwner(), locationModel -> {
            viewModel.getMessData();
        });

        viewModel.filterMess.observe(getViewLifecycleOwner(), filter -> {
            viewModel.getMessData();
        });



        b.svMessList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setMessRecyclerView(viewModel.filter(newText));
                return false;
            }
        });

    }

//    public void getMessData(MessFilterModel filter) {
//       // final ProgressDialog progressDialog = ProgressDialog.show(context, null, "processing...", false, false);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//              //  progressDialog.dismiss();
//                Log.e("Filter response", response);
//                Gson gson = new Gson();
//
//                data = gson.fromJson(response, DataModelMessDetailsList.class);
//                if (data.result == 1) {
//                    Log.e("session city id", sessionManager.getCityId());
////                    setMessRecyclerView();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        //progressDialog.dismiss();
//                        Toast.makeText(context, "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("apikey", Utils.API_KEY);
//                params.put("CityID", sessionManager.getCityId());
//                params.put("UserID", sessionManager.getId());
//                params.put("MessType", filter.MessType);
//                params.put("Service", filter.Service);
//                params.put("SortBy", filter.SortBy);
//                params.put("Category", filter.Category);
//                params.put("Location", filter.Location);
//                params.put("CuisineType", filter.CuisineType);
//                Log.e("Location", filter.Location);
//                Log.e("params", params.toString());
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.myGetMySingleton(context).myAddToRequest(stringRequest);
//    }

    private void setMessRecyclerView(List<Messdeatilslistmodel> MessList) {
        //  GridLayoutManager layoutManager = new GridLayoutManager(context, 2);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            b.rvMessList.setLayoutManager(layoutManager);
            b.rvMessList.setHasFixedSize(true);
            b.rvMessList.setNestedScrollingEnabled(true);
            messListAdapter = new MessListAdapter(context, MessList);
            b.rvMessList.setAdapter(messListAdapter);

        if (messListAdapter.getItemCount() != 0) {
            b.llNoMessData.setVisibility(View.GONE);
            b.rvMessList.setAdapter(messListAdapter);
        } else {
            b.llNoMessData.setVisibility(View.VISIBLE);
        }
    }


    private void filter(String text) {
        ArrayList<Messdeatilslistmodel> filteredList = new ArrayList<>();
        for (Messdeatilslistmodel item : data.MessList) {
            if (item.Location.toLowerCase().contains(text.toLowerCase()) || item.MemberName.toLowerCase(Locale.ROOT).startsWith(text)) {
                filteredList.add(item);
            }
        }
//        if (text.isEmpty()) {
//            filteredList.clear();
//        }
        messListAdapter.filterList(filteredList);
    }



}