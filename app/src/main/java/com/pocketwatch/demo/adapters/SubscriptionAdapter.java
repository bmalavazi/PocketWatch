package com.pocketwatch.demo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketwatch.demo.models.Show;
import com.pocketwatch.demo.models.Subscription;
import com.pocketwatch.demo.R;
import com.pocketwatch.demo.utils.ImageLoader;
import com.pocketwatch.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmalavazi on 1/18/15.
 */
public class SubscriptionAdapter extends ArrayAdapter<Subscription> {
    private static final String TAG = "SubscriptionAdapter";
    private List<Subscription> mSubscriptions;
    private Context mContext;

    public SubscriptionAdapter(Context context, int viewId, List<Subscription> shows) {
        super(context, viewId, shows);
        this.mSubscriptions = shows;
        this.mContext = context;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView duration;
    }

    @Override
    public int getCount() {
        return mSubscriptions.size();
    }

    @Override
    public Subscription getItem(int position) {
        return mSubscriptions.get(position);
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

        Subscription subscription = mSubscriptions.get(position);
        Show show = subscription.getShow();

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.queue_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.queue_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.queue_title);
            viewHolder.duration = (TextView) convertView.findViewById(R.id.queue_date_duration);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(show.getTitle());

        //viewHolder.dateDuration.setText(calendar.getDateString() + " • " + Utils.getFormatDuration(episode.getDuration()));
        viewHolder.duration.setText(Utils.getFormattedUnwatched(subscription.getUnviewedEpisodeCount()) + " • " + Utils.getFormatDuration(subscription.getUnviewedEpisodeDuration()));

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
        return mSubscriptions.isEmpty();
    }
}
