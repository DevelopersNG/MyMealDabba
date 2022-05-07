package com.example.mymealdabba.ui.specaloffer;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymealdabba.databinding.FragmentSpecalofferBinding;

public class SpecalOfferFragment extends Fragment {
    private  FragmentSpecalofferBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSpecalofferBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        final TextView textView = binding.textSpecalOffer;


            gotourl("https://mymealdabba.com/site/offers");

        return root;
    }

    private void gotourl(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
