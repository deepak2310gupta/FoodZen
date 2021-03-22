package com.example.foodzen.CollectionActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodzen.R;

public class MainActivity extends AppCompatActivity {

    TextView worldhello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        worldhello=findViewById(R.id.worldhello);
        worldhello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    
}