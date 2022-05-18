package com.example.mymealdabba;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymealdabba.databinding.FragmentMessCuisineBinding;
import com.example.mymealdabba.databinding.FragmentMessFilterBinding;
import com.example.mymealdabba.model.CuisineModel;
import com.example.mymealdabba.model.MessFilterModel;

import retrofit2.http.GET;

public class MessCuisineFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Context context;
    SessionManager sessionManager;
    FragmentMessCuisineBinding b;
    HomeViewModel viewModel;
    CuisineModel model;
    public MessCuisineFragment() {
        // Required empty public constructor
    }

    public static MessCuisineFragment newInstance(String param1, String param2) {
        MessCuisineFragment fragment = new MessCuisineFragment();
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
        b = FragmentMessCuisineBinding.inflate(inflater, container, false);
        context = getContext();
        sessionManager = new SessionManager(context);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        model = viewModel._filterCuisineMess.getValue();
        listener();
//        getMessFilterData();
        return b.getRoot();
    }

    private void listener() {
        b.btnApplyCuisineFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
//        if (b.rbRecentlyAdded.isChecked()) {
//            model.CuisineType = b.rbRecentlyAdded.getText().toString().trim();
//        }
    }


}