package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketwatch.demo.models.Show;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 12/23/14.
 */
public class ShowAdapter extends ArrayAdapter<Show> {
    private static final String TAG = "ShowAdapter";
    private List<Show> mShows;
    private Context mContext;

    public ShowAdapter(Context context, int viewId, List<Show> shows) {
        super(context, viewId, shows);
        this.mShows = shows;
        this.mContext = context;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView text;
    }

    @Override
    public int getCount() {
        return mShows.size();
    }

    @Override
    public Show getItem(int position) {
        return mShows.get(position);
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

        Show show = mShows.get(position);

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.shows_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.show_image);
            viewHolder.text = (TextView) convertView.findViewById(R.id.show_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(show.getTitle());
        //imgUrls.add(show.getTileImageUrl());
        for (Show.ShowThumbnail thumbnail : show.getThumbnailList())
            imgUrls.add(Utils.getThumbnail(thumbnail.getThumbnailUrl()));
        ImageLoader.loadImage(viewHolder.image, imgUrls, ImageLoader.getCache(), R.drawable.thumb_placeholder);

        Utils.Exit(TAG, func);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public boolean isEmpty() {
        return mShows.isEmpty();
    }
}
