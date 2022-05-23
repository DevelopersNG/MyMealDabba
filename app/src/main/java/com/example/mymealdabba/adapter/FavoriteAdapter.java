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
import com.example.mymealdabba.BookmarkDetailActivity;
import com.example.mymealdabba.MessDetailsActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.Utils;
import com.example.mymealdabba.model.ImageModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;
import com.google.gson.Gson;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    Context context;
    List<Messdeatilslistmodel> list;

    public FavoriteAdapter(Context context, List<Messdeatilslistmodel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bookmark_items, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Messdeatilslistmodel model= list.get(position);

//        Glide.with(holder.imageListViewMess)
//                .load(model.Images)
//                .into(holder.imageListViewMess);

        holder.lblFavMessName.setText(model.MemberName);
        holder.lblFavMessAddress.setText(model.BussinessAddress);
        holder.lblFavMessMonthlyRate.setText(model.MonthlyRate);
        holder.lblFavTotalViews.setText(model.Views);
        holder.lblFavMessCategory.setText(model.Category);
        holder.lblFavMessService.setText(model.Service);
        holder.lblFavMessExperience.setText(model.ExpYears);



        for (ImageModel image : model.Images) {
            Log.e("image", Utils.IMAGEURL + image.ImagePath);
            if (image.IsDefault.equalsIgnoreCase("1")) {
                Glide.with(context)
                        .load(Utils.IMAGEURL + image.ImagePath)
                        .into(holder.imageFavListViewMess);

                break;
            } else {
                Glide.with(context)
                        .load(Utils.IMAGEURL + image.ImagePath)
                        .into(holder.imageFavListViewMess);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookmarkDetailActivity.class);
                intent.putExtra("data", new Gson().toJson(model));
                context.startActivity(intent);
            }
        });
        holder.tbBookmarkFav.setChecked(model.BookMarksStatus.equals("1"));

        holder.tbBookmarkFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tbBookmarkFav.isChecked()) {
                    Utils.favourite(context, model.MemberID);
                    model.BookMarksStatus = "1";
                } else {
                    Utils.favourite(context, model.MemberID);
                    model.BookMarksStatus = "0";
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFavListViewMess;
        AppCompatToggleButton tbBookmarkFav;
        TextView lblFavMessName,lblFavPromoted,lblFavMessAddress,lblFavMessMonthlyRate,lblFavTotalViews,lblFavMessCategory,lblFavMessService,lblFavMessExperience;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageFavListViewMess=itemView.findViewById(R.id.imageFavListViewMess);
            lblFavMessName=itemView.findViewById(R.id.lblFavMessName);
            lblFavPromoted=itemView.findViewById(R.id.lblFavPromoted);
            lblFavMessAddress=itemView.findViewById(R.id.lblFavMessAddress);
            lblFavMessMonthlyRate=itemView.findViewById(R.id.lblFavMessMonthlyRate);
            lblFavTotalViews=itemView.findViewById(R.id.lblFavTotalViews);
            lblFavMessCategory=itemView.findViewById(R.id.lblFavMessCategory);
            lblFavMessService=itemView.findViewById(R.id.lblFavMessService);
            lblFavMessExperience=itemView.findViewById(R.id.lblFavMessExperience);
            tbBookmarkFav = itemView.findViewById(R.id.tbBookmarkFav);
        }
    }
}
