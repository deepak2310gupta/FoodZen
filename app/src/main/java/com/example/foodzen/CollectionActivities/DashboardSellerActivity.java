package com.example.foodzen.CollectionActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.foodzen.CollectionFragments.AddProductFragment;
import com.example.foodzen.CollectionFragments.FavouritesFragment;
import com.example.foodzen.CollectionFragments.HomeFragment;
import com.example.foodzen.CollectionFragments.HomeSellerFragment;
import com.example.foodzen.CollectionFragments.ProfileFragment;
import com.example.foodzen.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DashboardSellerActivity extends AppCompatActivity {


    ChipNavigationBar menuChipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_seller);

        menuChipNavigationBar=findViewById(R.id.menuChipNavigationBarForSeller);

        HomeSellerFragment homeSellerFragment = new HomeSellerFragment();
        FragmentTransaction fragmentTransactionAgain1 = getSupportFragmentManager().beginTransaction();
        fragmentTransactionAgain1.replace(R.id.collectionfragmentsReplacerForSeller, homeSellerFragment,"").commit();

        menuChipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.homeSeller:
                        HomeSellerFragment homeSellerFragment = new HomeSellerFragment();
                        FragmentTransaction fragmentTransactionAgain1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain1.replace(R.id.collectionfragmentsReplacerForSeller, homeSellerFragment,"").commit();
                        break;
                    case R.id.AddproductSeller:
                        AddProductFragment addProductFragment = new AddProductFragment();
                        FragmentTransaction fragmentTransactionAgain2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain2.replace(R.id.collectionfragmentsReplacerForSeller, addProductFragment,"").commit();
                        break;
                    case R.id.profileSeller:
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentTransaction fragmentTransactionAgain3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain3.replace(R.id.collectionfragmentsReplacerForSeller, profileFragment,"").commit();
                        break;
                }
            }
        });
    }
}