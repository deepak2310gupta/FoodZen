package com.example.foodzen.CollectionActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.example.foodzen.CollectionFragments.CartFragment;
import com.example.foodzen.CollectionFragments.EmptycartFragment;
import com.example.foodzen.CollectionFragments.FavouritesFragment;
import com.example.foodzen.CollectionFragments.HomeFragment;
import com.example.foodzen.CollectionFragments.ProfileFragment;
import com.example.foodzen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;


public class DashboardUserActivity extends AppCompatActivity {

    ChipNavigationBar menuChipNavigationBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();

        menuChipNavigationBar = findViewById(R.id.menuChipNavigationBar);

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransactionAgain1 = getSupportFragmentManager().beginTransaction();
        fragmentTransactionAgain1.replace(R.id.collectionfragmentsReplacer, homeFragment, "").commit();

        menuChipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentTransaction fragmentTransactionAgain1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain1.replace(R.id.collectionfragmentsReplacer, homeFragment, "").commit();
                        break;
                    case R.id.favourites:
                        FavouritesFragment favouritesFragment = new FavouritesFragment();
                        FragmentTransaction fragmentTransactionAgain2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAgain2.replace(R.id.collectionfragmentsReplacer, favouritesFragment, "").commit();
                        break;
                    case R.id.userCart:
                        int track = 0;
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String help = firebaseUser.getUid();
                        EasyDB easyDBBestNew = EasyDB.init(DashboardUserActivity.this, help)
                                .setTableName("Items")
                                .addColumn(new Column("foodid", new String[]{"text", "unique"}))
                                .addColumn(new Column("foodPid", new String[]{"text", "not null"}))
                                .addColumn(new Column("foodPName", new String[]{"text", "not null"}))
                                .addColumn(new Column("foodUserName", new String[]{"text", "not null"}))
                                .addColumn(new Column("foodTotalOriginalPrice", new String[]{"text", "not null"}))
                                .addColumn(new Column("foodQuantity", new String[]{"text", "not null"}))
                                .addColumn(new Column("foodTotalDiscountedPrice", new String[]{"text", "not null"}))
                                .addColumn(new Column("foodTotalPrice", new String[]{"text", "not null"}))
                                .doneTableColumn();
                        Cursor res = easyDBBestNew.getAllData();
                        while (res.moveToNext()) {
                            track++;
                        }
                        Cursor cursor = easyDBBestNew.getOneRowData(track);
                        Cursor ncursor = easyDBBestNew.getOneRowData(++track);
                        if (cursor == null && ncursor == null) {
                            EmptycartFragment emptycartFragment = new EmptycartFragment();
                            FragmentTransaction fragmentTransactionAgain4 = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionAgain4.replace(R.id.collectionfragmentsReplacer, emptycartFragment, "").commit();
                            break;
                        } else {
                            CartFragment cartFragment = new CartFragment();
                            FragmentTransaction fragmentTransactionAgain3 = getSupportFragmentManager().beginTransaction();
                            fragmentTransactionAgain3.replace(R.id.collectionfragmentsReplacer, cartFragment, "").commit();
                            break;
                        }
                }

            }
        });


    }


}