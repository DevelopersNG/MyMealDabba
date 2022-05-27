package com.example.mymealdabba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymealdabba.MessDetailsActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.Utils;
import com.example.mymealdabba.model.ImageModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;

import java.util.List;

public class MessListAdapter extends RecyclerView.Adapter<MessListAdapter.ViewHolder> {
    Context context;
    List<Messdeatilslistmodel> list;

    public MessListAdapter(Context context, List<Messdeatilslistmodel> list) {
        this.context = context;
        this.list = list;
    }

    public void filterList(List<Messdeatilslistmodel> filterlList) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_listview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Messdeatilslistmodel item = list.get(position);
        Log.e("model", new Gson().toJson(item));

        for (ImageModel image : item.Images) {
            Log.e("image", Utils.IMAGEURL + image.ImagePath);
            if (image.IsDefault.equalsIgnoreCase("1")) {
                Glide.with(context)
                        .load(Utils.IMAGEURL + image.ImagePath)
                        .into(holder.imageListViewMess);
                
            }
            else
            {
                Glide.with(context)
                        .load(Utils.IMAGEURL + image.ImagePath)
                        .into(holder.imageListViewMess);
            }
        }


        holder.lblMessName.setText(item.MemberName);
        holder.lblMessAddress.setText(item.BussinessAddress);
        //  holder.lblMessMonthlyRate.setText(model.MonthlyRate);
        holder.lblTotalViews.setText(item.Views);
        holder.lblMessCategory.setText(item.Category);
        holder.lblMessService.setText(item.Service);
        holder.lblMessSingleRate.setText(item.TiffinRate);
        holder.lblMessExperience.setText(item.ExpYears);
        holder.lblMessType.setText(item.Type);
//        holder.imageViewFav.setChecked(model.BookMarksStatus.equals("1"));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessDetailsActivity.class);
                intent.putExtra("data", new Gson().toJson(item));
                context.startActivity(intent);
            }
        });


        if (item.Promoted.equals("1")) {
            //
            holder.lblPromoted.setVisibility(View.VISIBLE);
        } else {
            holder.lblPromoted.setVisibility(View.GONE);
        }

//        holder.imageViewFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.imageViewFav.isChecked()) {
//                    Utils.favourite(context, model.MemberID);
//                    model.BookMarksStatus = "1";
//                } else {
//                    Utils.favourite(context, model.MemberID);
//                    model.BookMarksStatus = "0";
//                }
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageListViewMess;
        AppCompatToggleButton imageViewFav;

        TextView lblMessName, lblPromoted, lblMessAddress, lblMessType, lblMessMonthlyRate, lblMessSingleRate, lblTotalViews, lblMessCategory, lblMessService, lblMessExperience;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageViewFav = itemView.findViewById(R.id.tbBookmarkFav);
            imageListViewMess = itemView.findViewById(R.id.imageListViewMess);
            lblMessName = itemView.findViewById(R.id.lblMessName);
            lblPromoted = itemView.findViewById(R.id.lblPromoted);
            lblMessAddress = itemView.findViewById(R.id.lblMessAddress);
            lblMessType = itemView.findViewById(R.id.lblMessType);
            //  lblMessMonthlyRate = itemView.findViewById(R.id.lblMessMonthlyRate);
            lblTotalViews = itemView.findViewById(R.id.lblTotalViews);
            lblMessCategory = itemView.findViewById(R.id.lblMessCategory);
            lblMessService = itemView.findViewById(R.id.lblMessService);
            lblMessExperience = itemView.findViewById(R.id.lblMessExperience);
            lblMessSingleRate = itemView.findViewById(R.id.lblMessSingleRateTitle);
        }
    }
}
