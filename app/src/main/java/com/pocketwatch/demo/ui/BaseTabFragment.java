package com.pocketwatch.demo.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bmalavazi on 12/22/14.
 */
public abstract class BaseTabFragment extends Fragment {
    public abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        int id = getLayoutId();

        if (0 == id)
            return null;

        return inflater.inflate(id, container, false);
    }

}
