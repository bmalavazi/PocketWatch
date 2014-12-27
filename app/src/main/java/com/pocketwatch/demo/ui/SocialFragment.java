package com.pocketwatch.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bmalavazi on 12/26/14.
 */
public class SocialFragment extends Fragment {
    private static final String TAG = "SocialFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.social_fragment, container, false);
    }

    public static TabSpec getTabSpec() {
        return new TabSpec(TAG, R.string.tab_social, SocialFragment.class);
    }
}
