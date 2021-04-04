package com.example.foodzen.CollectionActivities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.example.foodzen.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        },2950);

        Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);
        findViewById(R.id.cardSplash).startAnimation(animZoomIn);

    }


    
}