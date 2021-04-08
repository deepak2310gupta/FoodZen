package com.example.foodzen.CollectionFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodzen.CollectionActivities.DashboardSellerActivity;
import com.example.foodzen.CollectionActivities.DashboardUserActivity;
import com.example.foodzen.CollectionActivities.SignInActivity;
import com.example.foodzen.CollectionAdapters.AdapterRestaurants;
import com.example.foodzen.CollectionAdapters.AdapterTopPicks;
import com.example.foodzen.CollectionModels.ModelSeller;
import com.example.foodzen.CollectionModels.ModelTopPicks;
import com.example.foodzen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }
    private RecyclerView RestaurantsRecylerView;
    private AdapterRestaurants adapterRestaurants;
    private ArrayList<ModelSeller>modelSellerList;
    ImageSlider imageSlider;
    FirebaseAuth firebaseAuth;
    private RecyclerView TopPicksRecyclerView;
    ArrayList<ModelTopPicks>modelTopPicksArrayList;
    AdapterTopPicks adapterTopPicks;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home2, container, false);
        firebaseAuth=FirebaseAuth.getInstance();

        RestaurantsRecylerView=view.findViewById(R.id.RestaurantsRecylerView);
        TopPicksRecyclerView=view.findViewById(R.id.TopPicksRecyclerView);

        imageSlider=view.findViewById(R.id.image_slider);
        RestaurantsRecylerView.setHasFixedSize(true);
        RestaurantsRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        modelSellerList=new ArrayList<>();
        modelTopPicksArrayList=new ArrayList<>();

        List<SlideModel> imageListCollection=new ArrayList<>();

        adapterRestaurants=new AdapterRestaurants(getContext(),modelSellerList);
        RestaurantsRecylerView.setAdapter(adapterRestaurants);

        adapterTopPicks=new AdapterTopPicks(getContext(),modelTopPicksArrayList);
        TopPicksRecyclerView.setAdapter(adapterTopPicks);

        imageListCollection.add(new SlideModel(R.drawable.sliderfive));
        imageListCollection.add(new SlideModel(R.drawable.sliderfour));
        imageListCollection.add(new SlideModel(R.drawable.sliderthird));
        imageListCollection.add(new SlideModel(R.drawable.slidesix));

        imageSlider.setImageList(imageListCollection,false);

        Query query= FirebaseDatabase.getInstance().getReference("TotalAppUsers").orderByChild("usertype").equalTo("RestaurantSeller");
        query.addListenerForSingleValueEvent(valueEventListener);

        showTopPicksDialogAccordingToUser();

        Query dbReferebnce= FirebaseDatabase.getInstance().getReference("CollectionTopPicks").child(firebaseAuth.getUid()).orderByChild("topPickuserid").equalTo(firebaseAuth.getUid());
        dbReferebnce.addListenerForSingleValueEvent(valueEventListenerTopPicks);

        return  view;
    }

    private void showTopPicksDialogAccordingToUser(){

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        ArrayList<String>listRestaurant=new ArrayList<>();
        listRestaurant.add("Fmdu06Lrb0gNBn9kearMHWKnysB3");
        listRestaurant.add("cz7ylb8KvHNp0jYiT0ykWPmNsE83");
        listRestaurant.add("tCSZ8GFBXPRkdixDcajmLPD7KTr1");
        for(int i=0;i<listRestaurant.size();i++){

            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("TotalAppUsers");
            databaseReference.orderByChild("uid").equalTo(listRestaurant.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){

                        String one=""+ds.child("uid").getValue();
                        String two=""+ds.child("name").getValue();
                        String three=""+ds.child("discountnote").getValue();
                        String four=""+ds.child("category").getValue();
                        DatabaseReference databaseReferencePicks=FirebaseDatabase.getInstance().getReference("CollectionTopPicks");
                        HashMap<String,Object>hashMapTopPicks=new HashMap<>();
                        String timeVal= String.valueOf(System.currentTimeMillis());
                        hashMapTopPicks.put("shopId",one);
                        hashMapTopPicks.put("shopName",two);
                        hashMapTopPicks.put("shopDiscountNoteOff",three);
                        hashMapTopPicks.put("shopCategorytype",four);
                        hashMapTopPicks.put("topPickid",timeVal);
                        hashMapTopPicks.put("topPickuserid",firebaseAuth.getUid());
                        databaseReferencePicks.child(firebaseUser.getUid()).child(timeVal).setValue(hashMapTopPicks);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }



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


    ValueEventListener valueEventListenerTopPicks=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelTopPicksArrayList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    ModelTopPicks modelTopPicks=snapshot.getValue(ModelTopPicks.class);
                    modelTopPicksArrayList.add(modelTopPicks);
                }
                adapterTopPicks.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


}