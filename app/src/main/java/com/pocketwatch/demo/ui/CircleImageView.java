package com.pocketwatch.demo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.pocketwatch.demo.utils.ImageHelper;

/**
 * Created by bmalavazi on 1/17/15.
 */
public class CircleImageView extends ImageView {
    private static final String TAG = "AspectRatioImageView";

    public CircleImageView(Context context) {
        super(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        Bitmap circleBitmap = ImageHelper.getRoundedCornerBitmap(bm, 100);

        super.setImageBitmap(circleBitmap);
    }

}
