package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodzen.CollectionAdapters.AdapterRestaurantItems;
import com.example.foodzen.CollectionAdapters.AdapterRestaurants;
import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.CollectionModels.ModelFoodItem;
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
    private ArrayList<ModelAddProducts> modelFoodItemArrayList;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_show_items);
        shopId=getIntent().getStringExtra("ResShopId");
        RestaurantShowItemsRecyclerView=findViewById(R.id.RestaurantShowItemsRecyclerView);

        modelFoodItemArrayList=new ArrayList<>();
        adapterRestaurantItems=new AdapterRestaurantItems(RestaurantShowItemsActivity.this,modelFoodItemArrayList);
        RestaurantShowItemsRecyclerView.setAdapter(adapterRestaurantItems);

        Query query= FirebaseDatabase.getInstance().getReference("TotalProductsSeller").orderByChild("productUserId").equalTo(shopId);
        query.addListenerForSingleValueEvent(valueEventListener);

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
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(RestaurantShowItemsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

}