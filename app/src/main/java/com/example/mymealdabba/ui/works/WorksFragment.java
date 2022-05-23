package com.example.mymealdabba.ui.works;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.example.mymealdabba.R;
import com.example.mymealdabba.databinding.FragmentWorksBinding;

public class WorksFragment extends Fragment {

    private FragmentWorksBinding binding;
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;

    TextView[] dots;
//    ViewPagerAdapter viewPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentWorksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        backbtn = root.findViewById(R.id.backbtn);
//        nextbtn = root.findViewById(R.id.nextbtn);
//        skipbtn = root.findViewById(R.id.skipButton);

//        backbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (getitem(0) > 0){
//
//                    mSLideViewPager.setCurrentItem(getitem(-1),true);
//
//                }
//
//            }
//        });
//
//        nextbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (getitem(0) < 3)
//                    mSLideViewPager.setCurrentItem(getitem(1),true);
//                else {
//
//                    Intent i = new Intent(WorksFragment.this, com.example.mymealdabba.mainscreen.class);
//                    startActivity(i);
//
//
//                }
//
//            }
//        });

//        skipbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent i = new Intent(MainActivity.this, com.example.mymealdabba.mainscreen.class);
//                startActivity(i);
//
//
//            }
//        });

//        mSLideViewPager = (ViewPager) root.findViewById(R.id.slideViewPager);
//        mDotLayout = (LinearLayout) root.findViewById(R.id.indicator_layout);

//        viewPagerAdapter = new ViewPagerAdapter(getActivity());
//
//        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
        return root;

    }

    public void setUpindicator(int position) {

        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

//           dots[i].setTextColor(getResources().getColor(R.color.orange_color, getActivity().getTheme()));
            mDotLayout.addView(dots[i]);

        }

//        dots[position].setTextColor(getResources().getColor(R.color.orange_color, getActivity().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0) {

//                backbtn.setVisibility(View.VISIBLE);

            } else {

//                backbtn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i) {

        return mSLideViewPager.getCurrentItem() + i;
    }

//       textView = binding.textWor final TextView ks;




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}