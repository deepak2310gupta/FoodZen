package com.example.foodzen.CollectionFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodzen.CollectionActivities.SignUpSellerActivity;
import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class AddProductFragment extends Fragment {


    Button addProductButtonDetails;
    FirebaseAuth firebaseAuth;
    LinearProgressIndicator addProductLinearIndicator;
    EditText addProductName, addProductDescription, addproductOriginalPrice, addProductItemType, addProductDiscountedPrice, addProductDiscountedNote;
    Uri productimage_uri_new;
    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=200;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE=400;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE=300;
    CircularImageView addProductImage;
    String cameraPermissions[];
    String storagePermissions[];

    public AddProductFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewNewest = inflater.inflate(R.layout.fragment_add_product, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        addProductButtonDetails = viewNewest.findViewById(R.id.addProductButtonDetailsNew);
        addProductName = viewNewest.findViewById(R.id.addProductNameNew);
        addProductImage=viewNewest.findViewById(R.id.addProductImage);
        addProductDescription = viewNewest.findViewById(R.id.addProductDescriptionNew);
        addproductOriginalPrice = viewNewest.findViewById(R.id.addproductOriginalPriceNew);
        addProductItemType = viewNewest.findViewById(R.id.addProductItemTypeNew);
        addProductDiscountedPrice = viewNewest.findViewById(R.id.addProductDiscountedPriceNew);
        addProductDiscountedNote = viewNewest.findViewById(R.id.addProductDiscountedNoteNew);
        addProductLinearIndicator = viewNewest.findViewById(R.id.addProductLinearIndicatorNew);


        cameraPermissions=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        addProductButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtproductName = addProductName.getText().toString();
                String txtproductDesc = addProductDescription.getText().toString();
                String txtOriginalPrice = addproductOriginalPrice.getText().toString();
                String txtDiscountPrice = addProductDiscountedPrice.getText().toString();
                String txtDiscountNote = addProductDiscountedNote.getText().toString();
                String txtItemType = addProductItemType.getText().toString();
                addProductDetails(txtproductName, txtproductDesc, txtOriginalPrice, txtDiscountNote, txtDiscountPrice, txtItemType);
            }
        });

        addProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[]={"Camera","Gallery"};
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
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

        return viewNewest;
    }

    private void addProductDetails(String txtproductName, String txtproductDesc, String txtOriginalPrice, String txtDiscountNote, String txtDiscountPrice, String txtItemType) {

        addProductLinearIndicator.setIndeterminate(true);
        addProductLinearIndicator.setVisibility(View.VISIBLE);
        String timeValueStamp = String.valueOf(System.currentTimeMillis());

        if (TextUtils.isEmpty(txtproductName) || TextUtils.isEmpty(txtproductDesc) || TextUtils.isEmpty(txtOriginalPrice) || TextUtils.isEmpty(txtDiscountNote) || TextUtils.isEmpty(txtDiscountPrice) || TextUtils.isEmpty(txtItemType)) {
            addProductLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Plesae Fill All The Information Related To Products", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(productimage_uri_new==null){
            addProductLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Plesae Upload The Product Image", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            String time= String.valueOf(System.currentTimeMillis());
            String filePathAndName="product_images_sellers/"+firebaseAuth.getUid()+time;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(productimage_uri_new).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadProductimageUri=uriTask.getResult();
                    if(uriTask.isSuccessful()){
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("pId", "" + timeValueStamp);
                        hashMap.put("pName", "" + txtproductName);
                        hashMap.put("pDesc", "" + txtproductDesc);
                        hashMap.put("oriPrice", "" + txtOriginalPrice);
                        hashMap.put("itemType", "" + txtItemType);
                        hashMap.put("productimage",""+downloadProductimageUri);
                        hashMap.put("discountPrice", "" + txtDiscountPrice);
                        hashMap.put("discontNote", "" + txtDiscountNote);
                        hashMap.put("productUserId", "" + firebaseAuth.getUid());
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalProductsSeller");
                        databaseReference.child(timeValueStamp).setValue(hashMap);
                        addProductLinearIndicator.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Product Added Successfully !!", Toast.LENGTH_SHORT).show();
                        EmptyAllEditTexts();
                        return;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            });

        }


    }

    private void EmptyAllEditTexts() {
        addProductName.setText("");
        addProductDescription.setText("");
        addproductOriginalPrice.setText("");
        addProductDiscountedPrice.setText("");
        addProductDiscountedNote.setText("");
        addProductItemType.setText("");
        return;
    }

    private boolean checkStoragePermission(){
        boolean result= ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStorgePermission(){
        ActivityCompat.requestPermissions((Activity) getContext(),storagePermissions,STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){

        boolean result=ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)
                ==(PackageManager.PERMISSION_GRANTED);

        boolean result1=ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions((Activity) getContext(),cameraPermissions,CAMERA_REQUEST_CODE);
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
                        Toast.makeText(getContext(), "Please Enable Camera And Storage Permissions", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Please Enable Storage Permissions", Toast.LENGTH_SHORT).show();
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

        productimage_uri_new=getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,productimage_uri_new);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_REQUEST_CODE);

    }
    private void pickFromGallery(){

        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK){

            if(requestCode==IMAGE_PICK_GALLERY_REQUEST_CODE){
                productimage_uri_new=data.getData();
                addProductImage.setImageURI(productimage_uri_new);
            }
            if(requestCode==IMAGE_PICK_CAMERA_REQUEST_CODE){
                addProductImage.setImageURI(productimage_uri_new);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}