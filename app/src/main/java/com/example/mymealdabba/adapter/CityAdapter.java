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

import com.example.mymealdabba.MessListFragment;
import com.example.mymealdabba.NavigationActivity;
import com.example.mymealdabba.R;
import com.example.mymealdabba.SessionManager;
import com.example.mymealdabba.model.CityModel;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    List<CityModel> list;
    Context context;

    public CityAdapter(Context context, List<CityModel> list) {
        this.list = list;
        this.context = context;
    }

    public void filterList(List<CityModel> filterlList) {
        list = filterlList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_allcity_iteam, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final CityModel model = list.get(i);
        holder.tvCityName.setText(model.City);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new  SessionManager(context).setCityId(model.ID);
                Intent intent = new Intent(context, NavigationActivity.class);
                intent.putExtra("id", model.ID);
                Log.e("City id", model.ID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCityName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
        }
    }
}
