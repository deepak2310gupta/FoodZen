package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelFoodItem;
import com.example.foodzen.R;

import java.util.ArrayList;

public class AdapterRestaurantItems extends RecyclerView.Adapter<AdapterRestaurantItems.ShopItemsHolder> {


    Context context;
    ArrayList<ModelFoodItem>modelFoodItemArrayList;

    public AdapterRestaurantItems(Context context, ArrayList<ModelFoodItem> modelFoodItems) {
        this.context = context;
        this.modelFoodItemArrayList = modelFoodItems;
    }

    @NonNull
    @Override
    public ShopItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurantshowitems_layout,parent,false);
        return new ShopItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemsHolder holder, int position) {

        ModelFoodItem modelFoodItem=modelFoodItemArrayList.get(position);
        String txtFoodName=modelFoodItem.getFoodName();
        String txtFoodDesc=modelFoodItem.getFoodDesc();
        String txtFoodOriPrice=modelFoodItem.getFoodOriginalPrice();
        String txtFoodDiscPrice=modelFoodItem.getFoodDiscountedPrice();
        String txtFoodImg=modelFoodItem.getFoodImage();
        String txtFoodType=modelFoodItem.getFoodType();

    }

    @Override
    public int getItemCount() {
        return modelFoodItemArrayList.size();
    }

    public class ShopItemsHolder extends RecyclerView.ViewHolder {
        ImageView FoodImage,itemTypeImageIcon;
        TextView FoodItemName,FoodItemOriginalPrice,FoodItemDescription,FoodItemDiscountedPrice;
        RelativeLayout addProductLayout;
        public ShopItemsHolder(@NonNull View itemView) {
            super(itemView);
            itemTypeImageIcon=itemView.findViewById(R.id.itemTypeImageIcon);
            FoodImage=itemView.findViewById(R.id.FoodImage);
            FoodItemName=itemView.findViewById(R.id.FoodItemName);
            FoodItemOriginalPrice=itemView.findViewById(R.id.FoodItemOriginalPrice);
            FoodItemDescription=itemView.findViewById(R.id.FoodItemDescription);
            FoodItemDiscountedPrice=itemView.findViewById(R.id.FoodItemDiscountedPrice);
            addProductLayout=itemView.findViewById(R.id.addProductLayout);

        }
    }


}
