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

import com.example.mymealdabba.NavigationActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.model.CityModel;
import com.example.mymealdabba.model.LocationModel;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    List<LocationModel> list;
    Context context;

    public LocationAdapter(Context context, List<LocationModel> list) {
        this.list = list;
        this.context = context;
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
                Intent intent = new Intent(context, NavigationActivity.class);
                intent.putExtra("location", model.Location);
                Log.e("Location Name", model.Location);
                context.startActivity(intent);
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
