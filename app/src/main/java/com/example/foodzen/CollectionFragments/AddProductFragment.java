package com.example.foodzen.CollectionFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodzen.CollectionModels.ModelAddProducts;
import com.example.foodzen.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class AddProductFragment extends Fragment {



    Button addProductButtonDetails;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    LinearProgressIndicator addProductLinearIndicator;
    EditText addProductName,addProductDescription,addproductOriginalPrice,addProductItemType,addProductDiscountedPrice,addProductDiscountedNote;
    public AddProductFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewNewest= inflater.inflate(R.layout.fragment_add_product, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        addProductButtonDetails=viewNewest.findViewById(R.id.addProductButtonDetailsNew);
        addProductName=viewNewest.findViewById(R.id.addProductNameNew);
        addProductDescription=viewNewest.findViewById(R.id.addProductDescriptionNew);
        addproductOriginalPrice=viewNewest.findViewById(R.id.addproductOriginalPriceNew);
        addProductItemType=viewNewest.findViewById(R.id.addProductItemTypeNew);
        addProductDiscountedPrice=viewNewest.findViewById(R.id.addProductDiscountedPriceNew);
        addProductDiscountedNote=viewNewest.findViewById(R.id.addProductDiscountedNoteNew);
        addProductLinearIndicator=viewNewest.findViewById(R.id.addProductLinearIndicatorNew);
       // initViews(viewNewest);

        addProductButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtproductName=addProductName.getText().toString();
                String txtproductDesc=addProductDescription.getText().toString();
                String txtOriginalPrice=addproductOriginalPrice.getText().toString();
                String  txtDiscountPrice=addProductDiscountedPrice.getText().toString();
                String txtDiscountNote=addProductDiscountedNote.getText().toString();
                String txtItemType=addProductItemType.getText().toString();
                addProductDetails(txtproductName,txtproductDesc,txtOriginalPrice,txtDiscountNote,txtDiscountPrice,txtItemType);
            }
        });
        return viewNewest;
    }

    private void addProductDetails(String txtproductName, String txtproductDesc, String txtOriginalPrice, String txtDiscountNote, String txtDiscountPrice, String txtItemType) {

        addProductLinearIndicator.setIndeterminate(true);
        addProductLinearIndicator.setVisibility(View.VISIBLE);
        String timeValueStamp = String.valueOf(System.currentTimeMillis());

        if(TextUtils.isEmpty(txtproductName)|| TextUtils.isEmpty(txtproductDesc)|| TextUtils.isEmpty(txtOriginalPrice)|| TextUtils.isEmpty(txtDiscountNote) || TextUtils.isEmpty(txtDiscountPrice)|| TextUtils.isEmpty(txtItemType)){
            addProductLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Plesae Fill All The Information Related To Products", Toast.LENGTH_SHORT).show();
            return;
        }

        else {

            HashMap<String,Object>hashMap=new HashMap<>();
            hashMap.put("pId","" + timeValueStamp);
            hashMap.put("pName","" + txtproductName);
            hashMap.put("pDesc","" + txtproductDesc);
            hashMap.put("oriPrice","" + txtOriginalPrice);
            hashMap.put("itemType","" + txtItemType);
            hashMap.put("discountPrice","" + txtDiscountPrice);
            hashMap.put("discontNote","" + txtDiscountNote);
            hashMap.put("productUserId",""+user.getUid());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalProductsSeller");
            databaseReference.child(timeValueStamp).setValue(hashMap);
            addProductLinearIndicator.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Product Added Successfully !!", Toast.LENGTH_SHORT).show();
            EmptyAllEditTexts();
            return;
        }


    }

    private void EmptyAllEditTexts(){
        addProductName.setText("");
        addProductDescription.setText("");
        addproductOriginalPrice.setText("");
        addProductDiscountedPrice.setText("");
        addProductDiscountedNote.setText("");
        addProductItemType.setText("");
        return;
    }



}