package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodzen.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    Button logiUserButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Pattern pattern;
    EditText loginEmailEt,loginPasswordEt;
    LinearProgressIndicator registerProgressLinearIndicator;
    TextView DontHaveAnAccountYet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        pattern = Patterns.EMAIL_ADDRESS;

        DontHaveAnAccountYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logiUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail=loginEmailEt.getText().toString().trim();
                String txtPassword=loginPasswordEt.getText().toString().trim();
                loginUserFunction(txtEmail,txtPassword);
            }
        });

    }

    private void loginUserFunction(String txtEmail, String txtPassword) {
        registerProgressLinearIndicator.setIndeterminate(true);
        registerProgressLinearIndicator.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult){
                Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registerProgressLinearIndicator.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

    }
    private void initViews() {

        logiUserButton=findViewById(R.id.logiUserButton);
        registerProgressLinearIndicator=findViewById(R.id.linearIndicatorProg);
        loginPasswordEt=findViewById(R.id.loginPasswordEt);
        loginEmailEt=findViewById(R.id.loginEmailEt);
        DontHaveAnAccountYet=findViewById(R.id.DontHaveAnAccountYet);

    }



}