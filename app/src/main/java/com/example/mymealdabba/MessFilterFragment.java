package com.example.mymealdabba;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymealdabba.databinding.FragmentMessFilterBinding;
import com.example.mymealdabba.model.CuisineModel;
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


        return b.getRoot();
    }

    private void listener() {
        viewModel._filterMess.postValue(model);
        viewModel._filterMess.observe(getViewLifecycleOwner(), model -> {
            if (model != null) {
                setData(model);
            }
        });
        b.btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

//        b.mtbFilterMess.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(getContext(),NavigationActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });

        b.btnResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.chipNonVeg.setChecked(false);
                b.chipVeg.setChecked(false);
                b.chkPremises.setChecked(false);
                b.chkHomeDelivery.setChecked(false);
                b.chkMahila.setChecked(false);
                b.chkHotel.setChecked(false);
                b.chkChef.setChecked(false);
                b.chkFoodMess.setChecked(false);
                b.rbCostLowToHigh.setChecked(false);
                b.rbCostHighToLow.setChecked(false);
                b.rbRecentlyAdded.setChecked(false);

            }
        });
    }

    private void setData(MessFilterModel model) {
        b.rbCostLowToHigh.setChecked(b.rbCostLowToHigh.getText().toString().equalsIgnoreCase(model.SortBy));
        b.rbCostHighToLow.setChecked(b.rbCostHighToLow.getText().toString().equalsIgnoreCase(model.SortBy));
        b.rbRecentlyAdded.setChecked(("Recently Added").equalsIgnoreCase(model.SortBy));

//checked checkbox mess type
        String messType = (model.MessType);
        Log.e("mess type", model.MessType);
        String[] mess = messType.split(",");

        for (int i = 0; i < (mess.length); i++) {
            if (mess[i].equalsIgnoreCase(b.chkFoodMess.getText().toString().trim())) {
                b.chkFoodMess.setChecked(true);
            } else if (mess[i].equalsIgnoreCase(b.chkChef.getText().toString().trim())) {
                b.chkChef.setChecked(true);
            } else if (mess[i].equalsIgnoreCase(("Hotel/Parcel").trim())) {
                b.chkHotel.setChecked(true);
            } else if (mess[i].equalsIgnoreCase(("mahila").trim())) {
                b.chkMahila.setChecked(true);
            }
        }

        //checked service.....
            String service = (model.Service);
            Log.e("service", model.Service);
            String[] serv = service.split(",");

            for (int j = 0; j < (serv.length); j++) {
                if (serv[j].equalsIgnoreCase(b.chkHomeDelivery.getText().toString().trim())) {
                    b.chkHomeDelivery.setChecked(true);
                } else if (serv[j].equalsIgnoreCase(b.chkPremises.getText().toString().trim())) {
                    b.chkPremises.setChecked(true);
                }
            }
// checked category
                String category = (model.Category);
                Log.e("category", model.Category);
                String[] cat = category.split(",");

                for (int k = 0; k < (cat.length); k++) {
                    if (cat[k].equalsIgnoreCase(b.chipVeg.getText().toString().trim())) {
                        b.chipVeg.setChecked(true);
                    } else if (cat[k].equalsIgnoreCase(b.chipNonVeg.getText().toString().trim())) {
                        b.chipNonVeg.setChecked(true);
                    }
                }
            }



    void submit() {
        StringBuilder sbMessType = new StringBuilder("");
        StringBuilder sbService = new StringBuilder("");
        StringBuilder sbCategory = new StringBuilder("");

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
            sbMessType.append(b.chkFoodMess.getText().toString().trim()).append(",");
        }

        if (b.chkChef.isChecked()) {
            sbMessType.append(b.chkChef.getText().toString().trim()).append(",");
        }

        if (b.chkHotel.isChecked()) {
            sbMessType.append("Hotel/Parcel".trim()).append(",");
        }

        if (b.chkMahila.isChecked()) {
            sbMessType.append("mahila".toString().trim()).append(",");
        }

        if (b.chkHomeDelivery.isChecked()) {
            sbService.append(b.chkHomeDelivery.getText().toString().trim()).append(",");
        }

        if (b.chkPremises.isChecked()) {
            sbService.append(b.chkPremises.getText().toString().trim()).append(",");
        }

        if (b.chipVeg.isChecked()) {
            sbCategory.append(b.chipVeg.getText().toString().trim()).append(",");
        }

        if (b.chipNonVeg.isChecked()) {
            sbCategory.append(b.chipNonVeg.getText().toString().trim()).append(",");
        }

        if (sbMessType.toString().endsWith(",")) {
            model.MessType = sbMessType.substring(0, sbMessType.length() - 1);
        } else {
            model.MessType = sbMessType.toString();
        }
        if (sbCategory.toString().endsWith(",")) {
            model.Category = sbCategory.substring(0, sbCategory.length() - 1);
        } else {
            model.Category = sbCategory.toString();
        }
        if (sbService.toString().endsWith(",")) {
            model.Service = sbService.substring(0, sbService.length() - 1);
        } else {
            model.Service = sbService.toString();
        }
        viewModel._filterMess.postValue(model);
        dismiss();
    }

}

