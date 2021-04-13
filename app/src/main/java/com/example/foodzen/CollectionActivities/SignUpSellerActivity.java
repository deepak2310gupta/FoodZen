package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodzen.CollectionModels.ModelSeller;
import com.example.foodzen.CollectionModels.ModelUsers;
import com.example.foodzen.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpSellerActivity extends AppCompatActivity {

    Button registerButtonN;
    FirebaseAuth firebaseAuthNew;
    TextView alreadyHaveAccountSellerNew;
    EditText registerShopPasswordEtN, registerShopNameEtN, registerCategoryEtN, registerShopAddressEtN, registerShopEmailEtN, registerShopDiscount;
    Pattern pattern;
    Uri image_uri_new;
    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=200;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE=400;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE=300;

    CircularImageView profileImageSeller;
    String cameraPermissions[];
    String storagePermissions[];
    LinearProgressIndicator registerProgressLinearIndicatorNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seller);
        firebaseAuthNew = FirebaseAuth.getInstance();
        pattern = Patterns.EMAIL_ADDRESS;

        initViews();
        cameraPermissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        registerButtonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtShopName = registerShopNameEtN.getText().toString();
                String txtShopAdress = registerShopAddressEtN.getText().toString();
                String txtShopCategory = registerCategoryEtN.getText().toString();
                String txtDiscoutNote = registerShopDiscount.getText().toString();
                String txtEmailIdShop = registerShopEmailEtN.getText().toString();
                String txtPasswordShop = registerShopPasswordEtN.getText().toString();
                registerSeller(txtShopName, txtShopCategory, txtShopAdress, txtDiscoutNote, txtEmailIdShop, txtPasswordShop);
            }
        });

        profileImageSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[]={"Camera","Gallery"};
                AlertDialog.Builder builder=new AlertDialog.Builder(SignUpSellerActivity.this);
                builder.setTitle("Pick an Image From");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){

                            if(checkCameraPermission())
                                pickFromCamera();
                            else
                                requestCameraPermission();
                        }
                        else{
                            if(checkStoragePermission())
                                pickFromGallery();
                            else
                                requestStorgePermission();
                        }
                    }
                });

                builder.create().show();
            }
        });
    }

    private void registerSeller(String txtShopName, String txtShopCategory, String txtShopAdress, String txtDiscoutNote,
                                String txtEmailIdShop, String txtPasswordShop) {

        registerProgressLinearIndicatorNew.setIndeterminate(true);
        registerProgressLinearIndicatorNew.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(txtShopName) || TextUtils.isEmpty(txtShopCategory) || TextUtils.isEmpty(txtShopAdress) || TextUtils.isEmpty(txtDiscoutNote) || TextUtils.isEmpty(txtEmailIdShop) || TextUtils.isEmpty(txtPasswordShop)) {
            registerProgressLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(this, "Plesae Fill All The Information", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtPasswordShop.length() <= 6) {
            registerProgressLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(this, "Password Should be Greater Than 6 Digits", Toast.LENGTH_SHORT).show();
            return;
        } else if (!pattern.matcher(txtEmailIdShop).matches()) {
            registerProgressLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(this, "Please Enter A Valid Email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(image_uri_new==null){
            registerProgressLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(this, "Please Upload Your Restaurant Image", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            firebaseAuthNew.createUserWithEmailAndPassword(txtEmailIdShop, txtPasswordShop).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                       saveInformation(txtShopName,txtShopCategory,txtShopAdress,txtDiscoutNote, txtEmailIdShop,txtPasswordShop);
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

    private void initViews() {

        registerShopPasswordEtN = findViewById(R.id.registerShopPasswordEt);
        registerShopNameEtN = findViewById(R.id.registerShopNameEt);
        registerCategoryEtN = findViewById(R.id.registerShopCategoryEt);
        registerShopAddressEtN = findViewById(R.id.registerShopAddressEt);
        registerShopEmailEtN = findViewById(R.id.registerShopEmailEt);
        registerShopDiscount = findViewById(R.id.registerShopDiscountNote);
        registerButtonN = findViewById(R.id.registerhopButton);
        profileImageSeller=findViewById(R.id.profileImageSeller);
        registerProgressLinearIndicatorNew = findViewById(R.id.registerProgressLinearIndicatorNew);
        alreadyHaveAccountSellerNew = findViewById(R.id.alreadyHaveAccountSellerNew);


    }


    private void saveInformation(String txtShopName, String txtShopCategory, String txtShopAdress, String txtDiscoutNote,
                                 String txtEmailIdShop, String txtPasswordShop) {

        String filePathAndName="profile_images_sellers/"+firebaseAuthNew.getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri_new).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadimageUri=uriTask.getResult();

                if(uriTask.isSuccessful()){

                    HashMap<String, Object> hashMapSeller = new HashMap<>();
                    hashMapSeller.put("name", "" + txtShopName);
                    hashMapSeller.put("category", "" + txtShopCategory);
                    hashMapSeller.put("address", "" + txtShopAdress);
                    hashMapSeller.put("email", "" + txtEmailIdShop);
                    hashMapSeller.put("uid", "" + firebaseAuthNew.getUid());
                    hashMapSeller.put("usertype", "RestaurantSeller");
                    hashMapSeller.put("profileimageseller",""+downloadimageUri);
                    hashMapSeller.put("discountnote", "" + txtDiscoutNote);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalAppUsers");
                    databaseReference.child(firebaseAuthNew.getUid()).setValue(hashMapSeller);
                    Toast.makeText(SignUpSellerActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpSellerActivity.this, DashboardSellerActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpSellerActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private boolean checkStoragePermission(){
        boolean result= ContextCompat.checkSelfPermission(SignUpSellerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStorgePermission(){
        ActivityCompat.requestPermissions(SignUpSellerActivity.this,storagePermissions,STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){

        boolean result=ContextCompat.checkSelfPermission(SignUpSellerActivity.this,Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);

        boolean result1=ContextCompat.checkSelfPermission(SignUpSellerActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(SignUpSellerActivity.this,cameraPermissions,CAMERA_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeStorageAccepted){
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Please Enable Camera And Storage Permissions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                boolean writeStorageAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                if(writeStorageAccepted){
                    pickFromGallery();
                }
                else{
                    Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void pickFromCamera(){

        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Description");

        image_uri_new=SignUpSellerActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri_new);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_REQUEST_CODE);

    }
    private void pickFromGallery(){

        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK){

            if(requestCode==IMAGE_PICK_GALLERY_REQUEST_CODE){
                image_uri_new=data.getData();
                profileImageSeller.setImageURI(image_uri_new);
            }
            if(requestCode==IMAGE_PICK_CAMERA_REQUEST_CODE){
                profileImageSeller.setImageURI(image_uri_new);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}