package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSellerProducts extends RecyclerView.Adapter<AdapterSellerProducts.SellerproductsHolder> {


    Context context;
    ArrayList<ModelAddProducts> modelAddProductsArrayList;

    public AdapterSellerProducts(Context context, ArrayList<ModelAddProducts> modelAddProductsArrayList) {
        this.context = context;
        this.modelAddProductsArrayList = modelAddProductsArrayList;
    }

    @NonNull
    @Override
    public SellerproductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sellerrowproducts, parent, false);
        return new SellerproductsHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SellerproductsHolder holder, int position) {

        ModelAddProducts modelAddProducts = modelAddProductsArrayList.get(position);
        String txtFoodName = modelAddProducts.getpName();
        String txtFoodDesc = modelAddProducts.getpDesc();
        String txtFoodOriPrice = modelAddProducts.getOriPrice();
        String txtFoodDiscPrice = modelAddProducts.getDiscountPrice();
        String txtFoodType = modelAddProducts.getItemType();
        String productimage=modelAddProducts.getProductimage();
        Picasso.get().load(productimage).into(holder.FoodImageNewOne);
        holder.FoodItemNameNewOne.setText(txtFoodName);
        holder.FoodItemDescriptionNewOne.setText(txtFoodDesc);
        holder.FoodItemOriginalPriceNewOne.setText(txtFoodOriPrice);
        holder.FoodItemDiscountedPriceNewOne.setText(txtFoodDiscPrice);
        holder.FoodItemOriginalPriceNewOne.setPaintFlags(holder.FoodItemOriginalPriceNewOne.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (txtFoodType.equals("Veg")) {
            Picasso.get().load(R.drawable.vegpic).into(holder.itemTypeImageIconNew);
        } else {
            Picasso.get().load(R.drawable.nonvegpic).into(holder.itemTypeImageIconNew);
        }

        holder.DeleteProductLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelAddProductsArrayList.size();
    }

    public class SellerproductsHolder extends RecyclerView.ViewHolder {
        ImageView FoodImageNewOne, itemTypeImageIconNew;
        TextView FoodItemNameNewOne, FoodItemOriginalPriceNewOne, FoodItemDescriptionNewOne, FoodItemDiscountedPriceNewOne;
        RelativeLayout DeleteProductLayout;

        public SellerproductsHolder(@NonNull View itemView) {
            super(itemView);
            itemTypeImageIconNew = itemView.findViewById(R.id.itemTypeImageIconNew);
            FoodImageNewOne = itemView.findViewById(R.id.FoodImageNewOne);
            FoodItemNameNewOne = itemView.findViewById(R.id.FoodItemNameNewOne);
            FoodItemOriginalPriceNewOne = itemView.findViewById(R.id.FoodItemOriginalPriceNewOne);
            FoodItemDescriptionNewOne = itemView.findViewById(R.id.FoodItemDescriptionNewOne);
            FoodItemDiscountedPriceNewOne = itemView.findViewById(R.id.FoodItemDiscountedPriceNewOne);
            DeleteProductLayout = itemView.findViewById(R.id.DeleteProductLayout);
        }
    }
}
