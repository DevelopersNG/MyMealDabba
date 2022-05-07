package com.example.mymealdabba.ui.connectwith;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mymealdabba.BuildConfig;
import com.example.mymealdabba.R;
import com.example.mymealdabba.databinding.FragmentConnectwithBinding;

public class ConnectWithFragment extends Fragment {
    private FragmentConnectwithBinding binding;
    TextView lblMMDLink1,lblMMDLink;
    ImageView imageViewFacebook,imageViewTwitter,imageViewInstagram,imageViewWhatsApp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentConnectwithBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.lblFilter;
        lblMMDLink=root.findViewById(R.id.lblMMDLink);
        lblMMDLink1=root.findViewById(R.id.lblMMDLink1);
        imageViewFacebook=root.findViewById(R.id.imageViewFacebook);
        imageViewTwitter=root.findViewById(R.id.imageViewTwitter);
        imageViewInstagram=root.findViewById(R.id.imageViewInstagram);
        imageViewWhatsApp=root.findViewById(R.id.imageViewWhatsApp);

        imageViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gotoUrl("https://www.facebook.com/mymealdabba/");
            }
        });
        imageViewTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://twitter.com/mymealdabba");
            }
        });

        imageViewInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/mymealdabba/");
            }
        });

        imageViewWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://www.whatsapp.com" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        lblMMDLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("http://mymealdabba.com/");
            }
        });

        lblMMDLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://mymealdabba.com/" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        return root;
    }

    private void gotoUrl(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
