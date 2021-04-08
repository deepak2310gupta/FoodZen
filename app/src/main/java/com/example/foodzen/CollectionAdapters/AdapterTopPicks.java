package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelTopPicks;
import com.example.foodzen.R;

import java.util.ArrayList;

public class AdapterTopPicks extends RecyclerView.Adapter<AdapterTopPicks.PicksViewHolder>{

    Context context;
    ArrayList<ModelTopPicks>modelTopPicksArrayList;

    public AdapterTopPicks(Context context, ArrayList<ModelTopPicks> modelTopPicksArrayList) {
        this.context = context;
        this.modelTopPicksArrayList = modelTopPicksArrayList;
    }

    @NonNull
    @Override
    public PicksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.toppicks_layout,parent,false);
        return new PicksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicksViewHolder holder, int position) {

        ModelTopPicks modelTopPicks=modelTopPicksArrayList.get(position);
        String one=modelTopPicks.getShopName();
        String two=modelTopPicks.getShopDiscountNoteOff();
        String three=modelTopPicks.getTopPickid();
        String four=modelTopPicks.getShopId();

        holder.topPicksDiscoutnView.setText(two);
        holder.topPicksRestaurantName.setText(one);
    }

    @Override
    public int getItemCount() {
        return modelTopPicksArrayList.size();
    }


    public class PicksViewHolder extends RecyclerView.ViewHolder {

        TextView topPicksRestaurantName,topPicksDiscoutnView;
        public PicksViewHolder(@NonNull View itemView) {
            super(itemView);

            topPicksDiscoutnView=itemView.findViewById(R.id.topPicksDiscoutnView);
            topPicksRestaurantName=itemView.findViewById(R.id.topPicksRestaurantName);

        }
    }

}
