package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.pocketwatch.demo.ui.R;

import java.util.ArrayList;

/**
 * Created by bmalavazi on 1/5/15.
 */
public class BannerPagerAdapter<E> extends PagerAdapter {
    private ArrayList<E> mViews = null;
    private ImageView [][] mImageViews;
    private Context mContext;

    public BannerPagerAdapter(Context context, ArrayList<E> pages) {
        super();
        mViews = pages;
        mContext = context;
        mImageViews = new ImageView[getCount()][getCount()];

        for (int i = 0; i < getCount(); i++)
            for (int j = 0; j < getCount(); j++)
                mImageViews[i][j] = new ImageView(mContext);
    }

    @Override
    public Object instantiateItem(View view, int position) {
        E myView = mViews.get(position);
        LinearLayout verticalLayout = new LinearLayout(mContext);
        LinearLayout horizontalLayout = new LinearLayout(mContext);

        if (null != ((View)myView).getParent()) {
            ((ViewGroup) ((View)myView).getParent()).removeView((View)myView);
        }

        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        verticalLayout.addView((View) myView);

        for (int i = 0; i < getCount(); i++) {
            if (i == position)
                mImageViews[position][i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_shape_on));
            else
                mImageViews[position][i].setImageDrawable(mContext.getResources().getDrawable(R.drawable.circle_shape_off));

            mImageViews[position][i].setPadding(0, 0, 5, 0);

            if (null != (mImageViews[position][i]).getParent()) {
                ((ViewGroup) (mImageViews[position][i]).getParent()).removeView(mImageViews[position][i]);
            }
            horizontalLayout.addView(mImageViews[position][i]);
        }

        horizontalLayout.setLayoutParams(layoutParams);
        horizontalLayout.setGravity(Gravity.CENTER);

        if (null != horizontalLayout.getParent()) {
            ((ViewGroup) (horizontalLayout).getParent()).removeView(horizontalLayout);
        }

        verticalLayout.addView(horizontalLayout);

        ((ViewPager) view).addView((View) verticalLayout);

        return verticalLayout;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(View view, int arg1, Object object) {
        ((ViewPager) view).removeView((View)object);
    }
}
