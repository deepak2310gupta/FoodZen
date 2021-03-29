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
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodzen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpUserActivity extends AppCompatActivity {

    Button registerButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView alreadyHaveAccount,txtForSellerRegisteration;
    EditText registerPasswordEt,registerNameEt,registerMobileEt,registerAddressEt,registerEmailEt;
    Pattern pattern;
    LinearProgressIndicator registerProgressLinearIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        pattern = Patterns.EMAIL_ADDRESS;

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpUserActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
      
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtname=registerNameEt.getText().toString().trim();
                String txtphone=registerMobileEt.getText().toString().trim();
                String txtaddress=registerAddressEt.getText().toString().trim();
                String txtemail=registerEmailEt.getText().toString().trim();
                String txtpassword=registerPasswordEt.getText().toString().trim();
                registerUser(txtname,txtphone,txtaddress,txtemail,txtpassword);
            }
        });

        txtForSellerRegisteration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpUserActivity.this,SignUpSellerActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initViews() {

        registerPasswordEt=findViewById(R.id.registerPasswordEt);
        registerNameEt=findViewById(R.id.registerNameEt);
        registerButton=findViewById(R.id.registerButton);
        registerMobileEt=findViewById(R.id.registerMobileEt);
        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);
        registerAddressEt=findViewById(R.id.registerAddressEt);
        registerEmailEt=findViewById(R.id.registerEmailEt);
        registerProgressLinearIndicator=findViewById(R.id.registerProgressLinearIndicator);
        txtForSellerRegisteration=findViewById(R.id.txtForSellerRegisteration);

    }


    private void registerUser(String txtname, String txtphone, String txtaddress, String txtemail, String txtpassword) {

        registerProgressLinearIndicator.setIndeterminate(true);
        registerProgressLinearIndicator.setVisibility(View.VISIBLE);

        if(TextUtils.isEmpty(txtname)||TextUtils.isEmpty(txtphone)||TextUtils.isEmpty(txtaddress) || TextUtils.isEmpty(txtemail)||TextUtils.isEmpty(txtpassword)){
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Plesae Fill All The Information", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(txtpassword.length()<=6){
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Password Should be Greater Than 6 Digits", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!pattern.matcher(txtemail).matches()){
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Please Enter A Valid Email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            firebaseAuth.createUserWithEmailAndPassword(txtemail, txtpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                HashMap<String,Object> hashMap=new HashMap<>();
                                hashMap.put("name","" + txtname);
                                hashMap.put("phone","" + txtphone);
                                hashMap.put("address", "" + txtaddress);
                                hashMap.put("email","" + txtemail);
                                hashMap.put("uid","" + firebaseAuth.getCurrentUser().getUid());
                                hashMap.put("usertype","User");
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalAppUsers");
                                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(hashMap);
                                Toast.makeText(SignUpUserActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpUserActivity.this, DashboardUserActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    registerProgressLinearIndicator.setVisibility(View.GONE);
                    Toast.makeText(SignUpUserActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        }

    }





}