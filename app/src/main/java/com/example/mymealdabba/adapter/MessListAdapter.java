package com.example.mymealdabba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymealdabba.MessDetailsActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.Utils;
import com.example.mymealdabba.model.ImageModel;
import com.example.mymealdabba.model.LocationModel;
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
        return new MessListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Messdeatilslistmodel model = list.get(position);


        for (ImageModel image : model.Images) {
            Log.e("image",Utils.IMAGEURL+ image.ImagePath);
            if (image.IsDefault.equalsIgnoreCase("1")) {
                Glide.with(context)
                        .load(Utils.IMAGEURL+ image.ImagePath)
                        .into(holder.imageListViewMess);

                break;
            }
            else
            {
                Glide.with(context)
                        .load(Utils.IMAGEURL+ image.ImagePath)
                        .into(holder.imageListViewMess);
            }
        }

        holder.lblMessName.setText(model.MemberName);
        holder.lblMessAddress.setText(model.BussinessAddress);
        holder.lblMessMonthlyRate.setText(model.MonthlyRate);
        holder.lblTotalViews.setText(model.Views);
        holder.lblMessCategory.setText(model.Category);
        holder.lblMessService.setText(model.Service);
        holder.lblMessExperience.setText(model.ExpYears);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessDetailsActivity.class);
                intent.putExtra("data", new Gson().toJson(model));
                context.startActivity(intent);
            }
        });



        if (model.BookMarksStatus.equals("1")) {
//Set the string to true
           holder.imageViewFav.setChecked(true);
        } else
        {
            holder.imageViewFav.setSelected(false);
        }


        if(model.Promoted.equals("1"))
        {
           //
            holder.lblPromoted.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.lblPromoted.setVisibility(View.GONE);
        }




        holder.imageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.imageViewFav.isSelected()) {
                    Utils.favourite(context, model.MemberID);
                } else {
//                    list.remove(model.MemberID);
//                    notifyItemRemoved(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageListViewMess;
        AppCompatToggleButton imageViewFav;

        TextView lblMessName, lblPromoted, lblMessAddress, lblMessMonthlyRate, lblTotalViews, lblMessCategory, lblMessService, lblMessExperience;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageViewFav = itemView.findViewById(R.id.imageViewFav);
            imageListViewMess = itemView.findViewById(R.id.imageListViewMess);
            lblMessName = itemView.findViewById(R.id.lblMessName);
            lblPromoted = itemView.findViewById(R.id.lblPromoted);
            lblMessAddress = itemView.findViewById(R.id.lblMessAddress);
            lblMessMonthlyRate = itemView.findViewById(R.id.lblMessMonthlyRate);
            lblTotalViews = itemView.findViewById(R.id.lblTotalViews);
            lblMessCategory = itemView.findViewById(R.id.lblMessCategory);
            lblMessService = itemView.findViewById(R.id.lblMessService);
            lblMessExperience = itemView.findViewById(R.id.lblMessExperience);
        }
    }
}
