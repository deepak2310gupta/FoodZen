package com.example.foodzen.CollectionFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodzen.CollectionAdapters.AdapterRestaurants;
import com.example.foodzen.CollectionModels.ModelSeller;
import com.example.foodzen.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }
    private RecyclerView RestaurantsRecylerView;
    private AdapterRestaurants adapterRestaurants;
    private ArrayList<ModelSeller>modelSellerList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home2, container, false);
        RestaurantsRecylerView=view.findViewById(R.id.RestaurantsRecylerView);
        RestaurantsRecylerView.setHasFixedSize(true);
        RestaurantsRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        modelSellerList=new ArrayList<>();
        adapterRestaurants=new AdapterRestaurants(getContext(),modelSellerList);
        RestaurantsRecylerView.setAdapter(adapterRestaurants);

        Query query= FirebaseDatabase.getInstance().getReference("TotalAppUsers").orderByChild("usertype").equalTo("RestaurantSeller");
        query.addListenerForSingleValueEvent(valueEventListener);
        return  view;
    }


    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelSellerList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    ModelSeller modelSeller=snapshot.getValue(ModelSeller.class);
                    modelSellerList.add(modelSeller);
                }
                adapterRestaurants.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

}