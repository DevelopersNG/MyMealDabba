package com.example.mymealdabba;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymealdabba.databinding.FragmentMessFilterBinding;
import com.example.mymealdabba.model.MessFilterModel;


public class MessFilterFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Context context;
    SessionManager sessionManager;
    FragmentMessFilterBinding b;
    HomeViewModel viewModel;
    MessFilterModel model;

    public MessFilterFragment() {

    }


    public static MessFilterFragment newInstance(String param1, String param2) {
        MessFilterFragment fragment = new MessFilterFragment();
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
        b = FragmentMessFilterBinding.inflate(inflater, container, false);
        context = getContext();
        sessionManager = new SessionManager(context);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        model = viewModel._filterMess.getValue();
        listener();
//        getMessFilterData();
        return b.getRoot();
    }

    private void listener() {

        b.btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });


    }

    void submit() {
        if (b.rbRecentlyAdded.isChecked()) {
            model.SortBy = b.rbRecentlyAdded.getText().toString().trim();
        }

        if (b.rbCostHighToLow.isChecked()) {
            model.SortBy = b.rbCostHighToLow.getText().toString().trim();
        }
        if (b.rbCostLowToHigh.isChecked()) {
            model.SortBy = b.rbCostLowToHigh.getText().toString().trim();
        }


        if (b.chkFoodMess.isChecked()) {
            model.MessType = b.chkFoodMess.getText().toString().trim();
        }

        if (b.chkChef.isChecked()) {
            model.MessType = b.chkChef.getText().toString().trim();
        }

        if (b.chkHotel.isChecked()) {
            model.MessType = b.chkHotel.getText().toString().trim();
        }

        if (b.chkMahila.isChecked()) {
            model.MessType = b.chkMahila.getText().toString().trim();
        }

        if (b.chkHomeDelivery.isChecked()) {
            model.Service = b.chkHomeDelivery.getText().toString().trim().trim();
        }

        if (b.chkPremises.isChecked()) {
            model.Service = b.chkPremises.getText().toString().trim().trim();
        }

        if (b.chipVeg.isChecked()) {
            model.Category = b.chipVeg.getText().toString().trim();
        }

        if (b.chipNonVeg.isChecked()) {
            model.Category = b.chipNonVeg.getText().toString().trim();
        }
        viewModel._filterMess.postValue(model);
        dismiss();
    }

}
