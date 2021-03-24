package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.R;

public class AdapterRestaurants extends RecyclerView.Adapter<AdapterRestaurants.RestaurantsViewHolder>{

    Context context;

    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurants_nearulayout,parent,false);
        return new RestaurantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RestaurantsViewHolder extends RecyclerView.ViewHolder {
        ImageView RestaurantImage;
        TextView RestaurantName,RestaurantAddress,RestaurantCategory,RestaurantDiscountNote;
        public RestaurantsViewHolder(@NonNull View itemView) {
            super(itemView);
            RestaurantImage=itemView.findViewById(R.id.RestaurantImage);
            RestaurantName=itemView.findViewById(R.id.RestaurantName);
            RestaurantAddress=itemView.findViewById(R.id.RestaurantAddress);
            RestaurantCategory=itemView.findViewById(R.id.RestaurantCategory);
            RestaurantDiscountNote=itemView.findViewById(R.id.RestaurantDiscountNote);
        }
    }


}
