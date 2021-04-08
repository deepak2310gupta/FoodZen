package com.example.foodzen.CollectionFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodzen.CollectionAdapters.AdapterPromoCodes;
import com.example.foodzen.CollectionAdapters.AdapterRestaurantItems;
import com.example.foodzen.CollectionAdapters.AdapterSellerProducts;
import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.CollectionModels.ModelPromoCodes;
import com.example.foodzen.CollectionModels.ModelSeller;
import com.example.foodzen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeSellerFragment extends Fragment {



    RecyclerView SellerPromCodesRecyclerView,TotalProductsRecyclerView;
    public HomeSellerFragment() {
        // Required empty public constructor
    }
    FirebaseAuth firebaseAuth;
    AdapterSellerProducts adapterSellerProducts;
    ArrayList<ModelAddProducts>modelAddProductsArrayList;
    ArrayList<ModelPromoCodes>modelPromoCodesArrayList;
    AdapterPromoCodes adapterPromoCodes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_seller, container, false);
        firebaseAuth=FirebaseAuth.getInstance();

        SellerPromCodesRecyclerView=view.findViewById(R.id.SellerPromCodesRecyclerView);
        TotalProductsRecyclerView=view.findViewById(R.id.TotalProductsRecyclerView);

        modelAddProductsArrayList=new ArrayList<>();
        modelPromoCodesArrayList=new ArrayList<>();

        adapterSellerProducts=new AdapterSellerProducts(getContext(),modelAddProductsArrayList);
        adapterPromoCodes=new AdapterPromoCodes(getContext(),modelPromoCodesArrayList);

        SellerPromCodesRecyclerView.setAdapter(adapterPromoCodes);
        TotalProductsRecyclerView.setAdapter(adapterSellerProducts);

        Query query= FirebaseDatabase.getInstance().getReference("TotalProductsSeller").orderByChild("productUserId").equalTo(firebaseAuth.getUid());
        query.addListenerForSingleValueEvent(valueEventListener);

        Query queryPromoCode= FirebaseDatabase.getInstance().getReference("TotalPromotionCodes").orderByChild("promocodeUserId").equalTo(firebaseAuth.getUid());
        queryPromoCode.addListenerForSingleValueEvent(valueEventListenerPromoCodes);
        return  view;
    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            modelAddProductsArrayList.clear();
            if(datasnapshot.exists()){
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    ModelAddProducts modelAddProducts=snapshot.getValue(ModelAddProducts.class);
                    modelAddProductsArrayList.add(modelAddProducts);
                }
                adapterSellerProducts.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener valueEventListenerPromoCodes=new ValueEventListener() {
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
            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };




}