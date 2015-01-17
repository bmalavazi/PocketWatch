package com.pocketwatch.demo.ui;

import android.content.Context;
import android.widget.ImageView;

import com.pocketwatch.demo.Constants;

/**
 * Created by bmalavazi on 1/16/15.
 */
public class AspectRatioImageView extends ImageView {

    public AspectRatioImageView(Context context) {
        super(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int newHeight = (int) (height * Constants.BANNER_ASPECT_RATIO);

        setMeasuredDimension(width, newHeight);
    }
}
