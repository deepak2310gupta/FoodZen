package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodzen.CollectionAdapters.AdapterPromoCodes;
import com.example.foodzen.CollectionAdapters.AdapterRestaurantItems;
import com.example.foodzen.CollectionAdapters.AdapterRestaurants;
import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.CollectionModels.ModelFoodItem;
import com.example.foodzen.CollectionModels.ModelPromoCodes;
import com.example.foodzen.CollectionModels.ModelSeller;
import com.example.foodzen.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantShowItemsActivity extends AppCompatActivity {


    private RecyclerView RestaurantShowItemsRecyclerView;
    private AdapterRestaurantItems adapterRestaurantItems;
    private AdapterPromoCodes adapterPromoCodes;
    private ArrayList<ModelAddProducts> modelFoodItemArrayList;
    private ArrayList<ModelPromoCodes> modelPromoCodesArrayList;
    private RecyclerView PromoCodesRecyclerViewCollections;
    private String shopId;
    private TextView ShopNameTv,ShopCategoryTv,ShopAddresstv;
    private String shopName,shopAddress,shopCategory;
    ImageButton BackToListRestaurants;
    private ArrayList<String>ListCategory;
    LinearLayout fssaiAndLicenseLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_show_items);
        initViews();
        shopId=getIntent().getStringExtra("ResShopId");
        shopName=getIntent().getStringExtra("ResShopName");
        shopAddress=getIntent().getStringExtra("ResShopAddress");
        shopCategory=getIntent().getStringExtra("ResCategory");
        ListCategory=new ArrayList<>();
        ShopNameTv.setText(shopName);
        ShopCategoryTv.setText(shopCategory);
        ShopAddresstv.setText(shopAddress);
        adapterRestaurantItems=new AdapterRestaurantItems(RestaurantShowItemsActivity.this,modelFoodItemArrayList);
        adapterPromoCodes=new AdapterPromoCodes(RestaurantShowItemsActivity.this,modelPromoCodesArrayList);
        RestaurantShowItemsRecyclerView.setAdapter(adapterRestaurantItems);
        PromoCodesRecyclerViewCollections.setAdapter(adapterPromoCodes);
        Query query= FirebaseDatabase.getInstance().getReference("TotalProductsSeller").orderByChild("productUserId").equalTo(shopId);
        query.addListenerForSingleValueEvent(valueEventListener);
        Query query1=FirebaseDatabase.getInstance().getReference("TotalPromotionCodes").orderByChild("promocodeUserId").equalTo(shopId);
        query1.addListenerForSingleValueEvent(valueEventListenerNew);
        FetchTopPicksAccordingToUser();
        BackToListRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void FetchTopPicksAccordingToUser(){

        for(int i=0;i<ListCategory.size();i++){

            Query query2= FirebaseDatabase.getInstance().getReference("TopPicksCollection").orderByChild("categoryRestaurant").equalTo(ListCategory.get(i));
            query2.addListenerForSingleValueEvent(valueEventListenerOne);
        }
    }

    private void initViews() {

        ShopNameTv=findViewById(R.id.ShopNameTv);
        ShopCategoryTv=findViewById(R.id.ShopCategoryTv);
        ShopAddresstv=findViewById(R.id.ShopAddresstv);
        RestaurantShowItemsRecyclerView=findViewById(R.id.RestaurantShowItemsRecyclerView);
        PromoCodesRecyclerViewCollections=findViewById(R.id.PromoCodesRecyclerViewCollections);
        BackToListRestaurants=findViewById(R.id.BackToListRestaurants);
        modelFoodItemArrayList=new ArrayList<>();
        modelPromoCodesArrayList=new ArrayList<>();
        fssaiAndLicenseLogo=findViewById(R.id.fssaiAndLicenseLogo);
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelFoodItemArrayList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    ModelAddProducts modelAddProducts=snapshot.getValue(ModelAddProducts.class);
                    modelFoodItemArrayList.add(modelAddProducts);
                }
                adapterRestaurantItems.notifyDataSetChanged();
                fssaiAndLicenseLogo.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(RestaurantShowItemsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener valueEventListenerNew=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelPromoCodesArrayList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    ModelPromoCodes modelPromoCodes=snapshot.getValue(ModelPromoCodes.class);
                    modelPromoCodesArrayList.add(modelPromoCodes);
                }
                adapterPromoCodes.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(RestaurantShowItemsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener valueEventListenerOne=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelFoodItemArrayList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    ModelAddProducts modelAddProducts=snapshot.getValue(ModelAddProducts.class);
                    modelFoodItemArrayList.add(modelAddProducts);
                }
                adapterRestaurantItems.notifyDataSetChanged();
                fssaiAndLicenseLogo.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(RestaurantShowItemsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}