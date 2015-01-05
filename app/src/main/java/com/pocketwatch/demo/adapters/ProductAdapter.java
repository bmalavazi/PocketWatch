package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketwatch.demo.models.Product;
import com.pocketwatch.demo.ui.R;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 1/1/15.
 */
public class ProductAdapter extends ArrayAdapter<Product> {
    private static final String TAG = "ProductAdapter";
    private List<Product> mProducts;
    private Context mContext;

    public ProductAdapter(Context context, int viewId, List<Product> products) {
        super(context, viewId, products);
        this.mProducts = products;
        this.mContext = context;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView description;
        public TextView store;
        public TextView price;
    }

    @Override
    public int getCount() { return mProducts.size(); }

    @Override
    public Product getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final String func = "getView()";
        ViewHolder viewHolder = null;
        ArrayList<String> imgUrls = new ArrayList<String>();

        Utils.Entry(TAG, func, "Position: " + position);

        Product product = mProducts.get(position);

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.product_image);
            viewHolder.description = (TextView) convertView.findViewById(R.id.product_description);
            viewHolder.store = (TextView) convertView.findViewById(R.id.product_store_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.product_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Utils.Debug(TAG, func, "Description: " + product.getDescription());
        viewHolder.description.setText(product.getDescription());
        Utils.Debug(TAG, func, "Store Name: " + product.getStoreName());
        viewHolder.store.setText(product.getStoreName());
        Utils.Debug(TAG, func, "Price: " + product.getPrice());
        viewHolder.price.setText(product.getPrice());

        for (Product.ProductThumbnail thumbnail : product.getThumbnailList())
            imgUrls.add(Utils.getThumbnail(thumbnail.getThumbnailUrl()));

        ImageLoader.loadImage(viewHolder.image,
                              imgUrls,
                              ImageLoader.getCache(),
                              R.drawable.thumb_placeholder);

        Utils.Exit(TAG, func);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public boolean isEmpty() {
        return mProducts.isEmpty();
    }
}
