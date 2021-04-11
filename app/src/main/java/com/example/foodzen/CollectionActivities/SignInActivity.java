package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodzen.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    Button logiUserButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Pattern pattern;
    EditText loginEmailEt, loginPasswordEt;
    ImageView newUserREG;
    LinearProgressIndicator registerProgressLinearIndicator;
    TextView DontHaveAnAccountYet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        pattern = Patterns.EMAIL_ADDRESS;

        DontHaveAnAccountYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        logiUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = loginEmailEt.getText().toString().trim();
                String txtPassword = loginPasswordEt.getText().toString().trim();
                loginUserFunction(txtEmail, txtPassword);
            }
        });

        newUserREG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loginUserFunction(String txtEmail, String txtPassword) {
        registerProgressLinearIndicator.setIndeterminate(true);
        registerProgressLinearIndicator.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Empty Credentials", Toast.LENGTH_SHORT).show();
            return;
        } else {

            firebaseAuth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    checkUserType();
                    return;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    registerProgressLinearIndicator.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            });
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
                        Intent intent = new Intent(SignInActivity.this, DashboardUserActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    } else {
                        Intent intent = new Intent(SignInActivity.this, DashboardSellerActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignInActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {

        logiUserButton = findViewById(R.id.logiUserButton);
        registerProgressLinearIndicator = findViewById(R.id.linearIndicatorProg);
        loginPasswordEt = findViewById(R.id.loginPasswordEt);
        loginEmailEt = findViewById(R.id.loginEmailEt);
        newUserREG = findViewById(R.id.newUserREG);
        DontHaveAnAccountYet = findViewById(R.id.DontHaveAnAccountYet);

    }


}