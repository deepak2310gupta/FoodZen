package com.example.foodzen.CollectionFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodzen.R;

public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }
    TextView BrowseAllRestaurants;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        BrowseAllRestaurants=view.findViewById(R.id.BrowseAllRestaurants);

        BrowseAllRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ;
            }
        });
        return view;
    }
}