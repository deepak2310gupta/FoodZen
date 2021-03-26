package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionActivities.RestaurantShowItemsActivity;
import com.example.foodzen.CollectionModels.ModelSeller;
import com.example.foodzen.R;

import java.util.ArrayList;

public class AdapterRestaurants extends RecyclerView.Adapter<AdapterRestaurants.RestaurantsViewHolder>{

    Context context;
    ArrayList<ModelSeller>modelSellerArrayList;

    public AdapterRestaurants(Context context, ArrayList<ModelSeller> modelSellerArrayList) {
        this.context = context;
        this.modelSellerArrayList = modelSellerArrayList;
    }

    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurants_nearulayout,parent,false);
        return new RestaurantsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {

        ModelSeller modelSeller=modelSellerArrayList.get(position);
        String txtRName=modelSeller.getName();
        String txtRaddress=modelSeller.getAddress();
        String txtRcategory=modelSeller.getCategory();
        String txtRdiscountNote=modelSeller.getDiscountnote();
        String userId=modelSeller.getUid();

        holder.RestaurantName.setText(txtRName);
        holder.RestaurantAddress.setText(txtRaddress);
        holder.RestaurantCategory.setText(txtRcategory);
        holder.RestaurantDiscountNote.setText(txtRdiscountNote);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RestaurantShowItemsActivity.class);
                intent.putExtra("ResShopId",userId);
                intent.putExtra("ResShopName",txtRName);
                intent.putExtra("ResShopAddress",txtRaddress);
                intent.putExtra("ResCategory",txtRcategory);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelSellerArrayList.size();
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
