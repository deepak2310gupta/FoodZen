package com.example.foodzen.WalkthroughLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.foodzen.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.salad,
            R.drawable.fastdeliveryimage,
            R.drawable.cashlesspayment,
    };

    public String[] header = {
            "Delicious Food",
            "Fast Delivery",
            "Easy Payments"
    };

    public String[] detail = {
            "Eat Organic , Stay Healthy",
            "Special treat for your vehicle",
            "Imagine the secure future"
    };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slidinglayout, container, false);

        ImageView imageView = view.findViewById(R.id.imageoverboard);
        TextView pagertv1 = view.findViewById(R.id.TitleHeading);
        TextView pagertv2 = view.findViewById(R.id.DescriptionHeading);

        imageView.setImageResource(slide_images[position]);
        pagertv1.setText(header[position]);
        pagertv2.setText(detail[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
