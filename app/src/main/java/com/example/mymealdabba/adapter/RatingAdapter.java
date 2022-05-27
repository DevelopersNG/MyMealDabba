package com.example.mymealdabba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymealdabba.BookmarkDetailActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.Utils;
import com.example.mymealdabba.model.ImageModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.example.mymealdabba.model.RatingModel;
import com.google.gson.Gson;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder>{

    Context context;
    List<RatingModel> list;

    public RatingAdapter(Context context, List<RatingModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rating_item, parent, false);
        return new RatingAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RatingModel model = list.get(position);

holder.lblReviewrName.setText(model.ReviewerName);
  holder.lblReviewDate.setText(model.AddedOn);
  holder.rbAvgRating.setRating(Float.parseFloat(model.Rating));




    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       RatingBar rbAvgRating;
        TextView lblReviewrName, lblReviewDate;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            lblReviewrName = itemView.findViewById(R.id.lblReviewrName);
            lblReviewDate = itemView.findViewById(R.id.lblReviewDate);
            rbAvgRating = itemView.findViewById(R.id.rbAvgRating);

        }
    }
}
