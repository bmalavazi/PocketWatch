package com.pocketwatch.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pocketwatch.demo.R;

/**
 * Created by bmalavazi on 12/26/14.
 */
public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    public static TabSpec getTabSpec() {
        return new TabSpec(TAG, R.string.tab_chat, ChatFragment.class);
    }
}
