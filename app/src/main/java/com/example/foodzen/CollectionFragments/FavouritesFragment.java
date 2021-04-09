package com.example.foodzen.CollectionFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodzen.CollectionAdapters.AdapterLikedFood;
import com.example.foodzen.CollectionAdapters.AdapterRestaurants;
import com.example.foodzen.CollectionModels.ModelLikedFoods;
import com.example.foodzen.CollectionModels.ModelSeller;
import com.example.foodzen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FavouritesFragment extends Fragment {


    public FavouritesFragment() {
        // Required empty public constructor
    }
    FirebaseAuth firebaseAuth;
    RecyclerView LikedFoodsRecyclerView;
    AdapterLikedFood adapterLikedFood;
    ArrayList<ModelLikedFoods>modelLikedFoodsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_favourites, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        modelLikedFoodsArrayList=new ArrayList<>();
        LikedFoodsRecyclerView=view.findViewById(R.id.LikedFoodsRecyclerView);
        adapterLikedFood=new AdapterLikedFood(getContext(),modelLikedFoodsArrayList);
        LikedFoodsRecyclerView.setAdapter(adapterLikedFood);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Query query= FirebaseDatabase.getInstance().getReference("TotalLikedFoods").orderByChild("likedfooduserid").equalTo(firebaseAuth.getUid());
                query.addListenerForSingleValueEvent(valueEventListener);
                adapterLikedFood.isLikedShimmer=false;
            }
        },1900);


        return view;
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelLikedFoodsArrayList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    ModelLikedFoods modelLikedFoods=snapshot.getValue(ModelLikedFoods.class);
                    modelLikedFoodsArrayList.add(modelLikedFoods);
                }
                adapterLikedFood.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}