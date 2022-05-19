package com.example.mymealdabba.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymealdabba.HomeViewModel;
import com.example.mymealdabba.R;
import com.example.mymealdabba.model.LocationModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    List<LocationModel> list;
    Context context;
    HomeViewModel viewModel;
    DialogFragment fragment;

    public LocationAdapter(Context context, List<LocationModel> list, HomeViewModel viewModel, DialogFragment fragment) {
        this.list = list;
        this.context = context;
        this.viewModel = viewModel;
        this.fragment = fragment;
    }

    // method for filtering our recyclerview items.
    public void filterList(List<LocationModel> filterlList) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location_items, parent, false);
        return new LocationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.MyViewHolder holder, int i) {
        final LocationModel model = list.get(i);
        holder.tvLocationName.setText(model.Location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel._selectedLocation.postValue(model);
                fragment.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocationName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tvLocationName);
        }
    }
}
