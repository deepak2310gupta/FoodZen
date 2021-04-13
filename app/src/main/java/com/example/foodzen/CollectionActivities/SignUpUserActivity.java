package com.example.foodzen.CollectionActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class SignUpUserActivity extends AppCompatActivity {

    Button registerButton;
    FirebaseAuth firebaseAuth;
    TextView alreadyHaveAccount, txtForSellerRegisteration;
    EditText registerPasswordEt, registerNameEt, registerMobileEt, registerAddressEt, registerEmailEt;
    Pattern pattern;

    ImageView BackToLogin;
    CircularImageView profileImageUser;
    Uri image_uri;
    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=200;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE=400;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE=300;

    String cameraPermissions[];
    String storagePermissions[];
    LinearProgressIndicator registerProgressLinearIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        firebaseAuth = FirebaseAuth.getInstance();
        pattern = Patterns.EMAIL_ADDRESS;

        cameraPermissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpUserActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        profileImageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[]={"Camera","Gallery"};
                AlertDialog.Builder builder=new AlertDialog.Builder(SignUpUserActivity.this);
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
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtname = registerNameEt.getText().toString().trim();
                String txtphone = registerMobileEt.getText().toString().trim();
                String txtaddress = registerAddressEt.getText().toString().trim();
                String txtemail = registerEmailEt.getText().toString().trim();
                String txtpassword = registerPasswordEt.getText().toString().trim();
                registerUser(txtname, txtphone, txtaddress, txtemail, txtpassword);
            }
        });
        txtForSellerRegisteration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpUserActivity.this, SignUpSellerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpUserActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initViews() {

        registerPasswordEt = findViewById(R.id.registerPasswordEt);
        registerNameEt = findViewById(R.id.registerNameEt);
        registerButton = findViewById(R.id.registerButton);
        registerMobileEt = findViewById(R.id.registerMobileEt);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        registerAddressEt = findViewById(R.id.registerAddressEt);
        registerEmailEt = findViewById(R.id.registerEmailEt);
        registerProgressLinearIndicator = findViewById(R.id.registerProgressLinearIndicator);
        txtForSellerRegisteration = findViewById(R.id.txtForSellerRegisteration);
        BackToLogin = findViewById(R.id.BackToLogin);
        profileImageUser=findViewById(R.id.profileImageUser);

    }


    private void registerUser(String txtname, String txtphone, String txtaddress, String txtemail, String txtpassword) {

        registerProgressLinearIndicator.setIndeterminate(true);
        registerProgressLinearIndicator.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(txtname) || TextUtils.isEmpty(txtphone) || TextUtils.isEmpty(txtaddress) || TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpassword)) {
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Plesae Fill All The Information", Toast.LENGTH_SHORT).show();
            return;
        } else if (txtpassword.length() <= 6) {
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Password Should be Greater Than 6 Digits", Toast.LENGTH_SHORT).show();
            return;
        } else if (!pattern.matcher(txtemail).matches()) {
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Please Enter A Valid Email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(image_uri==null){
            registerProgressLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(this, "Please Upload Your Profile Image", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            firebaseAuth.createUserWithEmailAndPassword(txtemail,txtpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    saveInformation(txtname,txtphone, txtaddress,txtemail,txtpassword);
                    else
                        Toast.makeText(SignUpUserActivity.this, "Task Is Not Successfull", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpUserActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            });
        }

    }

    private void saveInformation(String txtname, String txtphone, String txtaddress, String txtemail, String txtpassword) {

        String filePathAndName="profile_images_users/"+firebaseAuth.getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadimageUri=uriTask.getResult();

                if(uriTask.isSuccessful()){

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", "" + txtname);
                    hashMap.put("phone", "" + txtphone);
                    hashMap.put("address", "" + txtaddress);
                    hashMap.put("email", "" + txtemail);
                    hashMap.put("uid", "" + firebaseAuth.getUid());
                    hashMap.put("profileimage",""+downloadimageUri);
                    hashMap.put("usertype", "User");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalAppUsers");
                    databaseReference.child(firebaseAuth.getUid()).setValue(hashMap);
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
                Toast.makeText(SignUpUserActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private boolean checkStoragePermission(){
        boolean result= ContextCompat.checkSelfPermission(SignUpUserActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStorgePermission(){
        ActivityCompat.requestPermissions(SignUpUserActivity.this,storagePermissions,STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){

        boolean result=ContextCompat.checkSelfPermission(SignUpUserActivity.this,Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);

        boolean result1=ContextCompat.checkSelfPermission(SignUpUserActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(SignUpUserActivity.this,cameraPermissions,CAMERA_REQUEST_CODE);
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

        image_uri=SignUpUserActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
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
                image_uri=data.getData();
                profileImageUser.setImageURI(image_uri);
            }
            if(requestCode==IMAGE_PICK_CAMERA_REQUEST_CODE){
                profileImageUser.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }







}