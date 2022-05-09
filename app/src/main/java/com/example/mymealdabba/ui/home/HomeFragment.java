package com.example.mymealdabba.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymealdabba.R;
import com.example.mymealdabba.adapter.Messdeatlislist;
import com.example.mymealdabba.databinding.FragmentHomeBinding;
import com.example.mymealdabba.model.Messdeatilslistmodel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    TextView lblFilter, lblCousineFilter;
    RecyclerView home_ver_recy;

    List<Messdeatilslistmodel> messdeatilslistmodels;
    Messdeatlislist messdeatlislist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //Image Verticaly Recyclerview
        home_ver_recy = root.findViewById(R.id.home_ver_recy);


        messdeatilslistmodels = new ArrayList<>();
       // messdeatilslistmodels.add(new Messdeatilslistmodel(R.drawable.listing_default_grey, "Shree Mess", "Nashik", "", "Views:", "Food mess", "Vegetarian", "Home Delivery", "6 years"));

        messdeatlislist = new Messdeatlislist(getActivity(), messdeatilslistmodels);
        home_ver_recy.setAdapter(messdeatlislist);
        home_ver_recy.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        home_ver_recy.setHasFixedSize(true);
        home_ver_recy.setNestedScrollingEnabled(false);


        ///Filter and cousinerFilter click button
        final TextView textView = binding.lblFilter;

        lblFilter = root.findViewById(R.id.lblFilter);
        lblCousineFilter = root.findViewById(R.id.lblCousineFilter);


        lblFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.filtter_page, null);


                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        lblCousineFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.selectcuisine, null);


                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}