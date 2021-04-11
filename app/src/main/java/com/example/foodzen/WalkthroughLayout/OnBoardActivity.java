package com.example.foodzen.WalkthroughLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodzen.R;

public class OnBoardActivity extends AppCompatActivity {

    private TextView[] mDots;
    private LinearLayout mDotsLayout;
    ViewPager viewpagerid;
    private int mCurrenPage;
    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        viewpagerid = findViewById(R.id.viewpagerid);
        mDotsLayout = findViewById(R.id.mDotsLayout);
        sliderAdapter = new SliderAdapter(this);
        addDotsIndicator(0);
        viewpagerid.setAdapter(sliderAdapter);
        viewpagerid.addOnPageChangeListener(viewListener);
    }


    private void addDotsIndicator(int position) {

        mDots = new TextView[3];
        mDotsLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.inactivetransparent));

            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.yellow));
        }


    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int state, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrenPage = position;

            /*
            if (position == 0) {
                buttonNext.setEnabled(true);
                buttonPrev.setEnabled(false);
                buttonPrev.setVisibility(View.INVISIBLE);
                buttonNext.setText("Next");
                buttonPrev.setText("");
                buttonNext.setVisibility(View.GONE);
            } else if (position == mDots.length - 1) {
                buttonNext.setEnabled(true);
                buttonPrev.setEnabled(true);
                buttonPrev.setVisibility(View.VISIBLE);
                buttonNext.setText("Finish");
                buttonPrev.setText("Back");
                buttonNext.setVisibility(View.VISIBLE);
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ViewPagerOnBorad.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {

                buttonNext.setEnabled(true);
                buttonPrev.setEnabled(true);
                buttonPrev.setVisibility(View.VISIBLE);
                buttonNext.setVisibility(View.GONE);
                buttonNext.setText("Next");
                buttonPrev.setText("Back");

                 */

        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };


}