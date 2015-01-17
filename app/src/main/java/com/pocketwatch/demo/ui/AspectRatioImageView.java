package com.pocketwatch.demo.ui;

import android.content.Context;
import android.widget.ImageView;

import com.pocketwatch.demo.Constants;
import com.pocketwatch.demo.utils.Utils;

/**
 * Created by bmalavazi on 1/16/15.
 */
public class AspectRatioImageView extends ImageView {
    private static final String TAG = "AspectRatioImageView";

    public AspectRatioImageView(Context context) {
        super(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final String func = "onMeasure()";

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int newHeight = (int) ( ((double) width) * Constants.BANNER_ASPECT_RATIO);

        Utils.Debug(TAG, func, "Width: " + width);
        Utils.Debug(TAG, func, "New Height: " + newHeight);

        setMeasuredDimension(width, newHeight);
    }
}
