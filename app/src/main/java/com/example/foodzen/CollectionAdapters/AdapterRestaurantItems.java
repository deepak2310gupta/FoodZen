package com.example.foodzen.CollectionAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionActivities.DashboardSellerActivity;
import com.example.foodzen.CollectionActivities.DashboardUserActivity;
import com.example.foodzen.CollectionActivities.SignInActivity;
import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.CollectionModels.ModelCartItems;
import com.example.foodzen.CollectionModels.ModelFoodItem;
import com.example.foodzen.FilterFoodItems;
import com.example.foodzen.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterRestaurantItems extends RecyclerView.Adapter<AdapterRestaurantItems.ShopItemsHolder>implements Filterable {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    Context context;
    public ArrayList<ModelAddProducts> modelAddProductsArrayList,filterList;
    private FilterFoodItems filterFoodItems;

    public boolean isShimmerItems = true;
    public int isShimmerItemNum = 5;

    public AdapterRestaurantItems(Context context, ArrayList<ModelAddProducts> modelAddProductsArrayList) {
        this.context = context;
        this.modelAddProductsArrayList = modelAddProductsArrayList;
        this.filterList=modelAddProductsArrayList;
    }

    @NonNull
    @Override
    public ShopItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurantshowitems_layout, parent, false);
        return new ShopItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemsHolder holder, int position) {

        if (isShimmerItems) {
            holder.itemsShimmerLayout.startShimmer();
        } else {
            holder.itemsShimmerLayout.stopShimmer();
            holder.itemsShimmerLayout.setShimmer(null);
            ModelAddProducts modelAddProducts = modelAddProductsArrayList.get(position);
            String txtFoodName = modelAddProducts.getpName();
            String txtFoodDesc = modelAddProducts.getpDesc();
            String txtFoodOriPrice = modelAddProducts.getOriPrice();
            String txtFoodDiscPrice = modelAddProducts.getDiscountPrice();
            String txtFoodType = modelAddProducts.getItemType();
            String foodImgs=modelAddProducts.getProductimage();

            holder.FoodItemName.setBackground(null);
            holder.FoodItemDescription.setBackground(null);
            holder.FoodItemOriginalPrice.setBackground(null);
            holder.FoodItemDiscountedPrice.setBackground(null);
            holder.itemTypeImageIcon.setBackground(null);
            Picasso.get().load(foodImgs).into(holder.FoodImage);
            holder.addProductLayout.setBackgroundResource(R.drawable.addlayoutbackground);
            holder.FoodItemName.setText(txtFoodName);
            holder.FoodItemDescription.setText(txtFoodDesc);
            holder.FoodItemOriginalPrice.setText(txtFoodOriPrice);
            holder.FoodItemDiscountedPrice.setText(txtFoodDiscPrice);
            holder.FoodItemOriginalPrice.setPaintFlags(holder.FoodItemOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (txtFoodType.equals("Veg")) {
                Picasso.get().load(R.drawable.vegpic).into(holder.itemTypeImageIcon);
            } else {
                Picasso.get().load(R.drawable.nonvegpic).into(holder.itemTypeImageIcon);
            }

            holder.addProductLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showCartBottomDialog(modelAddProducts);

                }

            });

        }

    }
    private void showCartBottomDialog(ModelAddProducts modelAddProducts) {

        View view = LayoutInflater.from(context).inflate(R.layout.bottomdialogproductdetails, null);
        ImageButton decrementButton = view.findViewById(R.id.decrementButton);
        ImageButton incrementButton = view.findViewById(R.id.incrementButton);
        ImageButton FavouriteFoodButton = view.findViewById(R.id.FavouriteFoodButton);
        TextView tvQuantity = view.findViewById(R.id.tvQuantity);
        CircularImageView bottomproductimage=view.findViewById(R.id.bottomproductimage);
        Button addItemToCartButton = view.findViewById(R.id.addItemToCartButton);
        ProgressBar addCartProgressIndicator = view.findViewById(R.id.addCartProgressIndicator);
        TextView cartBottomFoodName = view.findViewById(R.id.cartBottomFoodName);
        TextView NetTotalSum = view.findViewById(R.id.NetTotalSum);
        TextView dialogDiscountedprice = view.findViewById(R.id.dialogDiscountedprice);
        TextView dialogOriginalPrice = view.findViewById(R.id.dialogOriginalPrice);
        TextView cartBottomFoodDesc = view.findViewById(R.id.cartBottomFoodDesc);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        Picasso.get().load(modelAddProducts.getProductimage()).into(bottomproductimage);
        cartBottomFoodName.setText(modelAddProducts.getpName());
        cartBottomFoodDesc.setText(modelAddProducts.getpDesc());
        dialogOriginalPrice.setText(Integer.toString(0));
        dialogDiscountedprice.setText(Integer.toString(0));
        dialogOriginalPrice.setPaintFlags(dialogOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvQuantity.getText().toString().trim().equals("1")) {

                    Toast.makeText(context, "Error !!(Minimum Quantity Present)", Toast.LENGTH_SHORT).show();
                    dialogOriginalPrice.setText(modelAddProducts.getOriPrice());
                    NetTotalSum.setText(modelAddProducts.getDiscountPrice());
                    return;
                } else {
                    int CurrentQuantity = Integer.parseInt(tvQuantity.getText().toString());
                    int CurrentTotalSum = Integer.parseInt(NetTotalSum.getText().toString());
                    int TotalOriginalSum = Integer.parseInt(dialogOriginalPrice.getText().toString()) - Integer.parseInt(modelAddProducts.getOriPrice());
                    CurrentQuantity--;
                    CurrentTotalSum = CurrentTotalSum - Integer.parseInt(modelAddProducts.getDiscountPrice());
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
                int CurrentQuantity = Integer.parseInt(tvQuantity.getText().toString());
                int CurrentTotalSum = Integer.parseInt(NetTotalSum.getText().toString());
                CurrentQuantity++;
                CurrentTotalSum = CurrentTotalSum + Integer.parseInt(modelAddProducts.getDiscountPrice());
                int TotalOriginalSum = Integer.parseInt(dialogOriginalPrice.getText().toString()) + Integer.parseInt(modelAddProducts.getOriPrice());
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
                String cartFoodName = cartBottomFoodName.getText().toString().trim();
                String cartFoodQuantity = tvQuantity.getText().toString().trim();
                String cartFoodPrice = dialogDiscountedprice.getText().toString().trim();
                String cartUserId = modelAddProducts.getProductUserId();
                String cartProductId = modelAddProducts.getpId();
                String FoodOriginalTotalPrice = dialogOriginalPrice.getText().toString().trim();
                AddToCartDatabase(cartFoodName, cartFoodQuantity, cartFoodPrice, cartUserId, cartProductId, FoodOriginalTotalPrice, addCartProgressIndicator, bottomSheetDialog);

            }
        });

        FavouriteFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddtoUserFavouriteList(modelAddProducts);
                bottomSheetDialog.dismiss();
            }
        });
    }
    private void AddtoUserFavouriteList(ModelAddProducts modelAddProducts) {


        DatabaseReference database = FirebaseDatabase.getInstance().getReference("TotalAppUsers");
        database.child(modelAddProducts.getProductUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String shopName = "" + snapshot.child("name").getValue();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalLikedFoods");
                String timestamp = String.valueOf(System.currentTimeMillis());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("likedfoodid", timestamp);
                hashMap.put("likedfoodname", modelAddProducts.getpName());
                hashMap.put("likedfoodshopname", shopName);
                hashMap.put("likedfooduserid", user.getUid());
                hashMap.put("likedfoodproductimage",modelAddProducts.getProductimage());
                hashMap.put("likedfoodoriprice", modelAddProducts.getOriPrice());
                hashMap.put("likedfooddiscountprice", modelAddProducts.getDiscountPrice());
                hashMap.put("likedfooditemtype", modelAddProducts.getItemType());
                hashMap.put("likedfooddiscountnote", modelAddProducts.getDiscontNote());
                databaseReference.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Added To Favourites List", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private int itemID = 1;
    private void AddToCartDatabase(String cartFoodName, String cartFoodQuantity, String cartFoodPrice, String cartUserId, String cartProductId, String foodOriginalTotalPrice, ProgressBar addCartProgressIndicator, BottomSheetDialog bottomSheetDialog) {

        itemID++;
        EasyDB easyDBBestNew = EasyDB.init(context, user.getUid())
                .setTableName("Items")
                .addColumn(new Column("foodid", new String[]{"text", "unique"}))
                .addColumn(new Column("foodPid", new String[]{"text", "not null"}))
                .addColumn(new Column("foodPName", new String[]{"text", "not null"}))
                .addColumn(new Column("foodUserName", new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalOriginalPrice", new String[]{"text", "not null"}))
                .addColumn(new Column("foodQuantity", new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalDiscountedPrice", new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalPrice", new String[]{"text", "not null"}))
                .doneTableColumn();
        Boolean add = easyDBBestNew
                .addData("foodid", itemID)
                .addData("foodPid", cartProductId)
                .addData("foodPName", cartFoodName)
                .addData("foodUserName", cartUserId)
                .addData("foodTotalOriginalPrice", foodOriginalTotalPrice)
                .addData("foodQuantity", cartFoodQuantity)
                .addData("foodTotalDiscountedPrice", cartFoodPrice)
                .addData("foodTotalPrice", cartFoodPrice)
                .doneDataAdding();

        if (add && cartFoodPrice.equals("0")) {
            addCartProgressIndicator.setVisibility(View.GONE);
            Toast.makeText(context, "Error !! Item Not Added To Cart", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            return;
        }
        if (add) {
            addCartProgressIndicator.setVisibility(View.GONE);
            Toast.makeText(context, "Item Added To Cart Successfully", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            return;
        } else {
            addCartProgressIndicator.setVisibility(View.GONE);
            Toast.makeText(context, " Error !! Item Not Added To Cart....", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            return;
        }

    }

    @Override
    public int getItemCount() {

        return isShimmerItems ? isShimmerItemNum : modelAddProductsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filterFoodItems==null)
            filterFoodItems=new FilterFoodItems(this,filterList);

        return filterFoodItems;
    }


    public class ShopItemsHolder extends RecyclerView.ViewHolder {
        ImageView FoodImage, itemTypeImageIcon;
        ShimmerFrameLayout itemsShimmerLayout;
        TextView FoodItemName, FoodItemOriginalPrice, FoodItemDescription, FoodItemDiscountedPrice;
        RelativeLayout addProductLayout;

        public ShopItemsHolder(@NonNull View itemView) {
            super(itemView);
            itemsShimmerLayout = itemView.findViewById(R.id.itemsShimmerLayout);
            itemTypeImageIcon = itemView.findViewById(R.id.itemTypeImageIcon);
            FoodImage = itemView.findViewById(R.id.FoodImage);
            FoodItemName = itemView.findViewById(R.id.FoodItemNameNew);
            FoodItemOriginalPrice = itemView.findViewById(R.id.FoodItemOriginalPriceNew);
            FoodItemDescription = itemView.findViewById(R.id.FoodItemDescriptionNew);
            FoodItemDiscountedPrice = itemView.findViewById(R.id.FoodItemDiscountedPriceNew);
            addProductLayout = itemView.findViewById(R.id.addProductLayout);

        }
    }


}
