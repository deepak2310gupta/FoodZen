package com.example.foodzen.CollectionAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelCartItems;
import com.example.foodzen.R;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterCartItems extends RecyclerView.Adapter<AdapterCartItems.CartItemsHolder> {


    Context context;
    ArrayList<ModelCartItems> modelCartItemsArrayList;

    public AdapterCartItems(Context context, ArrayList<ModelCartItems> modelCartItemsArrayList) {
        this.context = context;
        this.modelCartItemsArrayList = modelCartItemsArrayList;
    }

    @NonNull
    @Override
    public CartItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cartitemsrows, parent, false);
        return new CartItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsHolder holder, int position) {
        ModelCartItems modelCartItems = modelCartItemsArrayList.get(position);
        String id = modelCartItems.getFoodid();
        String help = modelCartItems.getFoodUserName();
        holder.cartListFoodName.setText(modelCartItems.getFoodPName());
        holder.CartFinalQuantity.setText(modelCartItems.getFoodQuantity());
        holder.CartfinalItemPrice.setText(modelCartItems.getFoodTotalDiscountedPrice());

        holder.DeleteCartItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyDB easyDBBestNew = EasyDB.init(context, help)
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

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Item ?");
                builder.setMessage("Do You Want To Delete " + modelCartItems.getFoodPName() + " From Cart ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        easyDBBestNew.deleteRow(1, id);
                        modelCartItemsArrayList.remove(position);
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Item Removed Successfully!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelCartItemsArrayList.size();
    }

    public class CartItemsHolder extends RecyclerView.ViewHolder {
        TextView CartFinalQuantity, CartfinalItemPrice, cartListFoodName;
        ImageButton DeleteCartItemButton;

        public CartItemsHolder(@NonNull View itemView) {
            super(itemView);
            CartfinalItemPrice = itemView.findViewById(R.id.CartfinalItemPrice);
            CartFinalQuantity = itemView.findViewById(R.id.CartFinalQuantity);
            cartListFoodName = itemView.findViewById(R.id.cartListFoodName);
            DeleteCartItemButton = itemView.findViewById(R.id.DeleteCartItemButton);
        }
    }

}
