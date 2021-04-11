package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class SplashActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserAlredyLoggedin();
            }
        },2900);

        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);
        findViewById(R.id.cardSplash).startAnimation(animZoomIn);




    }

    private void checkUserAlredyLoggedin() {

        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            checkUserType();
        }else{
            startActivity(new Intent(SplashActivity.this,SignInActivity.class));
            finish();
        }

    }
    private void checkUserType(){

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("TotalAppUsers");
        databaseReference.orderByChild("uid").equalTo(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){

                    String accounttype=""+ds.child("usertype").getValue();
                    if(accounttype.equals("User")){
                        Intent intent = new Intent(SplashActivity.this, DashboardUserActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                    else{
                        Intent intent = new Intent(SplashActivity.this, DashboardSellerActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SplashActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}