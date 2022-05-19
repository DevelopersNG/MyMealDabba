package com.example.mymealdabba.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymealdabba.HomeViewModel;
import com.example.mymealdabba.NavigationActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.model.MessNameListModel;
import com.example.mymealdabba.model.Messdeatilslistmodel;

import java.util.List;

public class MessNameAdapter extends RecyclerView.Adapter<MessNameAdapter.MyViewHolder>{
    List<MessNameListModel> list;
    Context context;
    HomeViewModel viewModel;

    public MessNameAdapter(Context context, List<MessNameListModel> list, HomeViewModel viewModel) {
        this.list = list;
        this.context = context;
        this.viewModel = viewModel;
    }
    // method for filtering our recyclerview items.
    public void filterList(List<MessNameListModel> filterlList) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessNameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location_items, parent, false);
        return new MessNameAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessNameAdapter.MyViewHolder holder, int i) {
        final MessNameListModel model = list.get(i);
        holder.tvLocationName.setText(model.Title);

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocationName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
        }
    }
}
