package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelPromoCodes;
import com.example.foodzen.R;

import java.util.ArrayList;

public class AdapterPromoCodes extends RecyclerView.Adapter<AdapterPromoCodes.PromoCodeHolder> {


    Context context;
    ArrayList<ModelPromoCodes>modelPromoCodesArrayList;

    public AdapterPromoCodes(Context context, ArrayList<ModelPromoCodes> modelPromoCodesArrayList) {
        this.context = context;
        this.modelPromoCodesArrayList = modelPromoCodesArrayList;
    }

    @NonNull
    @Override
    public PromoCodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.promorows_layout,parent,false);
        return new PromoCodeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoCodeHolder holder, int position){
        ModelPromoCodes modelPromoCodes=modelPromoCodesArrayList.get(position);
        String txtOne=modelPromoCodes.getPromocodename();
        String txtTwo=modelPromoCodes.getPromocodedesc();
        String txtThree=modelPromoCodes.getPromocodetitle();

        holder.pcTitle.setText(txtThree);
        holder.pcDesc.setText(txtTwo);
        holder.pcName.setText(txtOne);

    }

    @Override
    public int getItemCount() {
        return modelPromoCodesArrayList.size();
    }

    public class PromoCodeHolder extends RecyclerView.ViewHolder {

        TextView pcTitle,pcDesc,pcName;
        public PromoCodeHolder(@NonNull View itemView) {
            super(itemView);
            pcTitle=itemView.findViewById(R.id.pcTitle);
            pcDesc=itemView.findViewById(R.id.pcDesc);
            pcName=itemView.findViewById(R.id.pcName);

        }
    }

}
