package com.pocketwatch.demo.ui;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/22/14.
 */
public class HomeActivity extends BaseTabActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected List<TabSpec> getTabSpecs() {
        ArrayList<TabSpec> tabs = new ArrayList<TabSpec>();

        tabs.add(new TabSpec(ShowsFragment.getTAG(),
                             R.string.tab_shows,
                             ShowsFragment.class));
/*
        tabs.add(new TabSpec(QueueFragment.getTAG(),
                             R.string.tab_queue,
                             QueueFragment.class));
*/
        return tabs;
    }

    @Override
    protected List<String> getTabTags() {
        ArrayList<String> tabs = new ArrayList<String>();
        tabs.add("Shows");

        return tabs;
    }
}
