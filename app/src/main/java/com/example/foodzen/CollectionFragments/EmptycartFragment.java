package com.example.foodzen.CollectionFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodzen.R;


public class EmptycartFragment extends Fragment {


    public EmptycartFragment() {
        // Required empty public constructor
    }

    TextView BrowseAllRestaurants;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emptycart, container, false);
        BrowseAllRestaurants = view.findViewById(R.id.BrowseAllRestaurants);

        BrowseAllRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeNewfrag = new HomeFragment();
                FragmentTransaction fragmentTransactionAgain6 = getFragmentManager().beginTransaction();
                fragmentTransactionAgain6.replace(R.id.collectionfragmentsReplacer, homeNewfrag, "").commit();

            }
        });

        return view;
    }
}