package com.example.mymealdabba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymealdabba.databinding.FragmentMessCuisineBinding;
import com.example.mymealdabba.model.CuisineModel;
import com.example.mymealdabba.model.MessFilterModel;

public class MessCuisineFragment extends DialogFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Context context;

    SessionManager sessionManager;
    FragmentMessCuisineBinding b;
    HomeViewModel viewModel;
    MessFilterModel model;

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
    public int getTheme() {
        return R.style.FullScreenDialogTheme;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentMessCuisineBinding.inflate(inflater, container, false);
        context = getContext();
        sessionManager = new SessionManager(context);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        model = viewModel._filterMess.getValue();
        listener();
//        getMessFilterData();
        return b.getRoot();
    }

    private void listener() {
        viewModel._filterMess.postValue(model);
        viewModel._filterMess.observe(getViewLifecycleOwner(), model -> {
            if (model != null) {
                setData(model);
            }
        });

//        b.mtbCuisineType.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent(getContext(),NavigationActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });

        b.btnApplyCuisineFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        //unselected check box codding....
        b.btnResetCuisineAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.awadhi.setChecked(false);
                b.rbNorthIndian.setChecked(false);
                b.southindia.setChecked(false);
                b.maharashtrian.setChecked(false);
                b.rbNorthIndian.setChecked(false);
                b.goan.setChecked(false);
                b.malwani.setChecked(false);
                b.gujarati.setChecked(false);
                b.rajasthani.setChecked(false);
                b.bengali.setChecked(false);
                b.mughlai.setChecked(false);
                b.mangal.setChecked(false);
                b.punjabi.setChecked(false);

                b.korean.setChecked(false);
                b.awadhi.setChecked(false);
                b.continental.setChecked(false);
                b.chinese.setChecked(false);
                b.jainFood.setChecked(false);

            }
        });


    }

    private void setData(MessFilterModel model) {

//checked cuisine mess type

        String cuisineType = (model.CuisineType);
        Log.e("cuisine type", model.CuisineType);
        String[] cuisine = cuisineType.split(",");

        for (int i = 0; i < (cuisine.length); i++) {
            if (cuisine[i].equalsIgnoreCase(b.rbNorthIndian.getText().toString().trim())) {
                b.rbNorthIndian.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.southindia.getText().toString().trim())) {
                b.southindia.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.goan.getText().toString().trim())) {
                b.goan.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.malwani.getText().toString().trim())) {
                b.malwani.setChecked(true);

            } else if (cuisine[i].equalsIgnoreCase(b.gujarati.getText().toString().trim())) {
                b.gujarati.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.punjabi.getText().toString().trim())) {
                b.punjabi.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.rajasthani.getText().toString().trim())) {
                b.rajasthani.setChecked(true);

            } else if (cuisine[i].equalsIgnoreCase(b.bengali.getText().toString().trim())) {
                b.bengali.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.mughlai.getText().toString().trim())) {
                b.mughlai.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.mangal.getText().toString().trim())) {
                b.mangal.setChecked(true);

            } else if (cuisine[i].equalsIgnoreCase(b.korean.getText().toString().trim())) {
                b.korean.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.awadhi.getText().toString().trim())) {
                b.awadhi.setChecked(true);

            } else if (cuisine[i].equalsIgnoreCase(b.continental.getText().toString().trim())) {
                b.continental.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.chinese.getText().toString().trim())) {
                b.chinese.setChecked(true);
            } else if (cuisine[i].equalsIgnoreCase(b.jainFood.getText().toString().trim())) {
                b.jainFood.setChecked(true);
            }
        }
    }

    private void submit() {
        StringBuilder sbCuisineType = new StringBuilder("");

        if (b.rbNorthIndian.isChecked()) {
            model.CuisineType = b.rbNorthIndian.getText().toString().trim();
        }

        if (b.southindia.isChecked()) {
            model.CuisineType = b.southindia.getText().toString().trim();
        }

        if (b.maharashtrian.isChecked()) {
            model.CuisineType = b.maharashtrian.getText().toString().trim();
        }

        if (b.goan.isChecked()) {
            model.CuisineType = b.goan.getText().toString().trim();
        }

        if (b.malwani.isChecked()) {
            model.CuisineType = b.malwani.getText().toString().trim();
        }

        if (b.gujarati.isChecked()) {
            model.CuisineType = b.gujarati.getText().toString().trim();
        }

        if (b.rajasthani.isChecked()) {
            model.CuisineType = b.rajasthani.getText().toString().trim();
        }

        if (b.bengali.isChecked()) {
            model.CuisineType = b.bengali.getText().toString().trim();
        }

        if (b.mughlai.isChecked()) {
            model.CuisineType = b.mughlai.getText().toString().trim();
        }

        if (b.mangal.isChecked()) {
            model.CuisineType = b.mangal.getText().toString().trim();
        }

        if (b.korean.isChecked()) {
            model.CuisineType = b.korean.getText().toString().trim();
        }

        if (b.awadhi.isChecked()) {
            model.CuisineType = b.awadhi.getText().toString().trim();
        }

        if (b.continental.isChecked()) {
            model.CuisineType = b.continental.getText().toString().trim();
        }

        if (b.chinese.isChecked()) {
            model.CuisineType = b.chinese.getText().toString().trim();
        }

        if (b.jainFood.isChecked()) {
            model.CuisineType = b.jainFood.getText().toString().trim();
        }


        if (b.rbNorthIndian.isChecked()) {
            sbCuisineType.append(b.rbNorthIndian.getText().toString().trim()).append(",");
        }

        if (b.southindia.isChecked()) {
            sbCuisineType.append(b.southindia.getText().toString().trim()).append(",");
        }

        if (b.maharashtrian.isChecked()) {
            sbCuisineType.append(b.maharashtrian.getText().toString().trim()).append(",");
        }

        if (b.goan.isChecked()) {
            sbCuisineType.append(b.goan.getText().toString().trim()).append(",");
        }

        if (b.malwani.isChecked()) {
            sbCuisineType.append(b.malwani.getText().toString().trim()).append(",");
        }

        if (b.gujarati.isChecked()) {
            sbCuisineType.append(b.gujarati.getText().toString().trim()).append(",");
        }

        if (b.rajasthani.isChecked()) {
            sbCuisineType.append(b.rajasthani.getText().toString().trim()).append(",");
        }

        if (b.bengali.isChecked()) {
            sbCuisineType.append(b.rbNorthIndian.getText().toString().trim()).append(",");
        }

        if (b.mughlai.isChecked()) {
            sbCuisineType.append(b.mughlai.getText().toString().trim()).append(",");
        }

        if (b.mangal.isChecked()) {
            sbCuisineType.append(b.mangal.getText().toString().trim()).append(",");
        }

        if (b.korean.isChecked()) {
            sbCuisineType.append(b.korean.getText().toString().trim()).append(",");
        }

        if (b.awadhi.isChecked()) {
            sbCuisineType.append(b.awadhi.getText().toString().trim()).append(",");
        }

        if (b.continental.isChecked()) {
            sbCuisineType.append(b.continental.getText().toString().trim()).append(",");
        }

        if (b.chinese.isChecked()) {
            sbCuisineType.append(b.chinese.getText().toString().trim()).append(",");
        }

        if (b.jainFood.isChecked()) {
            sbCuisineType.append(b.jainFood.getText().toString().trim()).append(",");
        }

        if (sbCuisineType.toString().endsWith(",")) {
            model.CuisineType = sbCuisineType.substring(0, sbCuisineType.length() - 1);
        } else {
            model.CuisineType = sbCuisineType.toString();
        }


        viewModel._filterMess.postValue(model);
        dismiss();
    }


}