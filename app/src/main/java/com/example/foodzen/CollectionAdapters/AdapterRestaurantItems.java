package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.CollectionModels.ModelFoodItem;
import com.example.foodzen.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterRestaurantItems extends RecyclerView.Adapter<AdapterRestaurantItems.ShopItemsHolder> {


    Context context;
    ArrayList<ModelAddProducts>modelAddProductsArrayList;
    int itemIDCart=1;

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
        String txtFoodType=modelAddProducts.getItemType();

        holder.FoodItemName.setText(txtFoodName);
        holder.FoodItemDescription.setText(txtFoodDesc);
        holder.FoodItemOriginalPrice.setText(txtFoodOriPrice);
        holder.FoodItemDiscountedPrice.setText(txtFoodDiscPrice);
        holder.FoodItemOriginalPrice.setPaintFlags( holder.FoodItemOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if(txtFoodType.equals("Veg")){
            Picasso.get().load(R.drawable.vegpic).into(holder.itemTypeImageIcon);
        }else{
            Picasso.get().load(R.drawable.nonvegpic).into(holder.itemTypeImageIcon);
        }

        holder.addProductLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCartBottomDialog(modelAddProducts);
            }
        });

    }

    private void showCartBottomDialog(ModelAddProducts modelAddProducts) {

        View view=LayoutInflater.from(context).inflate(R.layout.bottomdialogproductdetails,null);
        ImageButton decrementButton=view.findViewById(R.id.decrementButton);
        ImageButton incrementButton=view.findViewById(R.id.incrementButton);
        TextView tvQuantity=view.findViewById(R.id.tvQuantity);
        Button addItemToCartButton=view.findViewById(R.id.addItemToCartButton);
        ProgressBar addCartProgressIndicator=view.findViewById(R.id.addCartProgressIndicator);
        TextView cartBottomFoodName=view.findViewById(R.id.cartBottomFoodName);
        TextView NetTotalSum=view.findViewById(R.id.NetTotalSum);
        TextView dialogDiscountedprice=view.findViewById(R.id.dialogDiscountedprice);
        TextView dialogOriginalPrice=view.findViewById(R.id.dialogOriginalPrice);
        TextView cartBottomFoodDesc=view.findViewById(R.id.cartBottomFoodDesc);
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        cartBottomFoodName.setText(modelAddProducts.getpName());
        cartBottomFoodDesc.setText(modelAddProducts.getpDesc());
        dialogOriginalPrice.setText(Integer.toString(0));
        dialogDiscountedprice.setText(Integer.toString(0));
        dialogOriginalPrice.setPaintFlags(dialogOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvQuantity.getText().toString().trim().equals("1")){

                    Toast.makeText(context, "Error !!(Minimum Quantity Present)", Toast.LENGTH_SHORT).show();
                    dialogOriginalPrice.setText(modelAddProducts.getOriPrice());
                    NetTotalSum.setText(modelAddProducts.getDiscountPrice());
                    return;
                }
                else{
                    int CurrentQuantity=Integer.parseInt(tvQuantity.getText().toString());
                    int CurrentTotalSum=Integer.parseInt(NetTotalSum.getText().toString());
                    int TotalOriginalSum=Integer.parseInt(dialogOriginalPrice.getText().toString())-Integer.parseInt(modelAddProducts.getOriPrice());
                    CurrentQuantity--;
                    CurrentTotalSum=CurrentTotalSum-Integer.parseInt(modelAddProducts.getDiscountPrice());
                    NetTotalSum.setText(Integer.toString(CurrentTotalSum));
                    tvQuantity.setText(Integer.toString(CurrentQuantity));
                    dialogDiscountedprice.setText(Integer.toString(CurrentTotalSum));
                    dialogOriginalPrice.setText(Integer.toString(TotalOriginalSum));
                    return;
                }
            }
        });
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int CurrentQuantity=Integer.parseInt(tvQuantity.getText().toString());
                    int CurrentTotalSum=Integer.parseInt(NetTotalSum.getText().toString());
                    CurrentQuantity++;
                    CurrentTotalSum=CurrentTotalSum+Integer.parseInt(modelAddProducts.getDiscountPrice());
                    int TotalOriginalSum=Integer.parseInt(dialogOriginalPrice.getText().toString())+Integer.parseInt(modelAddProducts.getOriPrice());
                    tvQuantity.setText(Integer.toString(CurrentQuantity));
                    NetTotalSum.setText(Integer.toString(CurrentTotalSum));
                    dialogDiscountedprice.setText(Integer.toString(CurrentTotalSum));
                    dialogOriginalPrice.setText(Integer.toString(TotalOriginalSum));
                    return;
            }
        });
        addItemToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCartProgressIndicator.setIndeterminate(true);
                addCartProgressIndicator.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Adding To Cart....", Toast.LENGTH_SHORT).show();
                 String cartFoodName=cartBottomFoodName.getText().toString().trim();
                 String cartFoodQuantity=tvQuantity.getText().toString().trim();
                 String cartFoodPrice=dialogDiscountedprice.getText().toString().trim();
                 String cartUserId=modelAddProducts.getProductUserId();
                 String cartProductId=modelAddProducts.getpId();
                 String FoodOriginalTotalPrice=dialogOriginalPrice.getText().toString().trim();
                AddToCartDatabase(cartFoodName,cartFoodQuantity,cartFoodPrice,cartUserId,cartProductId,FoodOriginalTotalPrice,addCartProgressIndicator);
               // bottomSheetDialog.dismiss();
            }
        });
    }

    private void AddToCartDatabase(String cartFoodName, String cartFoodQuantity, String cartFoodPrice, String cartUserId, String cartProductId, String foodOriginalTotalPrice, ProgressBar addCartProgressIndicator) {
        EasyDB easyDB = EasyDB.init(context, "FoodZenDbName"+cartUserId)
                .setTableName("Cart_Items_Table"+cartUserId)
                .addColumn(new Column("FoodPid",new String[]{"text", "not null"}))
                .addColumn(new Column("FoodPName",new String[]{"text", "not null"}))
                .addColumn(new Column("FoodTotalOriginalPrice",new String[]{"text", "not null"}))
                .addColumn(new Column("FoodQuantity", new String[]{"text", "not null"}))
                .addColumn(new Column("FoodTotalDiscountedPrice", new String[]{"text", "not null"}))
                .addColumn(new Column("FoodTotalPrice", new String[]{"text", "not null"}))
                .doneTableColumn();
        boolean add=easyDB
                .addData("FoodPid",cartProductId).addData("FoodPName",cartFoodName).addData("FoodTotalOriginalPrice",foodOriginalTotalPrice)
                .addData("FoodQuantity",cartFoodQuantity).addData("FoodTotalDiscountedPrice",cartFoodPrice)
                .addData("FoodTotalPrice",cartFoodPrice).doneDataAdding();

        if(add && cartFoodPrice.equals("0")){
            addCartProgressIndicator.setVisibility(View.GONE);
            Toast.makeText(context, "Error !! Item Not Added To Cart", Toast.LENGTH_SHORT).show();
            return;
        }
        if(add){
            addCartProgressIndicator.setVisibility(View.GONE);
            Toast.makeText(context, "Item Added To Cart Successfully", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            addCartProgressIndicator.setVisibility(View.GONE);
            Toast.makeText(context, " Error !! Item Not Added To Cart....", Toast.LENGTH_SHORT).show();
            return;
        }

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
