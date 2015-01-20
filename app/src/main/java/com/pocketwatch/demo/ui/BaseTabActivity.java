package com.pocketwatch.demo.ui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pocketwatch.demo.R;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.ImageLoader.BitmapSizeCache;
import com.pocketwatch.demo.utils.Utils;

import java.util.List;


public abstract class BaseTabActivity extends Activity {
    private static final String TAG = "BaseTabActivity";
    protected ActionBar mActionBar;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

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
        //mFragmentTransaction = mFragmentManager.beginTransaction();
        //ShowsFragment showsFragment = new ShowsFragment();
        //QueueFragment queueFragment = new QueueFragment();
        //mFragmentTransaction.replace(R.id.tabContent, showsFragment);
        //mFragmentTransaction.commit();

        List<TabSpec> tabs = getTabSpecs();
        for (TabSpec tab : tabs)
            mActionBar.addTab(mActionBar.newTab().setText(tab.getTabTag()).setTabListener(new TabListener(this, tab.getTabTag(), tab.getTabClass())));

        //List<String> tabs = getTabTags();
        //for (String tab : tabs)
            //mActionBar.addTab(mActionBar.newTab().setText(tab).setTabListener(mActionBarTabListener));

        BitmapSizeCache cache = ImageLoader.getCache();
        if(cache == null){
            ImageLoader.initialize(getApplicationContext());
        }

        Utils.Exit(TAG, func);
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

    private class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;

        public TabListener(Activity activity, String tag, Class<T> clz) {
            this(activity, tag, clz, null);
        }

        public TabListener(Activity activity, String tag, Class<T> clz,
                           Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;

            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
            if (mFragment != null && !mFragment.isDetached()) {
                FragmentTransaction ft = mActivity.getFragmentManager()
                        .beginTransaction();
                ft.detach(mFragment);
                ft.commit();
            }
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            final String func = "onTabSelected()";

            Utils.Entry(TAG, func);

            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName(),
                        mArgs);
                ft.add(R.id.tabContent, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }

            Utils.Exit(TAG, func);
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            final String func = "onTabUnselected()";

            Utils.Entry(TAG, func);

            if (mFragment != null) {
                ft.detach(mFragment);
            }

            Utils.Exit(TAG, func);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            //new CustomToast(mActivity, "Reselected!");
        }
    }

}
