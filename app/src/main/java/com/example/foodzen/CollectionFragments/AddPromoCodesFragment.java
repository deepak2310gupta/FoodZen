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

import com.example.foodzen.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class AddPromoCodesFragment extends Fragment {


    public AddPromoCodesFragment() {
        // Required empty public constructor
    }

    EditText addPromoCodeNameNew, addPromoCodeDescriptionNew, addPromoCodeSubTitle, addPromoDscount;
    Button addPromoCodesButtonNew;
    LinearProgressIndicator addPromoCodeLinearIndicatorNew;
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewPromo = inflater.inflate(R.layout.fragment_promo_codes, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews(viewPromo);

        addPromoCodesButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtpromoName = addPromoCodeNameNew.getText().toString();
                String txtpromoDesc = addPromoCodeDescriptionNew.getText().toString();
                String txtPromoTitle = addPromoCodeSubTitle.getText().toString();
                String txtPromoDscount = addPromoDscount.getText().toString();
                addPromoCodes(txtpromoName, txtpromoDesc, txtPromoTitle, txtPromoDscount);
            }
        });
        return viewPromo;

    }

    private void addPromoCodes(String txtpromoName, String txtpromoDesc, String txtPromoTitle, String txtPromoDscount) {

        addPromoCodeLinearIndicatorNew.setIndeterminate(true);
        addPromoCodeLinearIndicatorNew.setVisibility(View.VISIBLE);
        String timeValueStamp = String.valueOf(System.currentTimeMillis());

        if (TextUtils.isEmpty(txtpromoName) || TextUtils.isEmpty(txtpromoDesc) || TextUtils.isEmpty(txtPromoTitle) || TextUtils.isEmpty(txtPromoDscount)) {
            addPromoCodeLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Plesae Fill All The Information Related To Promo Codes", Toast.LENGTH_SHORT).show();
            return;
        } else {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            HashMap<String, Object> hashPromoCodes = new HashMap<>();
            hashPromoCodes.put("promoCodeid", "" + timeValueStamp);
            hashPromoCodes.put("promocodename", "" + txtpromoName);
            hashPromoCodes.put("promocodedesc", "" + txtpromoDesc);
            hashPromoCodes.put("promocodetitle", "" + txtPromoTitle);
            hashPromoCodes.put("promodiscountamount", "" + txtPromoDscount);
            hashPromoCodes.put("promocodeUserId", "" + firebaseUser.getUid());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TotalPromotionCodes");
            databaseReference.child(timeValueStamp).setValue(hashPromoCodes);
            addPromoCodeLinearIndicatorNew.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Promo Code Added Successfully !!", Toast.LENGTH_SHORT).show();
            EmptyAllEditTexts();
            return;
        }
    }

    private void initViews(View viewPromo) {

        addPromoCodeNameNew = viewPromo.findViewById(R.id.addPromoCodeNameNew);
        addPromoCodeDescriptionNew = viewPromo.findViewById(R.id.addPromoCodeDescriptionNew);
        addPromoCodeSubTitle = viewPromo.findViewById(R.id.addPromoCodeSubTitle);
        addPromoDscount = viewPromo.findViewById(R.id.addPromoDscount);
        addPromoCodesButtonNew = viewPromo.findViewById(R.id.addPromoCodesButtonNew);
        addPromoCodeLinearIndicatorNew = viewPromo.findViewById(R.id.addPromoCodeLinearIndicatorNew);
    }


    private void EmptyAllEditTexts() {
        addPromoCodeNameNew.setText("");
        addPromoCodeDescriptionNew.setText("");
        addPromoCodeSubTitle.setText("");
        addPromoDscount.setText("");
        return;
    }


}