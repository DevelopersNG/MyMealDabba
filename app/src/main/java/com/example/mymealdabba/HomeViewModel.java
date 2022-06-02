package com.example.mymealdabba;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mymealdabba.model.DataModelMessDetailsList;
import com.example.mymealdabba.model.LocationModel;
import com.example.mymealdabba.model.MessFilterModel;
import com.example.mymealdabba.model.MessNameListModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {
    SessionManager sessionManager = new SessionManager(getApplication());

    public MutableLiveData<LocationModel> _selectedLocation = new MutableLiveData<>();
    public LiveData<LocationModel> selectedLocation = _selectedLocation;

    public MutableLiveData<MessFilterModel> _filterMess = new MutableLiveData<>(new MessFilterModel("", "", "", "", "", "", "", "", ""));
    public LiveData<MessFilterModel> filterMess = _filterMess;

    public MutableLiveData<MessNameListModel> _filterMessSearch = new MutableLiveData<>();
    public LiveData<MessNameListModel> filterMessSearch = _filterMessSearch;

    public MutableLiveData<DataModelMessDetailsList> _messList = new MutableLiveData<>();
    public LiveData<DataModelMessDetailsList> messList = _messList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMessData() {

        String url = Utils.URL + "getMessList";
//        MessFilterModel messFilterModel = new MessFilterModel("", "", "", "", "", "", "", "", "");
        /*if (_filterMess.getValue() != null) {
            messFilterModel = _filterMess.getValue();
        }*/
        MessFilterModel messFilterModel = _filterMess.getValue();
        if (_selectedLocation.getValue() != null) {
            messFilterModel.Location = _selectedLocation.getValue().Location;
        }


        final MessFilterModel filter = messFilterModel;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e("Filter response", response);
                Gson gson = new Gson();
                DataModelMessDetailsList data = gson.fromJson(response, DataModelMessDetailsList.class);
                if (data != null && (data.result == 1 || data.result == 0)) {
                    _messList.postValue(data);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Toast.makeText(getApplication(), "Sorry, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("apikey", Utils.API_KEY);
                params.put("CityID", sessionManager.getCityId());
                params.put("UserID", sessionManager.getId());


                if (filter != null && filter.MessType != null && !filter.MessType.isEmpty()) {
                    params.put("MessType", filter.MessType);
                }
                if (filter != null && filter.Service != null && !filter.Service.isEmpty()) {
                    params.put("Service", filter.Service);
                }
                if (filter != null && filter.SortBy != null && !filter.SortBy.isEmpty()) {
                    params.put("SortBy", filter.SortBy);
                }
                if (filter != null && filter.Category != null && !filter.Category.isEmpty()) {
                    params.put("Category", filter.Category);
                }
                if (filter != null && filter.Location != null && !filter.Location.isEmpty()) {
                    params.put("Location", filter.Location);
                }
                if (filter != null && filter.CuisineType != null && !filter.CuisineType.isEmpty()) {
                    params.put("CuisineType", filter.CuisineType);
                }
//                Log.e("Location", filter.Location);
                Log.e("params", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.myGetMySingleton(getApplication()).myAddToRequest(stringRequest);
    }

    public List<Messdeatilslistmodel> filter(String text) {
        ArrayList<Messdeatilslistmodel> filteredList = new ArrayList<>();
        if (_messList.getValue() != null) {
            for (Messdeatilslistmodel item : _messList.getValue().MessList) {
                if (item.Location.toLowerCase().contains(text.toLowerCase()) || item.MemberName.toLowerCase(Locale.ROOT).startsWith(text)) {
                    filteredList.add(item);
                }
            }
        }
//        if (text.isEmpty()) {
//            filteredList.clear();
//        }
        return filteredList;
    }
}
