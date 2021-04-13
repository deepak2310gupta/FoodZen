package com.example.foodzen.CollectionFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView RestaurantsRecylerView;
    private AdapterRestaurants adapterRestaurants;
    private ArrayList<ModelSeller> modelSellerList;
    ImageSlider imageSlider;
    FirebaseAuth firebaseAuth;
    private RecyclerView TopPicksRecyclerView;
    ArrayList<ModelTopPicks> modelTopPicksArrayList;
    AdapterTopPicks adapterTopPicks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        RestaurantsRecylerView = view.findViewById(R.id.RestaurantsRecylerView);
        TopPicksRecyclerView = view.findViewById(R.id.TopPicksRecyclerView);

        imageSlider = view.findViewById(R.id.image_slider);
        RestaurantsRecylerView.setHasFixedSize(true);
        RestaurantsRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        modelSellerList = new ArrayList<>();
        modelTopPicksArrayList = new ArrayList<>();

        List<SlideModel> imageListCollection = new ArrayList<>();

        adapterRestaurants = new AdapterRestaurants(getContext(), modelSellerList);

        RestaurantsRecylerView.setAdapter(adapterRestaurants);

        adapterTopPicks = new AdapterTopPicks(getContext(), modelTopPicksArrayList);
        TopPicksRecyclerView.setAdapter(adapterTopPicks);

        imageListCollection.add(new SlideModel(R.drawable.sliderfive));
        imageListCollection.add(new SlideModel(R.drawable.nextimage));
        imageListCollection.add(new SlideModel(R.drawable.sliderthird));
        imageListCollection.add(new SlideModel(R.drawable.slidesix));
        imageListCollection.add(new SlideModel(R.drawable.sliderfour));

        imageSlider.setImageList(imageListCollection, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Query query = FirebaseDatabase.getInstance().getReference("TotalAppUsers").orderByChild("usertype").equalTo("RestaurantSeller");
                query.addListenerForSingleValueEvent(valueEventListener);
                adapterRestaurants.isShimmer = false;
            }
        }, 1900);

        showTopPicksDialog();

        return view;
    }

    private void showTopPicksDialog() {

        if (helpingforTopPickslayout()) {
            ArrayList<String> listRestaurant = new ArrayList<>();
            View view = LayoutInflater.from(getContext()).inflate(R.layout.toppickslayoutprefer, null);
            CheckBox pizzahut = view.findViewById(R.id.pizzahut);
            CheckBox haldirams = view.findViewById(R.id.haldirams);
            CheckBox cafecoffeeday = view.findViewById(R.id.cafecoffeeday);
            CheckBox dominoz = view.findViewById(R.id.dominoz);
            CheckBox bikanervala = view.findViewById(R.id.bikanervala);
            CheckBox burgerking = view.findViewById(R.id.burgerking);
            CheckBox starbucks = view.findViewById(R.id.starbucks);
            CheckBox kfc = view.findViewById(R.id.kfc);
            CheckBox sagarratna = view.findViewById(R.id.sagarratna);
            Button doneButtonTopPicks = view.findViewById(R.id.doneButtonTopPicks);
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.show();

            doneButtonTopPicks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (pizzahut.isChecked()) {
                        listRestaurant.add("pizzahutone");
                    }
                    if (haldirams.isChecked()) {
                        listRestaurant.add("haldiramstwo");
                    }
                    if (cafecoffeeday.isChecked()) {
                        listRestaurant.add("ccdthree");
                    }
                    if (dominoz.isChecked()) {
                        listRestaurant.add("dominosfour");
                    }
                    if (bikanervala.isChecked()) {
                        listRestaurant.add("bikanervalafive");
                    }
                    if (burgerking.isChecked()) {
                        listRestaurant.add("burgerkingfive");
                    }
                    if (starbucks.isChecked()) {
                        listRestaurant.add("starbuckssix");
                    }
                    if (kfc.isChecked()) {
                        listRestaurant.add("kfcseven");
                    }
                    if (sagarratna.isChecked()) {
                        listRestaurant.add("sagarratnaeight");
                    }
                    addTopPicks(listRestaurant, bottomSheetDialog);
                }

            });

        } else {
            Query dbReferebnce = FirebaseDatabase.getInstance().getReference("CollectionTopPicks").child(firebaseAuth.getUid()).orderByChild("topPickuserid").equalTo(firebaseAuth.getUid());
            dbReferebnce.addListenerForSingleValueEvent(valueEventListenerTopPicks);
            adapterTopPicks.isShimmerNew = false;
            return;
        }


    }

    private void addTopPicks(ArrayList<String> listRestaurant, BottomSheetDialog bottomSheetDialog) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        for (int i = 0; i < listRestaurant.size(); i++) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalPicksRestaurant");
            databaseReference.orderByChild("uid").equalTo(listRestaurant.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        String one = "" + ds.child("uid").getValue();
                        String two = "" + ds.child("name").getValue();
                        String three = "" + ds.child("discountnote").getValue();
                        DatabaseReference databaseReferencePicks = FirebaseDatabase.getInstance().getReference("CollectionTopPicks");
                        HashMap<String, Object> hashMapTopPicks = new HashMap<>();
                        String timeVal = String.valueOf(System.currentTimeMillis());
                        hashMapTopPicks.put("shopId", one);
                        hashMapTopPicks.put("shopName", two);
                        hashMapTopPicks.put("shopDiscountNoteOff", three);
                        hashMapTopPicks.put("topPickid", timeVal);
                        hashMapTopPicks.put("topPickuserid", firebaseAuth.getUid());
                        databaseReferencePicks.child(firebaseUser.getUid()).child(timeVal).setValue(hashMapTopPicks);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        Query dbReferebnce = FirebaseDatabase.getInstance().getReference("CollectionTopPicks").child(firebaseAuth.getUid()).orderByChild("topPickuserid").equalTo(firebaseAuth.getUid());
        dbReferebnce.addListenerForSingleValueEvent(valueEventListenerTopPicks);
        adapterTopPicks.isShimmerNew = false;
        bottomSheetDialog.dismiss();
        return;
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelSellerList.clear();
            if (datasnapshot.exists()) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    ModelSeller modelSeller = snapshot.getValue(ModelSeller.class);
                    modelSellerList.add(modelSeller);
                }
                adapterRestaurants.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    ValueEventListener valueEventListenerTopPicks = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelTopPicksArrayList.clear();
            if (datasnapshot.exists()) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    ModelTopPicks modelTopPicks = snapshot.getValue(ModelTopPicks.class);
                    modelTopPicksArrayList.add(modelTopPicks);
                }
                adapterTopPicks.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private boolean helpingforTopPickslayout() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStartNewAgain = sharedPreferences.getBoolean("firstStartNewAgain", true);

        if (firstStartNewAgain == true) {

            SharedPreferences preferences = getContext().getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstStartNewAgain", false);
            editor.apply();
            return true;
        }
        return firstStartNewAgain;
    }


}