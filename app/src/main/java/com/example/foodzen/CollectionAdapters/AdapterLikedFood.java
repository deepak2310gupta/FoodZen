package com.example.foodzen.CollectionAdapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodzen.CollectionModels.ModelLikedFoods;
import com.example.foodzen.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLikedFood extends RecyclerView.Adapter<AdapterLikedFood.LikeFoodHolder> {


    Context context;
    ArrayList<ModelLikedFoods> modelLikedFoodsArrayList;

    public boolean isLikedShimmer = true;
    public int likedShimmerNum = 6;


    public AdapterLikedFood(Context context, ArrayList<ModelLikedFoods> modelLikedFoodsArrayList) {
        this.context = context;
        this.modelLikedFoodsArrayList = modelLikedFoodsArrayList;
    }

    @NonNull
    @Override
    public LikeFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.likedfoodslayout, parent, false);
        return new LikeFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeFoodHolder holder, int position) {

        if (isLikedShimmer) {
            holder.shimmerlikedlayout.startShimmer();
        } else {
            holder.shimmerlikedlayout.stopShimmer();
            holder.shimmerlikedlayout.setShimmer(null);
            ModelLikedFoods modelLikedFoods = modelLikedFoodsArrayList.get(position);
            String one = modelLikedFoods.getLikedfoodname();
            String two = modelLikedFoods.getLikedfoodshopname();
            String txtimg=modelLikedFoods.getLikedfoodproductimage();
            String three = modelLikedFoods.getLikedfoodoriprice();
            String four = modelLikedFoods.getLikedfooddiscountprice();
            String five = modelLikedFoods.getLikedfooddiscountnote();
            holder.foodLikedName.setBackground(null);
            holder.foodLikedShopname.setBackground(null);
            holder.likedoriPrice.setBackground(null);
            holder.likeddiscprice.setBackground(null);
            Picasso.get().load(txtimg).into(holder.imageLiked);
            holder.foodLikedName.setText(one);
            holder.foodLikedShopname.setText(two);
            holder.likedoriPrice.setText(three);
            holder.likeddiscprice.setText(four);

            holder.likedoriPrice.setPaintFlags(holder.likedoriPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.likedDiscountNote.setText(five);
        }

    }

    @Override
    public int getItemCount() {

        return isLikedShimmer ? likedShimmerNum : modelLikedFoodsArrayList.size();
    }

    public class LikeFoodHolder extends RecyclerView.ViewHolder {

        ImageView imageLiked;
        ShimmerFrameLayout shimmerlikedlayout;
        TextView likeddiscprice, likedoriPrice, foodLikedShopname, foodLikedName, likedDiscountNote;

        public LikeFoodHolder(@NonNull View itemView) {
            super(itemView);
            shimmerlikedlayout = itemView.findViewById(R.id.shimmerlikedlayout);
            imageLiked = itemView.findViewById(R.id.imageLiked);
            likeddiscprice = itemView.findViewById(R.id.likeddiscprice);
            likedoriPrice = itemView.findViewById(R.id.likedoriPrice);
            foodLikedShopname = itemView.findViewById(R.id.foodLikedShopname);
            foodLikedName = itemView.findViewById(R.id.foodLikedName);
            likedDiscountNote = itemView.findViewById(R.id.likedDiscountNote);
        }
    }

}
