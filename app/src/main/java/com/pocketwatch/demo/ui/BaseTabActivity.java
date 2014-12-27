package com.pocketwatch.demo.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;

import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.ImageLoader.BitmapSizeCache;
import com.pocketwatch.demo.utils.Utils;

import java.util.List;


public abstract class BaseTabActivity extends Activity {
    private static final String TAG = "BaseTabActivity";
    protected FragmentTabHost mTabHost;
    protected ActionBar mActionBar;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    ActionBar.TabListener mActionBarTabListener = new ActionBar.TabListener() {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String func = "onCreate()";
        Utils.Entry(TAG, func);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabs);

        mActionBar = getActionBar();
        mActionBar.setHomeButtonEnabled(false);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        ShowsFragment showsFragment = new ShowsFragment();
        mFragmentTransaction.replace(R.id.tabContent, showsFragment);
        mFragmentTransaction.commit();

        List<String> tabs = getTabTags();
        for (String tab : tabs)
            mActionBar.addTab(mActionBar.newTab().setText(tab).setTabListener(mActionBarTabListener));

        BitmapSizeCache cache = ImageLoader.getCache();
        if(cache == null){
            ImageLoader.initialize(getApplicationContext());
        }

        Utils.Exit(TAG, func);
    }

    protected void addTabs(List<TabSpec> tabs) {
        final String func = "addTabs()";
        Utils.Entry(TAG, func);

        for (TabSpec tab : tabs)
            mTabHost.addTab(mTabHost.newTabSpec(tab.getTabTag()).setIndicator(tab.getTabIndicator()),
                            tab.getTabClass(), null);

        Utils.Exit(TAG, func);
    }

    protected class TabSpec {
        private String tabSpecTag;
        private int tabSpecResId;
        private Class<?> tabSpecClass;

        public TabSpec(String tabTag, int tabResId, Class<?> tabClass) {
            this.tabSpecTag = tabTag;
            this.tabSpecResId = tabResId;
            this.tabSpecClass = tabClass;
        }

        public String getTabTag() { return tabSpecTag; }
        public CharSequence getTabIndicator() { return getResources().getText(tabSpecResId); }
        public Class<?> getTabClass() { return tabSpecClass; }
    }

    protected abstract List<TabSpec> getTabSpecs();
    protected abstract List<String> getTabTags();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
