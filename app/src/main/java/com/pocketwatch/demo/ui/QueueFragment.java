package com.pocketwatch.demo.ui;

/**
 * Created by bmalavazi on 12/22/14.
 */
public class QueueFragment extends BaseTabFragment {
    private static final String TAG = "QueueFragment";

    @Override
    public int getLayoutId() {
        return R.layout.queue_layout;
    }

    public static String getTAG() {
        return TAG;
    }
}
