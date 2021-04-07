package com.example.foodzen.CollectionFragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

    FirebaseAuth firebaseAuth;
    ArrayList<ModelCartItems>modelCartItemsArrayList;
    RecyclerView cartItemsCollectionRecyclerView;
    AdapterCartItems adapterCartItems;
    Button ProceedToPayment;
    TextView NetCurrentFinalTotalSumBeforeDeliveryFee;
    TextView NetCurrentFinalTotalSumAfterDeliveryFee;
    TextView RestaurantShopNameShowOnCart;
    LinearLayout EmptyCartDialog,OrdeToLayout;
    CardView BillCardLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        NetCurrentFinalTotalSumAfterDeliveryFee=view.findViewById(R.id.NetCurrentFinalTotalSumAfterDeliveryFee);
        NetCurrentFinalTotalSumBeforeDeliveryFee=view.findViewById(R.id.NetCurrentFinalTotalSumBeforeDeliveryFee);
        RestaurantShopNameShowOnCart=view.findViewById(R.id.RestaurantShopNameShowOnCart);

        BillCardLayout=view.findViewById(R.id.BillCardLayout);
        EmptyCartDialog=view.findViewById(R.id.EmptyCartDialog);
        OrdeToLayout=view.findViewById(R.id.OrdeToLayout);
        ProceedToPayment=view.findViewById(R.id.ProceedToPayment);
        cartItemsCollectionRecyclerView=view.findViewById(R.id.cartItemsCollectionRecyclerView);


        showCartItems();

        return view;
    }


    private void showCartItems(){

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String help=firebaseUser.getUid();

        modelCartItemsArrayList=new ArrayList<>();
        int sum=0;
        EasyDB easyDBBestNew = EasyDB.init(getContext(), help)
                .setTableName("Items")
                .addColumn(new Column("foodid",new String[]{"text", "unique"}))
                .addColumn(new Column("foodPid",new String[]{"text", "not null"}))
                .addColumn(new Column("foodPName",new String[]{"text", "not null"}))
                .addColumn(new Column("foodUserName",new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalOriginalPrice",new String[]{"text", "not null"}))
                .addColumn(new Column("foodQuantity", new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalDiscountedPrice", new String[]{"text", "not null"}))
                .addColumn(new Column("foodTotalPrice", new String[]{"text", "not null"}))
                .doneTableColumn();

        Cursor cursor=easyDBBestNew.getAllData();
        while (cursor.moveToNext()) {
                String rid=cursor.getString(1);
                String foodPid = cursor.getString(2);
                String foodPname = cursor.getString(3);
                String foodUserId = cursor.getString(4);
                String foodTotaloriprice = cursor.getString(5);
                String foodQuantity = cursor.getString(6);
                String foodTotaldiscountprice = cursor.getString(7);
                String foodtotalPrice = cursor.getString(8);

                ModelCartItems modelCartItems = new ModelCartItems(
                        ""+rid,
                        "" + foodPid,
                        "" + foodPname,
                        "" + foodUserId,
                        "" + foodTotaloriprice,
                        "" + foodQuantity,
                        "" + foodTotaldiscountprice,
                        "" + foodtotalPrice
                );

                sum = sum + Integer.parseInt(foodTotaldiscountprice);
                modelCartItemsArrayList.add(modelCartItems);
            }
            NetCurrentFinalTotalSumBeforeDeliveryFee.setText(Integer.toString(sum));
            NetCurrentFinalTotalSumAfterDeliveryFee.setText(Integer.toString(sum + 25));
            adapterCartItems = new AdapterCartItems(getContext(), modelCartItemsArrayList);
            cartItemsCollectionRecyclerView.setAdapter(adapterCartItems);


    }



}