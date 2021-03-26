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

import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.CollectionModels.ModelFoodItem;
import com.example.foodzen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRestaurantItems extends RecyclerView.Adapter<AdapterRestaurantItems.ShopItemsHolder> {


    Context context;
    ArrayList<ModelAddProducts>modelAddProductsArrayList;

    public AdapterRestaurantItems(Context context, ArrayList<ModelAddProducts> modelAddProductsArrayList) {
        this.context = context;
        this.modelAddProductsArrayList = modelAddProductsArrayList;
    }

    @NonNull
    @Override
    public ShopItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurantshowitems_layout,parent,false);
        return new ShopItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemsHolder holder, int position) {

        ModelAddProducts modelAddProducts=modelAddProductsArrayList.get(position);
        String txtFoodName=modelAddProducts.getpName();
        String txtFoodDesc=modelAddProducts.getpDesc();
        String txtFoodOriPrice=modelAddProducts.getOriPrice();
        String txtFoodDiscPrice=modelAddProducts.getDiscountPrice();
        //String txtFoodImg=modelAddProducts.get();
        //String txtFoodType=modelAddProducts.getFoodType();

        holder.FoodItemName.setText(txtFoodName);
        holder.FoodItemDescription.setText(txtFoodDesc);
        holder.FoodItemOriginalPrice.setText(txtFoodOriPrice);
        holder.FoodItemDiscountedPrice.setText(txtFoodDiscPrice);



    }

    @Override
    public int getItemCount() {
        return modelAddProductsArrayList.size();
    }

    public class ShopItemsHolder extends RecyclerView.ViewHolder {
        ImageView FoodImage,itemTypeImageIcon;
        TextView FoodItemName,FoodItemOriginalPrice,FoodItemDescription,FoodItemDiscountedPrice;
        RelativeLayout addProductLayout;
        public ShopItemsHolder(@NonNull View itemView) {
            super(itemView);
            itemTypeImageIcon=itemView.findViewById(R.id.itemTypeImageIcon);
            FoodImage=itemView.findViewById(R.id.FoodImage);
            FoodItemName=itemView.findViewById(R.id.FoodItemNameNew);
            FoodItemOriginalPrice=itemView.findViewById(R.id.FoodItemOriginalPriceNew);
            FoodItemDescription=itemView.findViewById(R.id.FoodItemDescriptionNew);
            FoodItemDiscountedPrice=itemView.findViewById(R.id.FoodItemDiscountedPriceNew);
            addProductLayout=itemView.findViewById(R.id.addProductLayout);

        }
    }


}
