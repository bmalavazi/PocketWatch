package com.pocketwatch.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pocketwatch.demo.Callbacks.EpisodeCallback;
import com.pocketwatch.demo.Callbacks.JsonCallback;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.adapters.ProductAdapter;
import com.pocketwatch.demo.models.Product;
import com.pocketwatch.demo.utils.HttpRequestTask;
import com.pocketwatch.demo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/26/14.
 */
public class ShopFragment extends Fragment {
    private static final String TAG = "ShopFragment";
    private EpisodeCallback mCallback;
    private String mUuid;
    private List<Product> mProductList = new ArrayList<Product>();
    private ListView mProductView;
    private ProductAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String func = "onCreate()";

        mCallback = (EpisodeCallback) getActivity();
        mUuid = mCallback.getEpisodeUuid();

        mAdapter = new ProductAdapter(getActivity(), R.id.product, mProductList);

        Utils.Debug(TAG, func, "UUID: " + mUuid);
    }

    @Override
    public void onResume() {
        super.onResume();

        new HttpRequestTask(new JsonCallback() {
            @Override
            public void setJsonObject(JSONObject json) {
                Log.d(TAG, "setJsonObject()");
                mAdapter.clear();
                mProductList = Product.getProducts(json);

                for (Product product : mProductList)
                    mAdapter.add(product);
                mAdapter.notifyDataSetChanged();
            }
        }).execute(Utils.getEpisodeProducts(mUuid));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_fragment, container, false);
        mProductView = (ListView) view.findViewById(R.id.product_list);
        mProductView.setAdapter(mAdapter);

        return view;
    }

    public static TabSpec getTabSpec() {
        return new TabSpec(TAG, R.string.tab_shop, ShopFragment.class);
    }
}
