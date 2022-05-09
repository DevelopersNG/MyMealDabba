package com.example.mymealdabba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymealdabba.MessDetailsActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.model.Messdeatilslistmodel;

import java.util.List;

import kotlin.jvm.internal.MutablePropertyReference0;

public class Messdeatlislist extends RecyclerView.Adapter<Messdeatlislist.ViewHolder> {
    Context context;
    List<Messdeatilslistmodel> list;

    public Messdeatlislist(Context context, List<Messdeatilslistmodel> list) {
        this.context = context;
        this.list=list;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_listview_item, parent, false);
        return new Messdeatlislist.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int position) {
        Messdeatilslistmodel model= list.get(position);

//        Glide.with(holder.imageListViewMess)
//                .load(model.Images)
//                .into(holder.imageListViewMess);

        holder.lblMessName.setText(model.MemberName);
        holder.lblMessAddress.setText(model.BussinessAddress);
        holder.lblMessMonthlyRate.setText(model.MonthlyRate);
        holder.lblTotalViews.setText(model.Views);
        holder.lblMessCategory.setText(model.Category);
        holder.lblMessService.setText(model.Service);
        holder.lblMessExperience.setText(model.ExpYears);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, MessDetailsActivity.class);
//                intent.putExtra("type",list.get(position).getType);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageListViewMess;

        TextView  lblMessName,lblPromoted,lblMessAddress,lblMessMonthlyRate,lblTotalViews,lblMessCategory,lblMessService,lblMessExperience;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
          imageListViewMess=itemView.findViewById(R.id.imageListViewMess);
            lblMessName=itemView.findViewById(R.id.lblMessName);
            lblPromoted=itemView.findViewById(R.id.lblPromoted);
            lblMessAddress=itemView.findViewById(R.id.lblMessAddress);
            lblMessMonthlyRate=itemView.findViewById(R.id.lblMessMonthlyRate);
            lblTotalViews=itemView.findViewById(R.id.lblTotalViews);
            lblMessCategory=itemView.findViewById(R.id.lblMessCategory);
            lblMessService=itemView.findViewById(R.id.lblMessService);
            lblMessExperience=itemView.findViewById(R.id.lblMessExperience);

        }
    }
}
