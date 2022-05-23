package com.example.mymealdabba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideViewerActivity extends PagerAdapter {

        Context ctx;

        public SlideViewerActivity(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.activity_slide_viewer,container,false);


            ImageView logo=view.findViewById(R.id.logo);

            ImageView ind1=view.findViewById(R.id.ind1);
            ImageView ind2=view.findViewById(R.id.ind2);
            ImageView ind3=view.findViewById(R.id.ind3);



            switch (position)
            {
                case 0:
                    logo.setImageResource(R.drawable.img1);
                    ind1.setImageResource(R.drawable.seleted);
                    ind2.setImageResource(R.drawable.unselected);
                    ind3.setImageResource(R.drawable.unselected);

                    break;
                case 1:
                    logo.setImageResource(R.drawable.img2);
                    ind1.setImageResource(R.drawable.unselected);
                    ind2.setImageResource(R.drawable.seleted);
                    ind3.setImageResource(R.drawable.unselected);

                    break;
                case 2:
                    logo.setImageResource(R.drawable.img3);
                    ind1.setImageResource(R.drawable.unselected);
                    ind2.setImageResource(R.drawable.unselected);
                    ind3.setImageResource(R.drawable.seleted);


                    break;






            }


            container.addView(view);
            return view;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

