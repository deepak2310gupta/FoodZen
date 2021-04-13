package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.example.foodzen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                formingDataforTopPicksToUers();
                checkUserAlredyLoggedin();
            }
        }, 3100);

        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        findViewById(R.id.cardSplash).startAnimation(animZoomIn);


    }

    private void checkUserAlredyLoggedin() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            checkUserType();
            return;
        } else {
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            finish();
            return;
        }

    }

    private void checkUserType() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalAppUsers");
        databaseReference.orderByChild("uid").equalTo(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    String accounttype = "" + ds.child("usertype").getValue();
                    if (accounttype.equals("User")) {
                        Intent intent = new Intent(SplashActivity.this, DashboardUserActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    } else {
                        Intent intent = new Intent(SplashActivity.this, DashboardSellerActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SplashActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void formingDataforTopPicksToUers() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalPicksRestaurant");
        HashMap<String, Object> hashMapKey = new HashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        ArrayList<String> list4 = new ArrayList<>();
        list1.add("pizzahutone");
        list1.add("haldiramstwo");
        list1.add("ccdthree");
        list1.add("dominosfour");
        list1.add("bikanervalafive");
        list1.add("burgerkingfive");
        list1.add("starbuckssix");
        list1.add("kfcseven");
        list1.add("sagarratnaeight");
        list2.add("Pizza Hut");
        list2.add("Haldirams");
        list2.add("Cafe Coffee Day");
        list2.add("Domino's Pizza");
        list2.add("Bikanervala");
        list2.add("Burger King");
        list2.add("StarBucks");
        list2.add("KFC");
        list2.add("Sagar Ratna");
        list3.add("10% Off");
        list3.add("15% Off");
        list3.add("20% Off");
        list3.add("25% Off");
        list3.add("5% Off");
        list3.add("35% Off");
        list3.add("5% Off");
        list3.add("15% Off");
        list3.add("20% Off");

        for (int i = 0; i < list1.size(); i++) {
            hashMapKey.put("uid", list1.get(i));
            hashMapKey.put("name", list2.get(i));
            hashMapKey.put("discountnote", list3.get(i));
            databaseReference.child(list1.get(i)).setValue(hashMapKey);
        }
    }


}