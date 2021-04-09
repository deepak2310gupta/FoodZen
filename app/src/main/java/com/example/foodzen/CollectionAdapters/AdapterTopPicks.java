package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelTopPicks;
import com.example.foodzen.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class AdapterTopPicks extends RecyclerView.Adapter<AdapterTopPicks.PicksViewHolder>{

    Context context;
    ArrayList<ModelTopPicks>modelTopPicksArrayList;

    public boolean isShimmerNew=true;
    public int shimmerNumberNew=5;

    public AdapterTopPicks(Context context, ArrayList<ModelTopPicks> modelTopPicksArrayList) {
        this.context = context;
        this.modelTopPicksArrayList = modelTopPicksArrayList;
    }

    @NonNull
    @Override
    public PicksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.toppicks_layout,parent,false);
        return new PicksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicksViewHolder holder, int position) {

        if(isShimmerNew) {
            holder.TopicksShimmer.startShimmer();
        }
        else {
            holder.TopicksShimmer.stopShimmer();
            holder.TopicksShimmer.setShimmer(null);
            ModelTopPicks modelTopPicks = modelTopPicksArrayList.get(position);
            String one = modelTopPicks.getShopName();
            String two = modelTopPicks.getShopDiscountNoteOff();
            String three = modelTopPicks.getTopPickid();
            String four = modelTopPicks.getShopId();
            holder.topPicksDiscoutnView.setBackground(null);
            holder.topPicksRestaurantName.setBackground(null);
            holder.topimagepicks.setImageResource(R.drawable.burgerking);
            holder.backgrounddiscount.setBackgroundResource(R.drawable.discountbackground);
            holder.topPicksDiscoutnView.setText(two);
            holder.topPicksRestaurantName.setText(one);
        }
    }

    @Override
    public int getItemCount() {
        return isShimmerNew?shimmerNumberNew:modelTopPicksArrayList.size();
    }


    public class PicksViewHolder extends RecyclerView.ViewHolder {

        TextView topPicksRestaurantName,topPicksDiscoutnView;
        ShimmerFrameLayout TopicksShimmer;
        ImageView topimagepicks;
        RelativeLayout backgrounddiscount;
        public PicksViewHolder(@NonNull View itemView) {
            super(itemView);
            topimagepicks=itemView.findViewById(R.id.topPicksImage);
            TopicksShimmer=itemView.findViewById(R.id.TopicksShimmer);
            backgrounddiscount=itemView.findViewById(R.id.backgrounddiscount);
            topPicksDiscoutnView=itemView.findViewById(R.id.topPicksDiscoutnView);
            topPicksRestaurantName=itemView.findViewById(R.id.topPicksRestaurantName);

        }
    }

}
