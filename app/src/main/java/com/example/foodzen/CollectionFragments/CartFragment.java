package com.example.foodzen.CollectionFragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodzen.CollectionAdapters.AdapterCartItems;
import com.example.foodzen.CollectionModels.ModelCartItems;
import com.example.foodzen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }
    TextView BrowseAllRestaurants;
    FirebaseAuth firebaseAuth;
    ArrayList<ModelCartItems>modelCartItemsArrayList;
    RecyclerView cartItemsCollectionRecyclerView;
    AdapterCartItems adapterCartItems;
    TextView NetCurrentFinalTotalSumBeforeDeliveryFee;
    TextView NetCurrentFinalTotalSumAfterDeliveryFee;
    TextView RestaurantShopNameShowOnCart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        NetCurrentFinalTotalSumAfterDeliveryFee=view.findViewById(R.id.NetCurrentFinalTotalSumAfterDeliveryFee);
        NetCurrentFinalTotalSumBeforeDeliveryFee=view.findViewById(R.id.NetCurrentFinalTotalSumBeforeDeliveryFee);
        RestaurantShopNameShowOnCart=view.findViewById(R.id.RestaurantShopNameShowOnCart);
        BrowseAllRestaurants=view.findViewById(R.id.BrowseAllRestaurants);
        cartItemsCollectionRecyclerView=view.findViewById(R.id.cartItemsCollectionRecyclerView);
        BrowseAllRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        });
        showCartItems();
        return view;
    }


    private void showCartItems(){

        modelCartItemsArrayList=new ArrayList<>();
        int sum=0;
        EasyDB easyDBBest = EasyDB.init(getContext(), "It_124")
                .setTableName("Items")
                .addColumn(new Column("rowid",new String[]{"text", "unique"}))
                .addColumn(new Column("foodPid",new String[]{"text", "not null"}))
                .addColumn(new Column("foodPName",new String[]{"text", "not null"}))
                .addColumn(new Column("foodUserName",new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalOriginalPrice",new String[]{"text", "not null"}))
                .addColumn(new Column("foodQuantity", new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalDiscountedPrice", new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalPrice", new String[]{"text", "not null"}))
                .doneTableColumn();

        Cursor cursor=easyDBBest.getAllData();
        while (cursor.moveToNext()){
            String rowId=cursor.getString(1);
            String foodPid=cursor.getString(2);
            String foodPname=cursor.getString(3);
            String foodUserId=cursor.getString(4);
            String foodTotaloriprice=cursor.getString(5);
            String foodQuantity=cursor.getString(6);
            String foodTotaldiscountprice=cursor.getString(7);
            String foodtotalPrice=cursor.getString(8);

            ModelCartItems modelCartItems=new ModelCartItems(
                    ""+rowId,
                    ""+foodPid,
                    ""+foodPname,
                    ""+foodUserId,
                    ""+foodTotaloriprice,
                    ""+foodQuantity,
                    ""+foodTotaldiscountprice,
                    ""+foodtotalPrice
            );

            sum=sum+Integer.parseInt(foodTotaldiscountprice);
            modelCartItemsArrayList.add(modelCartItems);
        }
        NetCurrentFinalTotalSumBeforeDeliveryFee.setText(Integer.toString(sum));
        NetCurrentFinalTotalSumAfterDeliveryFee.setText(Integer.toString(sum+25));
        adapterCartItems=new AdapterCartItems(getContext(),modelCartItemsArrayList);
        cartItemsCollectionRecyclerView.setAdapter(adapterCartItems);

    }



}