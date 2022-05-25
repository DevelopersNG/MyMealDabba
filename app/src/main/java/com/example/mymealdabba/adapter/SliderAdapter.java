package com.example.mymealdabba.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mymealdabba.R;
import com.example.mymealdabba.Utils;
import com.example.mymealdabba.model.ImageModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder> {

    // list for storing urls of images.
    private final List<ImageModel> mSliderItems;

    // Constructor
    public SliderAdapter(List<ImageModel> mSliderItems) {
        this.mSliderItems = mSliderItems;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new SliderAdapterViewHolder(inflate);
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {

        ImageModel model = mSliderItems.get(position);

        // Glide is use to load image
        // from url in your imageview.

        Glide.with(viewHolder.itemView)
                .load(Utils.IMAGEURL + model.getImagePath())
                .fitCenter()
                .into(viewHolder.imageViewBackground);


//                    for (ImageModel image : model.Images) {
//            Log.e("image", Utils.IMAGEURL + image.ImagePath);
//            if (image.IsDefault.equalsIgnoreCase("1")) {
//                Glide.with(context)
//                        .load(Utils.IMAGEURL + image.ImagePath)
//                        .into(b.ivMess);
//
//                break;
//            } else {
//                Glide.with(context)
//                        .load(Utils.IMAGEURL + image.ImagePath)
//                        .into(b.ivMess);
//            }
//
//        }


    }

    // this method will return
    // the count of our list.
    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.myimage);
        }
    }
}


