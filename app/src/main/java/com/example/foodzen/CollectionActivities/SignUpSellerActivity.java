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

import com.example.foodzen.CollectionModels.ModelUsers;
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

import java.util.regex.Pattern;

public class SignUpSellerActivity extends AppCompatActivity {

    Button registerButtonN;
    FirebaseAuth firebaseAuthNew;
    FirebaseUser userNew;
    TextView alreadyHaveAccountSellerNew;
    EditText registerShopPasswordEtN,registerShopNameEtN,registerCategoryEtN,registerShopAddressEtN,registerShopEmailEtN,registerShopDiscount;
    Pattern pattern;
    LinearProgressIndicator registerProgressLinearIndicatorNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seller);
        firebaseAuthNew=FirebaseAuth.getInstance();
        userNew = firebaseAuthNew.getCurrentUser();
        pattern = Patterns.EMAIL_ADDRESS;

        initViews();

        registerButtonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtShopName=registerShopNameEtN.getText().toString();
                String txtShopAdress=registerShopAddressEtN.getText().toString();
                String txtShopCategory=registerCategoryEtN.getText().toString();
                String txtDiscoutNote=registerShopDiscount.getText().toString();
                String txtEmailIdShop=registerShopEmailEtN.getText().toString();
                String txtPasswordShop=registerShopPasswordEtN.getText().toString();
                registerSeller(txtShopName,txtShopCategory,txtShopAdress,txtDiscoutNote,txtEmailIdShop,txtPasswordShop);
            }
        });
    }

    private void registerSeller(String txtShopName, String txtShopCategory, String txtShopAdress, String txtDiscoutNote,
                                String txtEmailIdShop, String txtPasswordShop) {

        registerProgressLinearIndicatorNew.setIndeterminate(true);
        registerProgressLinearIndicatorNew.setVisibility(View.VISIBLE);

        if(TextUtils.isEmpty(txtShopName)||TextUtils.isEmpty(txtShopCategory)||TextUtils.isEmpty(txtShopAdress) || TextUtils.isEmpty(txtDiscoutNote)||TextUtils.isEmpty(txtEmailIdShop)|| TextUtils.isEmpty(txtPasswordShop)){
            registerProgressLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(this, "Plesae Fill All The Information", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(txtPasswordShop.length()<=6){
            registerProgressLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(this, "Password Should be Greater Than 6 Digits", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!pattern.matcher(txtEmailIdShop).matches()){
            registerProgressLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(this, "Please Enter A Valid Email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            firebaseAuthNew.createUserWithEmailAndPassword(txtEmailIdShop, txtPasswordShop).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        ModelUsers modelUsers = new ModelUsers(
                                "" + txtShopName,
                                "" + txtShopCategory,
                                "" + txtShopAdress,
                                "" + txtEmailIdShop,
                                "" + firebaseAuthNew.getCurrentUser().getUid(),
                                "" + "RestaurantSeller"
                        );
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalAppUsers");
                        databaseReference.child(firebaseAuthNew.getCurrentUser().getUid()).setValue(modelUsers);
                        Toast.makeText(SignUpSellerActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpSellerActivity.this, DashboardSellerActivity.class);
                        startActivity(intent);
                        return;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    registerProgressLinearIndicatorNew.setVisibility(View.GONE);
                    Toast.makeText(SignUpSellerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        }


    }

    private void initViews(){

        registerShopPasswordEtN=findViewById(R.id.registerShopPasswordEt);
        registerShopNameEtN=findViewById(R.id.registerShopNameEt);
        registerCategoryEtN=findViewById(R.id.registerShopCategoryEt);
        registerShopAddressEtN=findViewById(R.id.registerShopAddressEt);
        registerShopEmailEtN=findViewById(R.id.registerShopEmailEt);
        registerShopDiscount=findViewById(R.id.registerShopDiscountNote);
        registerButtonN=findViewById(R.id.registerhopButton);
        registerProgressLinearIndicatorNew=findViewById(R.id.registerProgressLinearIndicatorNew);
        alreadyHaveAccountSellerNew=findViewById(R.id.alreadyHaveAccountSellerNew);



    }


}