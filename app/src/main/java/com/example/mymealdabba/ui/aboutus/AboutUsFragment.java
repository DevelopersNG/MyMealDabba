package com.example.mymealdabba.ui.aboutus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymealdabba.BuildConfig;
import com.example.mymealdabba.R;
import com.example.mymealdabba.databinding.FragmentAboutusBinding;

public class AboutUsFragment extends Fragment {

    private FragmentAboutusBinding binding;
    TextView info;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAboutusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

         info=root.findViewById(R.id.info);

         info.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 try {
                     Intent shareIntent = new Intent(Intent.ACTION_SEND);
                     shareIntent.setType("text/plain");
                     shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                     String shareMessage= "\nLet me recommend you this application\n\n";
                     shareMessage = shareMessage + "https://www.info@mymealdabba.com" + BuildConfig.APPLICATION_ID +"\n\n";
                     shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                     startActivity(Intent.createChooser(shareIntent, "choose one"));
                 } catch(Exception e) {
                     //e.toString();
                 }
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
