package com.example.foodzen.CollectionActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.foodzen.CollectionFragments.CartFragment;
import com.example.foodzen.CollectionFragments.FavouritesFragment;
import com.example.foodzen.CollectionFragments.HomeFragment;
import com.example.foodzen.CollectionFragments.ProfileFragment;
import com.example.foodzen.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class DashboardUserActivity extends AppCompatActivity {

    ChipNavigationBar menuChipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        menuChipNavigationBar=findViewById(R.id.menuChipNavigationBar);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransactionAgain1 = getSupportFragmentManager().beginTransaction();
        fragmentTransactionAgain1.replace(R.id.collectionfragmentsReplacer, homeFragment,"").commit();

        menuChipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentTransaction fragmentTransactionAgain1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain1.replace(R.id.collectionfragmentsReplacer, homeFragment,"").commit();
                        break;
                    case R.id.favourites:
                        FavouritesFragment favouritesFragment = new FavouritesFragment();
                        FragmentTransaction fragmentTransactionAgain2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain2.replace(R.id.collectionfragmentsReplacer, favouritesFragment,"").commit();
                        break;
                    case R.id.userCart:
                        CartFragment cartFragment = new CartFragment();
                        FragmentTransaction fragmentTransactionAgain3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain3.replace(R.id.collectionfragmentsReplacer, cartFragment,"").commit();
                        break;
                }
            }
        });


    }






}