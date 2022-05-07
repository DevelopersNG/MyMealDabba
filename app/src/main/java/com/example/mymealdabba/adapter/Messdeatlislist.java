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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_listview_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int position) {
        Messdeatilslistmodel currentItem= list.get(position);

        holder.imageListViewMess.setImageResource(list.get(position).getImg());
        holder.lblMessName.setText(list.get(position).getMessName());
        holder.lblMessAddress.setText(list.get(position).getMessAddress());
        holder.lblMessMonthlyRate.setText(list.get(position).getMessMonthlyRate());
        holder.lblTotalViews.setText(list.get(position).getTotalViews());
        holder.lblMessCategory.setText(list.get(position).getMessCategory());
        holder.lblMessService.setText(list.get(position).getMessService());
        holder.lblMessExperience.setText(list.get(position).getMessExperience());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessDetailsActivity.class);
                intent.putExtra("type",list.get(position).getType);
                context.startActivity(intent);
            }
        });
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
