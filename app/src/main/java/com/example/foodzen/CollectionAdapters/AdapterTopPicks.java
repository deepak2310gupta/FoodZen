package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.R;

public class AdapterTopPicks extends RecyclerView.Adapter<AdapterTopPicks.PicksViewHolder>{

    Context context;

    @NonNull
    @Override
    public PicksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.toppicks_layout,parent,false);
        return new PicksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicksViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class PicksViewHolder extends RecyclerView.ViewHolder {
        public PicksViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
